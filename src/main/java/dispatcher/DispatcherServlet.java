package dispatcher;

import constants.Messages;
import controller.*;
import org.apache.log4j.Logger;
import persistence.ConnectionManager;
import persistence.dao.factory.DaoFactory;
import persistence.dao.factory.JdbcDaoFactory;
import security.BaseResourceToRoleMapper;
import security.ResourceToRoleMapper;
import security.UserRole;
import service.*;
import service.impl.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    private void dispatchRequest(HttpWrapper httpWrapper) {
        Optional<HttpMatcher.HttpMatcherEntry> entry = HttpMatcher.getInstance()
                                                                  .getMatcherEntry(httpWrapper.getRequest().getPathInfo());

        if(entry.isPresent()) {
            entry.get().executeController(httpWrapper);
        }
        else {
            NotificationService.notify(httpWrapper, Messages.MESSAGE_404);
        }

        ConnectionManager connectionManager = ServiceLoader.getInstance().getService(ConnectionManager.class);
        if(connectionManager != null) {
            connectionManager.clean();
        }
    }
}
