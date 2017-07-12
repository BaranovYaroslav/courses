package controller;

import constants.NavigationConstants;
import dispatcher.Controller;
import dispatcher.HttpWrapper;
import org.apache.log4j.Logger;
import persistence.ConnectionManager;
import persistence.dao.UserDao;
import persistence.dao.factory.JdbcDaoFactory;
import persistence.transaction.Transaction;
import service.NavigationService;

/**
 * Created by Ярослав on 11.04.2017.
 */
public class LoadLoginPageController implements Controller {

    private static Logger LOGGER = Logger.getLogger(LoadLoginPageController.class);

    @Override
    public void execute(HttpWrapper httpWrapper) {
        NavigationService.navigateTo(httpWrapper, NavigationConstants.LOGIN_PAGE);
    }
}
