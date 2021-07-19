package web.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import web.config.handler.LoginSuccessHandler;
import web.services.UserService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserService userService;

    //public SecurityConfig(){}

    @Autowired
    private LoginSuccessHandler loginSuccessHandler;


    //    @Override
//    public void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication().withUser("ADMIN").password("ADMIN").roles("ADMIN");
//    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/hello").permitAll()
                .antMatchers("/user").hasAnyRole("USER", "ADMIN")
                .antMatchers("/users").hasRole("ADMIN")

                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
//                .loginPage("/login").permitAll()
                // .defaultSuccessUrl("/auth/hello");
//                .loginProcessingUrl("/login")
                .successHandler(new LoginSuccessHandler())//new LoginSuccessHandler())   loginSuccessHandler
                .usernameParameter("username")
                .passwordParameter("password") // везде 100
                .permitAll();



//        http.formLogin()
//                // указываем страницу с формой логина
//                .loginPage("/login")
//                //указываем логику обработки при логине
//                .successHandler(new LoginSuccessHandler())
//                // указываем action с формы логина
//                .loginProcessingUrl("/login")
//                // Указываем параметры логина и пароля с формы логина
//                .usernameParameter("j_username")
//                .passwordParameter("j_password")
//                // даем доступ к форме логина всем
//                .permitAll();

//        http.authorizeRequests()
//                .antMatchers("/").permitAll() // доступность всем
//                .antMatchers("/user").access("hasAnyRole('ROLE_USER')") // разрешаем входить на /user пользователям с ролью User
//                .and().formLogin()  // Spring сам подставит свою логин форму
//                .successHandler(loginSuccessHandler); // подключаем наш SuccessHandler для перенеправления по ролям
//                .usernameParameter("j_username")
//               .passwordParameter("j_password");

//        http.logout()
//                // разрешаем делать логаут всем
//                .permitAll()
//                // указываем URL логаута
//                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
//                // указываем URL при удачном логауте
//                .logoutSuccessUrl("/login?logout")
//                //выклчаем кроссдоменную секьюрность (на этапе обучения неважна)
//                .and().csrf().disable();

//        http
//                // делаем страницу регистрации недоступной для авторизированных пользователей
//                .authorizeRequests()
//                //страницы аутентификаци доступна всем
//                .antMatchers("/login").anonymous()
//                // защищенные URL
//                .antMatchers("/hello").access("hasAnyRole('ADMIN')").anyRequest().authenticated();
    }

//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return NoOpPasswordEncoder.getInstance();
//    }
    @Bean
    protected PasswordEncoder passwordEncoder (){
        return new BCryptPasswordEncoder(12);
    }
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        authenticationProvider.setUserDetailsService(userService);
        return authenticationProvider;
    }
}