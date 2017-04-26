package service;

import dispatcher.HttpWrapper;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * Created by Ярослав on 21.04.2017.
 */
public class NotificationService {
    public static void notify(HttpWrapper httpWrapper, String message){
        httpWrapper.getRequest().setAttribute("message", message);
        try {
            httpWrapper.getRequest().getRequestDispatcher("/pages/notification.jsp").forward(httpWrapper.getRequest(), httpWrapper.getResponse());
        } catch (ServletException | IOException e) {
            e.printStackTrace();
        }
    }
}
