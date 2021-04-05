package web.config;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

public class AppInit  extends AbstractAnnotationConfigDispatcherServletInitializer { // implements WebApplicationInitializer{

//    private final static String DISPATCHER = "dispatcher";
//
//    @Override
//    public void onStartup(ServletContext servletContext) throws ServletException {
//        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
//        context.register(WebConfig.class);
//        servletContext.addListener(new ContextLoaderListener(context));
//
//        ServletRegistration.Dynamic servlet = servletContext.addServlet(DISPATCHER, new DispatcherServlet(context));
//        servlet.addMapping("/");
//        servlet.setLoadOnStartup(1);
//    }


        @Override //  Метод, указывающий на класс конфигурации
    protected Class<?>[] getRootConfigClasses() {
        return new Class[0];
    }
    // Добавление конфигурации, в которой инициализируем ViewResolver, для корректного отображения jsp.
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[]{
                WebConfig.class
        };
    }
    // Данный метод указывает url, на котором будет базироваться приложение
    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }
}
