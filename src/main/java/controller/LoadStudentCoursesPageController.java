package controller;

import constants.NavigationConstants;
import constants.RequestAttribute;
import dispatcher.HttpWrapper;
import service.NavigationService;
import service.ServiceLoader;
import service.StudentService;

/**
 * Controller that load page with all courses of student.
 *
 * @author Yaroslav Baranov
 */
public class LoadStudentCoursesPageController implements Controller {

    private StudentService studentService = ServiceLoader.getInstance().getService(StudentService.class);

    /**
     * Method that student login from session and forward to page that contain all
     * courses of this student.
     *
     * @param httpWrapper holder of http request and response.
     * @see dispatcher.HttpWrapper
     */
    @Override
    public void execute(HttpWrapper httpWrapper) {
        String login = (String) httpWrapper.getRequest().getSession().getAttribute(RequestAttribute.USER);
        httpWrapper.getRequest().setAttribute(RequestAttribute.COURSES, studentService.getCoursesForStudent(login));
        NavigationService.navigateTo(httpWrapper, NavigationConstants.STUDENT_COURSES_PAGE);
    }
}
