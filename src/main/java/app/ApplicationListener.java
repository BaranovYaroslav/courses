package app;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * Listener for setup application on startup.
 *
 * @author Yaroslav Baranov
 */
@WebListener
public class ApplicationListener implements ServletContextListener {

    private static Logger LOGGER = LogManager.getLogger(ApplicationListener.class);

    private Application application = new Application();

    /**
     * Method that provide application initializing.
     *
     * @param servletContextEvent object that holds context needed to initialize application
     */
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
