package controller;

import dispatcher.Controller;
import dispatcher.HttpWrapper;
import service.NavigationService;

import java.io.IOException;

public class RegistrationPageController extends Controller{

    @Override
    public void get(HttpWrapper httpWrapper) {
        NavigationService.navigateTo(httpWrapper, "/pages/registration.jsp");
    }
}
