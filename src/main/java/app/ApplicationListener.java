package app;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * Created by Ярослав on 12.07.2017.
 */
@WebListener
public class ApplicationListener implements ServletContextListener {

    private static Logger LOGGER = LogManager.getLogger(ApplicationListener.class);

    private Application application = new Application();

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        servletContextEvent.getServletContext().setAttribute("application", application);
        application.setServletContext(servletContextEvent.getServletContext());
        application.init();

        LOGGER.info("Application started");
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
