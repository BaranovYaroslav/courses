package service;

import constants.NavigationConstants;
import dispatcher.HttpWrapper;

import javax.servlet.ServletException;
import java.io.IOException;

import org.apache.log4j.Logger;

/**
 * Service that provide navigation.
 *
 * @author Yaroslav Baranov
 */
public class NavigationService {

    private static Logger LOGGER = Logger.getLogger(NavigationService.class);

    /**
     * Method that forward user to given url.
     *
     * @param httpWrapper holder of http request and response
     * @param url url to forward
     */
    public static void navigateTo(HttpWrapper httpWrapper, String url) {
        try {
            httpWrapper.getRequest().getRequestDispatcher(url)
                                    .forward(httpWrapper.getRequest(), httpWrapper.getResponse());
        } catch (ServletException | IOException e) {
            LOGGER.error("Error when trying forward to " + url + " " + e);
        }
    }

    /**
     * Method that redirect user to given url.
     *
     * @param httpWrapper holder of http request and response
     * @param url url to redirect
     */
    public static void redirectTo(HttpWrapper httpWrapper, String url) {
        try {
            httpWrapper.getResponse().sendRedirect(NavigationConstants.BASE_APPLICATION_URL + url);
        } catch (IOException e) {
            LOGGER.error("Error when trying redirect to " + url + " " + e);
        }
    }
}
