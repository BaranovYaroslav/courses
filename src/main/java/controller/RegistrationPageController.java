package controller;

import dispatcher.Controller;
import dispatcher.HttpWrapper;
import service.NavigationService;

import java.io.IOException;

public class RegistrationPageController implements Controller{

    @Override
    public void execute(HttpWrapper httpWrapper) {
        NavigationService.navigateTo(httpWrapper, "/pages/registration.jsp");
    }
}
