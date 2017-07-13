package service;

import constants.NavigationConstants;
import constants.RequestAttribute;
import dispatcher.HttpWrapper;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * Service that provide notification.
 *
 * @author Yaroslav Baranov
 */
public class NotificationService {

    /**
     * Method that forward user to notification page.
     *
     * @param httpWrapper holder of http request and response
     * @param message message to user
     */
    public static void notify(HttpWrapper httpWrapper, String message){
        httpWrapper.getRequest().setAttribute(RequestAttribute.MESSAGE, message);
        try {
            httpWrapper.getRequest().getRequestDispatcher(NavigationConstants.NOTIFICATION_PAGE)
                                    .forward(httpWrapper.getRequest(), httpWrapper.getResponse());
        } catch (ServletException | IOException e) {
            e.printStackTrace();
        }
    }
}
