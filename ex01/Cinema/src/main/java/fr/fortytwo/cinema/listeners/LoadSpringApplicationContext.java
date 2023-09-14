package fr.fortytwo.cinema.listeners;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import fr.fortytwo.cinema.config.ApplicationConfig;

@WebListener
public class LoadSpringApplicationContext implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext context = servletContextEvent.getServletContext();
        var springContext = new AnnotationConfigApplicationContext(ApplicationConfig.class);
        context.setAttribute("springContext", springContext);
    }
}
