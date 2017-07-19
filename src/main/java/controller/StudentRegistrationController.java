package controller;

import constants.*;
import dispatcher.HttpWrapper;
import entities.Student;
import entities.User;
import entities.UserRole;
import org.apache.log4j.Logger;
import service.NavigationService;
import service.ServiceLoader;
import service.UserService;
import util.EncodingProvider;
import javax.servlet.http.HttpServletRequest;

/**
 * Controller provide possibility to create student account.
 *
 * @author Yaroslav Baranov
 */
public class StudentRegistrationController implements Controller {

    private static Logger LOGGER = Logger.getLogger(StudentRegistrationController.class);

    private UserService userService;

    public StudentRegistrationController() {
        userService = ServiceLoader.getInstance().getService(UserService.class);
    }

    public StudentRegistrationController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Method process registration.
     *
     * @param httpWrapper holder of http request and response.
     * @see dispatcher.HttpWrapper
     */
    @Override
    public void execute(HttpWrapper httpWrapper) {
        if(validateInputData(httpWrapper)) {
            String login = httpWrapper.getRequest().getParameter(RequestParameter.LOGIN);

            if (userService.getUserByLogin(login) == null) {
                Student student = constructStudent(httpWrapper.getRequest());
                userService.addUser(student);
                NavigationService.redirectTo(httpWrapper, ApplicationConstants.BASE_APPLICATION_URL);
            } else {
                returnToPreviousPage(httpWrapper, Messages.ON_LOGIN_OCCUPIED_MESSAGE);
            }
        }
        else {
            returnToPreviousPage(httpWrapper, Messages.INCORRECT_INPUT_DATA_MESSAGE);
        }
    }

    /**
     * Method that construct new student from request parameters.
     *
     * @param request http request that contains parameters of new course.
     */
    private Student constructStudent(HttpServletRequest request) {
        Student.Builder builder = Student.newBuilder();

        builder.setLogin(request.getParameter(RequestParameter.LOGIN))
               .setFullName(request.getParameter(RequestParameter.FULL_NAME))
               .setEmail(request.getParameter(RequestParameter.EMAIL))
               .setPassword(EncodingProvider.encode(request.getParameter(RequestParameter.PASSWORD)))
               .setRole(UserRole.STUDENT);

        return builder.build();
    }

    /**
     * Method that provide validation of request parameters.
     *
     * @param httpWrapper holder of http request and response.
     * @see dispatcher.HttpWrapper
     * @see constants.ValidationConstants
     */
    private boolean validateInputData(HttpWrapper httpWrapper) {
        HttpServletRequest request = httpWrapper.getRequest();

        String login = request.getParameter(RequestParameter.LOGIN);
        String fullName = request.getParameter(RequestParameter.FULL_NAME);
        String email = request.getParameter(RequestParameter.EMAIL);
        String password = request.getParameter(RequestParameter.PASSWORD);

        return login.matches(ValidationConstants.LOGIN_REGEX) &&
               fullName.matches(ValidationConstants.NAME_REGEX) &&
               email.matches(ValidationConstants.EMAIL_REGEX) &&
               password.matches(ValidationConstants.PASSWORD_REGEX);
    }

    private void returnToPreviousPage(HttpWrapper httpWrapper, String message) {
        HttpServletRequest request = httpWrapper.getRequest();
        String login = request.getParameter(RequestParameter.LOGIN);
        String fullName = request.getParameter(RequestParameter.FULL_NAME);
        String email = request.getParameter(RequestParameter.EMAIL);

        request.setAttribute(RequestAttribute.PREVIOUS_LOGIN, login);
        request.setAttribute(RequestAttribute.PREVIOUS_FULL_NAME, fullName);
        request.setAttribute(RequestAttribute.PREVIOUS_EMAIL, email);
        request.setAttribute(RequestAttribute.MESSAGE, message);

        NavigationService.navigateTo(httpWrapper, NavigationConstants.STUDENT_REGISTRATION_URL);
    }
}
