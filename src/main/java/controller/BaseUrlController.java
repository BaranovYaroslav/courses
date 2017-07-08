package controller;

import constants.NavigationConstants;
import dispatcher.Controller;
import dispatcher.HttpWrapper;
import service.NavigationService;

/**
 * Created by Ярослав on 19.04.2017.
 */
public class BaseUrlController implements Controller {

    @Override
    public void execute(HttpWrapper httpWrapper) {
        NavigationService.redirectTo(httpWrapper, NavigationConstants.INDEX_PAGE);
    }
}
