package constants;

/**
 * Holder of navigation constants.
 *
 * @author Yaroslav Baranov
 */
public interface NavigationConstants {
    String INDEX_PAGE = "/index.jsp";
    String ADMIN_PAGE = "/pages/admin/admin.jsp";
    String PROFESSOR_PAGE = "/pages/professor/professor.jsp";
    String STUDENT_PAGE =  "/pages/student/student.jsp";
    String EDIT_FEEDBACK_PAGE = "/pages/professor/feedback.jsp";
    String COURSE_FEEDBACKS_LIST_PAGE = "/pages/professor/course-feedbacks.jsp";
    String LOGIN_PAGE =  "/pages/login.jsp";
    String REGISTRATION_PAGE = "/pages/registration.jsp";
    String NEW_COURSE_PAGE = "/pages/admin/new-course.jsp";
    String EDIT_COURSE_PAGE = "/pages/admin/edit-course.jsp";
    String NEW_PROFESSOR_PAGE = "/pages/admin/new-professor.jsp";
    String STUDENT_COURSES_PAGE = "/pages/student/student-courses.jsp";
    String STUDENT_FEEDBACKS_PAGE = "/pages/student/student-feedbacks.jsp";
    String USERS_LIST_PAGE = "/pages/admin/users-list.jsp";
    String NOTIFICATION_PAGE = "/pages/notification.jsp";

    String ADMIN_ROOT_URL = "/app/admin";
    String STUDENT_ROOT_URL = "/app/student";
    String LOGIN_URL = "/app/login";
    String STUDENT_REGISTRATION_URL = "/app/registration";
    String FEEDBACKS_FOR_COURSE_URL = "/app/professor/feedbacks?id=";
    String FEEDBACK_URL = "/app/professor/feedback?id=";
    String BASE_APPLICATION_URL = "/pro";
}
