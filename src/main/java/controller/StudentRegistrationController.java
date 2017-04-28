package controller;

import application.ApplicationConstants;
import dispatcher.Controller;
import dispatcher.HttpWrapper;
import entities.Role;
import entities.Student;
import persistence.ConnectionManager;
import persistence.dao.UserDao;
import persistence.dao.factory.JdbcDaoFactory;

import org.apache.log4j.Logger;
import security.UserRoles;
import service.NavigationService;
import service.ServiceLoader;
import service.StudentService;
import service.UserService;
import service.impl.UserServiceImpl;
import util.EncodingProvider;

import javax.servlet.http.HttpServletRequest;


public class StudentRegistrationController extends Controller {

    private static Logger LOGGER = Logger.getLogger(StudentRegistrationController.class);

    private UserService userService = ServiceLoader.getInstance().getService(UserService.class);

    @Override
    public void get(HttpWrapper httpWrapper) {

        String login = httpWrapper.getRequest().getParameter("login");

        if(userService.getUserByLogin(login) == null){
            Student student = constructStudent(httpWrapper.getRequest());
            userService.addUser(student);
            NavigationService.navigateTo(httpWrapper, ApplicationConstants.BASE_APPLICATION_URL);
        }

        else {
            httpWrapper.getRequest().setAttribute("message", "Selected login already in use!");
            NavigationService.navigateTo(httpWrapper, "/app/registration");
        }
    }

    private Student constructStudent(HttpServletRequest request) {
        Student student = new Student();

        student.setLogin(request.getParameter("login"));
        student.setFullName(request.getParameter("fullName"));
        student.setEmail(request.getParameter("email"));
        student.setPassword(EncodingProvider.encode(request.getParameter("password")));
        student.setRole(new Role(UserRoles.STUDENT));

        return student;
    }
}
