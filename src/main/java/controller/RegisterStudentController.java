package controller;

import constants.NavigationConstants;
import constants.RequestAttribute;
import constants.RequestParameter;
import dispatcher.HttpWrapper;
import entities.Course;
import entities.Student;
import entities.User;
import service.*;

/**
 * Controller that provide to student possibility to make registration to certain course.
 *
 * @author Yaroslav Baranov
 */
public class RegisterStudentController implements Controller{

    private CourseService courseService;

    private StudentService studentService;

    public RegisterStudentController() {
        courseService = ServiceLoader.getInstance().getService(CourseService.class);
        studentService = ServiceLoader.getInstance().getService(StudentService.class);
    }

    public RegisterStudentController(CourseService courseService, StudentService studentService) {
        this.courseService = courseService;
        this.studentService = studentService;
    }

    /**
     * Method that process registration and forward to student base page.
     *
     * @param httpWrapper holder of http request and response.
     * @see dispatcher.HttpWrapper
     */
    @Override
    public void execute(HttpWrapper httpWrapper) {
        String login = (String) httpWrapper.getRequest().getSession().getAttribute(RequestAttribute.USER);
        int courseId = Integer.parseInt(httpWrapper.getRequest().getParameter(RequestParameter.COURSE_ID));

        Course course = courseService.getCourse(courseId);
        Student student = studentService.getStudentByLogin(login);
        studentService.registerStudent(course, student);

        NavigationService.redirectTo(httpWrapper, NavigationConstants.STUDENT_ROOT_URL);
    }
}
