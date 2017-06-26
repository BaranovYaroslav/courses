package dispatcher;

import application.MessagesConstants;
import controller.*;
import org.apache.log4j.Logger;
import persistence.ConnectionManager;
import persistence.dao.factory.DaoFactory;
import persistence.dao.factory.JdbcDaoFactory;
import security.BaseResourceToRoleMapper;
import security.ResourceToRoleMapper;
import security.UserRoles;
import service.*;
import service.impl.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/app/*")
public class DispatcherServlet extends HttpServlet {

    private static Logger LOGGER = Logger.getLogger(DispatcherServlet.class);

    private ArrayList<HttpMatcherEntry> httpMatcher;

    public DispatcherServlet() {

        ResourceToRoleMapper.getInstance()
                            .appendMapping("/admin", UserRoles.ADMIN)
                            .appendMapping("/student", UserRoles.STUDENT)
                            .appendMapping("/professor", UserRoles.PROFESSOR);

        BaseResourceToRoleMapper.getInstance()
                            .addMapping(UserRoles.STUDENT, "/app/student")
                            .addMapping(UserRoles.PROFESSOR, "/app/professor")
                            .addMapping(UserRoles.ADMIN, "/app/admin");

        DaoFactory daoFactory = new JdbcDaoFactory(ConnectionManager.fromJndi("jdbc/project"));

        CourseService courseService = new CourseServiceImpl(daoFactory);
        UserService userService = new UserServiceImpl(daoFactory);
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

        httpMatcher = new ArrayList<HttpMatcherEntry>();
        addMatcherEntry("/", new BaseUrlController());
        addMatcherEntry("/registration", new RegistrationPageController());
        addMatcherEntry("/registration/apply", new StudentRegistrationController());
        addMatcherEntry("/login", new LoadLoginPageController());
        addMatcherEntry("/login/apply", new LoginController());
        addMatcherEntry("/logout", new LogoutController());
        addMatcherEntry("/admin", new LoadAdminPageController());
        addMatcherEntry("/admin/update/course", new LoadEditCoursePageController());
        addMatcherEntry("/admin/update/course/save", new UpdateCourseController());
        addMatcherEntry("/admin/new-course", new LoadNewCoursePageController());
        addMatcherEntry("/admin/new-professor", new LoadNewProfessorPageController());
        addMatcherEntry("/admin/new-professor/save", new NewProfessorController());
        addMatcherEntry("/admin/delete-course", new DeleteCourseController());
        addMatcherEntry("/admin/new-course/save", new NewCourseController());
        addMatcherEntry("/admin/users", new LoadUsersListPageController());
        addMatcherEntry("/professor", new LoadProfessorPageController());
        addMatcherEntry("/professor/feedbacks", new LoadFeedbacksListPageController());
        addMatcherEntry("/professor/feedback", new LoadFeedbackPageController());
        addMatcherEntry("/professor/feedback/save", new SaveFeedbackController());
        addMatcherEntry("/student", new LoadStudentPageController());
        addMatcherEntry("/student/register", new RegisterStudentController());
        addMatcherEntry("/student/courses", new LoadStudentCoursesPageController());
        addMatcherEntry("/student/courses/unregister", new UnregisterStudentController());
        addMatcherEntry("/student/feedbacks", new LoadStudentsFeedbacksPageController());
        addMatcherEntry("/locale", new LocaleController());
        addMatcherEntry("/index", new IndexPageController());
        addMatcherEntry("/search", new CourseSearchController());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpWrapper httpWrapper = new HttpWrapper(req, resp);
        dispatchRequest(httpWrapper);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpWrapper httpWrapper = new HttpWrapper(req, resp);
        dispatchRequest(httpWrapper);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpWrapper httpWrapper = new HttpWrapper(req, resp);
        dispatchRequest(httpWrapper);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpWrapper httpWrapper = new HttpWrapper(req, resp);
        dispatchRequest(httpWrapper);
    }

    private void dispatchRequest(HttpWrapper httpWrapper) {
        HttpMatcherEntry entry = getMatcherEntry(httpWrapper.getRequest().getPathInfo());

        if(entry != null) {
            entry.executeController(httpWrapper);
        }
        else {
            NotificationService.notify(httpWrapper, MessagesConstants.MESSAGE_404);
        }
    }

    public void addMatcherEntry(String url, Controller controller){
        this.httpMatcher.add(new HttpMatcherEntry(url, controller));
    }

    public HttpMatcherEntry getMatcherEntry(String url){
        for(HttpMatcherEntry entry: httpMatcher){
            if(entry.getUrl().equals(url)){
                return entry;
            }
        }
        return null;
    }

    static class HttpMatcherEntry {

        private String url;

        private final Controller controller;

        public HttpMatcherEntry (String url, Controller controller){
            this.url = url;
            this.controller = controller;
        }

        public void executeController(HttpWrapper httpWrapper){
            this.controller.execute(httpWrapper);
        }

        public String getUrl() {
            return url;
        }
    }

}
