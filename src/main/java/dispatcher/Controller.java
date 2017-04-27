package dispatcher;

import application.MessagesConstants;
import org.apache.log4j.Logger;
import service.NotificationService;

/**
 * Created by Ярослав on 11.04.2017.
 */
public class Controller {

    private static Logger LOGGER = Logger.getLogger(Controller.class);

    public void execute(HttpWrapper httpWrapper) {
        String method = httpWrapper.getRequest().getMethod();

        switch (method) {
            case HttpMethods.GET:
                get(httpWrapper);
                break;
            case HttpMethods.POST:
                post(httpWrapper);
                break;
            case HttpMethods.PUT:
                put(httpWrapper);
                break;
            case HttpMethods.DELETE:
                delete(httpWrapper);
                break;
            default:
                LOGGER.error("Unable to resolve request http method: " + method);
        }
    }

    public void get(HttpWrapper httpWrapper) {
        NotificationService.notify(httpWrapper, MessagesConstants.MESSAGE_404);
    }

    public void post(HttpWrapper httpWrapper) {
        NotificationService.notify(httpWrapper, MessagesConstants.MESSAGE_404);
    }

    public void put(HttpWrapper httpWrapper) {
        NotificationService.notify(httpWrapper, MessagesConstants.MESSAGE_404);
    }

    public void delete(HttpWrapper httpWrapper) {
        NotificationService.notify(httpWrapper, MessagesConstants.MESSAGE_404);
    }
}
