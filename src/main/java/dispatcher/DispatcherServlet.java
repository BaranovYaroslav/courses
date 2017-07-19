package dispatcher;

import constants.Messages;
import controller.*;
import org.apache.log4j.Logger;
import persistence.ConnectionManager;
import service.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

/**
 * Front servlet that process all requests from users.
 *
 * @author Yaroslav Baranov
 */
@WebServlet("/app/*")
public class DispatcherServlet extends HttpServlet {

    private static Logger LOGGER = Logger.getLogger(DispatcherServlet.class);

    private HttpMatcher httpMatcher;

    public DispatcherServlet() {}

    public DispatcherServlet(HttpMatcher httpMatcher) {
        this.httpMatcher = httpMatcher;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpWrapper httpWrapper = new HttpWrapper(req, resp);
        dispatchRequest(httpWrapper);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpWrapper httpWrapper = new HttpWrapper(req, resp);
        dispatchRequest(httpWrapper);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpWrapper httpWrapper = new HttpWrapper(req, resp);
        dispatchRequest(httpWrapper);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpWrapper httpWrapper = new HttpWrapper(req, resp);
        dispatchRequest(httpWrapper);
    }

    /**
     * Method that find and invoke controller according given url.
     *
     * @param httpWrapper holder of http request and response.
     */
    private void dispatchRequest(HttpWrapper httpWrapper) {
        Optional<Controller> controller = HttpMatcher.getInstance().getMatcherEntry(httpWrapper.getRequest().getPathInfo());
        if(controller.isPresent()) {
            controller.get().execute(httpWrapper);
        }
        else {
            LOGGER.warn("Try to access incorrect resource: " + httpWrapper.getRequest().getPathInfo());
            NotificationService.notify(httpWrapper, Messages.MESSAGE_404);
        }

        ConnectionManager connectionManager = ServiceLoader.getInstance().getService(ConnectionManager.class);
        if(connectionManager != null) {
            connectionManager.clean();
        }
    }
}
