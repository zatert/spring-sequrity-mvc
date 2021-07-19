package web.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;


import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
@Configuration
//@EnableJpaRepositories("web.repository")
@EnableTransactionManagement
@ComponentScan("web")
@PropertySource(value = "classpath:db.properties", encoding = "UTF-8")
public class DataBaseConfig {

    @Autowired
    private Environment environment;
//    @Bean
//    public DataSourc dataSourc(){   // здесь подключаем DBCP через мавен
//        return null;
//    }
    // Настройки ентитиМенеджер
    @Bean//(name = "emf")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean em
                = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource());
        em.setPackagesToScan(environment.getRequiredProperty("db.entity.package")); //new String[] { "web" }
        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        em.setJpaProperties(getHibernateProperties()); // additionalProperties()
        return em;
    }
    public Properties getHibernateProperties(){
        try {
        Properties properties = new Properties();
        InputStream is = getClass().getClassLoader().getResourceAsStream("hibernate.properties");
        properties.load(is);
        return properties;
        } catch (IOException e) {
            throw new IllegalArgumentException("can't find 'hibernate.properties' in classpath", e);
        }
    }
    @Bean
    public DataSource dataSource(){
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/spring_sec_db");  //
        dataSource.setUsername( "root" );
        dataSource.setPassword( "root" );
        return dataSource;
    }
        @Bean
    public PlatformTransactionManager transactionManager() {
//    HibernateTransactionManager transactionManager = new HibernateTransactionManager();
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());

        return transactionManager;
    }
//
//    @Bean
//    public PersistenceExceptionTranslationPostProcessor exceptionTranslation(){
//        return new PersistenceExceptionTranslationPostProcessor();
//    }
//
//    Properties additionalProperties() {
//        Properties properties = new Properties();
//        properties.setProperty("hibernate.hbm2ddl.auto", "create-drop");
//        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
//
//        return properties;
//    }
}
