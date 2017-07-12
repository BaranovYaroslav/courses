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
 * Created by Ярослав on 12.07.2017.
 */
public class Application {

    private static Logger LOGGER = LogManager.getLogger(Application.class);

    private ServletContext servletContext;

    private ConnectionManager connectionManager;

    private DaoFactory daoFactory;

    protected void init() {
        initializeSecurity();
        initializePersistence();
        initializeServices();
        initializeDispatching();
    }

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

    public void initializePersistence() {
        connectionManager = ConnectionManager.fromJndi("jdbc/courses");
        daoFactory = new JdbcDaoFactory(connectionManager);
    }

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

    public void initializeDispatching() {
        HttpMatcher.Builder builder = HttpMatcher.newBuilder();

        builder.addEntry("/", HttpMethod.GET, new BaseUrlController())
               .addEntry("/registration", HttpMethod.GET, new LoadRegistrationPageController())
               .addEntry("/registration/apply", HttpMethod.POST, new StudentRegistrationController())
               .addEntry("/login", HttpMethod.GET, new LoadLoginPageController())
               .addEntry("/login/apply", HttpMethod.GET, new LoginController())
               .addEntry("/logout", HttpMethod.GET, new LogoutController())
               .addEntry("/admin", HttpMethod.GET, new LoadAdminPageController())
               .addEntry("/admin/update/course", HttpMethod.GET, new LoadEditCoursePageController())
               .addEntry("/admin/update/course/save", HttpMethod.POST, new UpdateCourseController())
               .addEntry("/admin/new-course", HttpMethod.GET, new LoadNewCoursePageController())
               .addEntry("/admin/new-professor", HttpMethod.GET, new LoadNewProfessorPageController())
               .addEntry("/admin/new-professor/save", HttpMethod.POST, new NewProfessorController())
               .addEntry("/admin/delete-course", HttpMethod.POST, new DeleteCourseController())
               .addEntry("/admin/new-course/save", HttpMethod.POST, new NewCourseController())
               .addEntry("/admin/users", HttpMethod.GET, new LoadUsersListPageController())
               .addEntry("/professor", HttpMethod.GET, new LoadProfessorPageController())
               .addEntry("/professor/feedbacks", HttpMethod.GET, new LoadFeedbacksListPageController())
               .addEntry("/professor/feedback", HttpMethod.GET, new LoadFeedbackPageController())
               .addEntry("/professor/feedback/save", HttpMethod.POST, new SaveFeedbackController())
               .addEntry("/student", HttpMethod.GET, new LoadStudentPageController())
               .addEntry("/student/register", HttpMethod.POST, new RegisterStudentController())
               .addEntry("/student/courses", HttpMethod.GET, new LoadStudentCoursesPageController())
               .addEntry("/student/courses/unregister", HttpMethod.POST, new UnregisterStudentController())
               .addEntry("/student/feedbacks", HttpMethod.GET, new LoadStudentsFeedbacksPageController())
               .addEntry("/locale", HttpMethod.GET, new LocaleController())
               .addEntry("/index", HttpMethod.GET, new IndexPageController())
               .addEntry("/search", HttpMethod.GET, new CourseSearchController());

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
