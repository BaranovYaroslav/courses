package controller;

import dispatcher.Controller;
import dispatcher.HttpWrapper;
import org.apache.log4j.Logger;
import service.NavigationService;

/**
 * Created by Ярослав on 11.04.2017.
 */
public class LoadLoginPageController implements Controller {

    private static Logger LOGGER = Logger.getLogger(LoadLoginPageController.class);

    @Override
    public void execute(HttpWrapper httpWrapper) {
        NavigationService.navigateTo(httpWrapper, "/pages/login.jsp");
    }
}
