package controller;

import constants.NavigationConstants;
import constants.RequestAttribute;
import dispatcher.HttpWrapper;
import org.apache.log4j.Logger;
import service.NavigationService;
import service.ServiceLoader;
import service.UserService;

/**
 * Controller that provide to admin possibility to see information about all users.
 *
 * @author Yaroslav Baranov
 */
public class LoadUsersListPageController implements Controller {

    private static Logger LOGGER = Logger.getLogger(LoadNewProfessorPageController.class);

    private UserService userService;

    public LoadUsersListPageController() {
        userService = ServiceLoader.getInstance().getService(UserService.class);
    }

    public LoadUsersListPageController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Method that forward to page that contains list of users.
     *
     * @param httpWrapper holder of http request and response.
     * @see dispatcher.HttpWrapper
     */
    @Override
    public void execute(HttpWrapper httpWrapper) {
        httpWrapper.getRequest().setAttribute(RequestAttribute.USERS, userService.getAllUsers());
        NavigationService.navigateTo(httpWrapper, NavigationConstants.USERS_LIST_PAGE);
    }
}
