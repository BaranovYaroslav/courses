package dispatcher;

import org.apache.log4j.Logger;

/**
 * Created by Ярослав on 11.04.2017.
 */
public class Controller {

    private static Logger LOGGER = Logger.getLogger(Controller.class);

    public void execute(HttpWrapper httpWrapper) {
        String method = httpWrapper.getRequest().getMethod();

        switch (method) {
            case "GET":
                get(httpWrapper);
                break;
            case "POST":
                post(httpWrapper);
                break;
            case "PUT":
                put(httpWrapper);
                break;
            case "DELETE":
                delete(httpWrapper);
                break;
            default:
                LOGGER.error("Unable to resolve request http method: " + method);
        }
    }

    public void get(HttpWrapper reqService) {}

    public void post(HttpWrapper reqService) {}

    public void put(HttpWrapper reqService) {}

    public void delete(HttpWrapper reqService) {}
}
