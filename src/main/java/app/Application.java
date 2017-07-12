package app;

import controller.*;
import dispatcher.HttpMatcher;
import dispatcher.HttpMethod;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import persistence.ConnectionManager;
import persistence.dao.factory.DaoFactory;
import persistence.dao.factory.JdbcDaoFactory;
import security.BaseResourceToRoleMapper;
import security.ResourceToRoleMapper;
import security.UserRole;
import service.*;
import service.impl.*;

import javax.servlet.ServletContext;
import java.util.ArrayList;

/**
 * Util class to make configuration of application on start up.
 *
 * @author Yaroslav Baranov
 */

public class Application {

    private ServletContext servletContext;

    private ConnectionManager connectionManager;

    private DaoFactory daoFactory;

    /**
     * Method that provide initialization of application.
     */
    protected void init() {
        initializeSecurity();
        initializePersistence();
        initializeServices();
        initializeDispatching();
    }

    /**
     * Method that provide initialization of util classes for security aspect of application.
     *
     * @see security
     */
    public void initializeSecurity() {
        ResourceToRoleMapper.getInstance()
                .addConstrains("/admin", UserRole.ADMIN)
                .addConstrains("/student", UserRole.STUDENT)
                .addConstrains("/professor", UserRole.PROFESSOR);

        BaseResourceToRoleMapper.getInstance()
                .addMapping(UserRole.STUDENT, "/app/student")
                .addMapping(UserRole.PROFESSOR, "/app/professor")
                .addMapping(UserRole.ADMIN, "/app/admin");
    }

    /**
     * Method that provide initialization of persistence.
     *
     * @see persistence
     */
    public void initializePersistence() {
        connectionManager = ConnectionManager.fromJndi("jdbc/courses");
        daoFactory = new JdbcDaoFactory(connectionManager);
    }

    /**
     * Method that provide initialization of services.
     *
     * @see service
     * @see service.ServiceLoader
     */
    public void initializeServices() {
        CourseService courseService = new CourseServiceImpl(daoFactory, connectionManager);
        UserService userService = new UserServiceImpl(daoFactory.getUserDao());
        FeedbackService feedbackService = new FeedbackServiceImpl(daoFactory);
        StudentService studentService = new StudentServiceImpl(daoFactory);
        AuthenticationService authenticationService = new AuthenticationServiceImpl(daoFactory);
        InformationService informationService = new InformationServiceImpl(daoFactory);

        ServiceLoader.getInstance().loadService(CourseService.class, courseService);
        ServiceLoader.getInstance().loadService(UserService.class, userService);
        ServiceLoader.getInstance().loadService(FeedbackService.class, feedbackService);
        ServiceLoader.getInstance().loadService(StudentService.class, studentService);
        ServiceLoader.getInstance().loadService(AuthenticationService.class, authenticationService);
        ServiceLoader.getInstance().loadService(InformationService.class, informationService);
        ServiceLoader.getInstance().loadService(ConnectionManager.class, connectionManager);
    }

    /**
     * Function that provide initialization of util class for request dispatching.
     *
     * @see controller
     * @see dispatcher.HttpMatcher
     * @see dispatcher.DispatcherServlet
     */
    public void initializeDispatching() {
        HttpMatcher.Builder builder = HttpMatcher.newBuilder();

        builder.addEntry("/", new BaseUrlController())
               .addEntry("/registration", new LoadRegistrationPageController())
               .addEntry("/registration/apply", new StudentRegistrationController())
               .addEntry("/login", new LoadLoginPageController())
               .addEntry("/login/apply", new LoginController())
               .addEntry("/logout", new LogoutController())
               .addEntry("/admin", new LoadAdminPageController())
               .addEntry("/admin/update/course", new LoadEditCoursePageController())
               .addEntry("/admin/update/course/save", new UpdateCourseController())
               .addEntry("/admin/new-course", new LoadNewCoursePageController())
               .addEntry("/admin/new-professor", new LoadNewProfessorPageController())
               .addEntry("/admin/new-professor/save", new NewProfessorController())
               .addEntry("/admin/delete-course", new DeleteCourseController())
               .addEntry("/admin/new-course/save", new NewCourseController())
               .addEntry("/admin/users", new LoadUsersListPageController())
               .addEntry("/professor", new LoadProfessorPageController())
               .addEntry("/professor/feedbacks", new LoadFeedbacksListPageController())
               .addEntry("/professor/feedback", new LoadFeedbackPageController())
               .addEntry("/professor/feedback/save", new SaveFeedbackController())
               .addEntry("/student", new LoadStudentPageController())
               .addEntry("/student/register", new RegisterStudentController())
               .addEntry("/student/courses", new LoadStudentCoursesPageController())
               .addEntry("/student/courses/unregister", new UnregisterStudentController())
               .addEntry("/student/feedbacks", new LoadStudentsFeedbacksPageController())
               .addEntry("/locale", new LocaleController())
               .addEntry("/index", new IndexPageController())
               .addEntry("/search", new CourseSearchController());

        builder.build();
    }

    public ServletContext getServletContext() {
        return servletContext;
    }

    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    public ConnectionManager getConnectionManager() {
        return connectionManager;
    }

    public void setConnectionManager(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }
}
