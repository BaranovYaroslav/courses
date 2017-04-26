package controller;

import dispatcher.Controller;
import dispatcher.HttpWrapper;
import service.NavigationService;

/**
 * Created by Ярослав on 19.04.2017.
 */
public class BaseUrlController extends Controller {

    @Override
    public void get(HttpWrapper httpWrapper) {
        NavigationService.redirectTo(httpWrapper, "/index.jsp");
    }
}
