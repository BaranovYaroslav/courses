package controller;

import dispatcher.Controller;
import dispatcher.HttpWrapper;
import org.apache.log4j.Logger;
import service.NavigationService;

/**
 * Created by Ярослав on 11.04.2017.
 */
public class LoadLoginPageController extends Controller {

    private static Logger LOGGER = Logger.getLogger(LoadLoginPageController.class);

    @Override
    public void get(HttpWrapper httpWrapper) {
        NavigationService.navigateTo(httpWrapper, "/pages/login.jsp");
    }
}
