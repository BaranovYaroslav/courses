package controller;

import constants.NavigationConstants;
import dispatcher.Controller;
import dispatcher.HttpWrapper;
import service.NavigationService;

import java.io.IOException;

public class LoadRegistrationPageController implements Controller{

    @Override
    public void execute(HttpWrapper httpWrapper) {
        NavigationService.navigateTo(httpWrapper, NavigationConstants.REGISTRATION_PAGE);
    }
}
