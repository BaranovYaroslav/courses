package service;

import dispatcher.HttpWrapper;

import javax.servlet.ServletException;
import java.io.IOException;

import org.apache.log4j.Logger;

/**
 * Created by Ярослав on 15.04.2017.
 */
public class NavigationService {

    private static Logger LOGGER = Logger.getLogger(NavigationService.class);

    public static void navigateTo(HttpWrapper httpWrapper, String url) {
        LOGGER.error("in navigation " + url + " " + httpWrapper);
        try {
            httpWrapper.getRequest().getRequestDispatcher(url)
                                    .forward(httpWrapper.getRequest(), httpWrapper.getResponse());
        } catch (ServletException | IOException e) {
            LOGGER.error("Error when trying forward to " + url + " " + e);
        }
    }

    public static void redirectTo(HttpWrapper httpWrapper, String url) {
        try {
            httpWrapper.getResponse().sendRedirect("/pro" + url);
        } catch (IOException e) {
            LOGGER.error("Error when trying redirect to " + url + " " + e);
        }
    }
}
