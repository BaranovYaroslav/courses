package controller;

import dispatcher.Controller;
import dispatcher.HttpWrapper;
import service.NavigationService;

/**
 * Created by Ярослав on 15.04.2017.
 */
public class LoadNewProfessorPageController implements Controller {
    @Override
    public void execute(HttpWrapper reqService) {
        NavigationService.navigateTo(reqService, "/pages/admin/new-professor.jsp");
    }
}
