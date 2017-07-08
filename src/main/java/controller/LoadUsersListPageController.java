package controller;

import constants.NavigationConstants;
import constants.RequestAttribute;
import dispatcher.Controller;
import dispatcher.HttpWrapper;
import org.apache.log4j.Logger;
import service.NavigationService;
import service.ServiceLoader;
import service.UserService;

/**
 * Created by Ярослав on 16.04.2017.
 */
public class LoadUsersListPageController implements Controller {

    private static Logger LOGGER = Logger.getLogger(LoadNewProfessorPageController.class);

    private UserService userService = ServiceLoader.getInstance().getService(UserService.class);

    @Override
    public void execute(HttpWrapper reqService) {

        reqService.getRequest().setAttribute(RequestAttribute.USERS, userService.getAllUsers());
        NavigationService.navigateTo(reqService, NavigationConstants.USERS_LIST_PAGE);
    }
}
