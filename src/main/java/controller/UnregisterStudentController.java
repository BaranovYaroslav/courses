package controller;

import constants.RequestAttribute;
import constants.RequestParameter;
import dispatcher.HttpWrapper;
import entities.Course;
import entities.Student;
import entities.User;
import service.*;

/**
 * Controller that provide to user possibility to unregister from course.
 *
 * @author Yaroslav Baranov
 */
public class UnregisterStudentController implements Controller {

    private CourseService courseService;

    private StudentService studentService;

    private FeedbackService feedbackService;

    public UnregisterStudentController() {
        courseService = ServiceLoader.getInstance().getService(CourseService.class);
        studentService = ServiceLoader.getInstance().getService(StudentService.class);
        feedbackService = ServiceLoader.getInstance().getService(FeedbackService.class);
    }


    /**
     * Method that unregister student from course. Parameters are passed by http request.
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

        studentService.unregisterStudent(course, student);
        feedbackService.deleteFeedback(course.getId(), student.getId());
    }
}
