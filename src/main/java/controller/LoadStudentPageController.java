package controller;

import dispatcher.Controller;
import dispatcher.HttpWrapper;
import persistence.dao.UserDao;
import service.NavigationService;
import service.ServiceLoader;
import service.StudentService;
import service.UserService;
import service.impl.StudentServiceImpl;
import service.impl.UserServiceImpl;

/**
 * Created by Ярослав on 18.04.2017.
 */
public class LoadStudentPageController extends Controller {

    private UserService userService = ServiceLoader.getInstance().getService(UserService.class);

    private StudentService studentService = ServiceLoader.getInstance().getService(StudentService.class);

    @Override
    public void get(HttpWrapper reqService) {
        String login = (String) reqService.getRequest().getSession().getAttribute("user");
        reqService.getRequest().setAttribute("coursesForRegistration",
                                             studentService.getCoursesForRegistration(userService.getUserByLogin(login)));
        NavigationService.navigateTo(reqService, "/pages/student/student.jsp");
    }
}
