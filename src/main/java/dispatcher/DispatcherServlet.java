package dispatcher;

import constants.MessagesConstants;
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
import java.util.List;

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

        DaoFactory daoFactory = new JdbcDaoFactory(ConnectionManager.fromJndi("jdbc/courses"));

        CourseService courseService = new CourseServiceImpl(daoFactory);
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

        httpMatcher = new ArrayList<HttpMatcherEntry>();

        addMatcherEntry("/", HttpMethod.GET, new BaseUrlController());
        addMatcherEntry("/registration", HttpMethod.GET, new LoadRegistrationPageController());
        addMatcherEntry("/registration/apply", HttpMethod.POST, new StudentRegistrationController());
        addMatcherEntry("/login", HttpMethod.GET, new LoadLoginPageController());
        addMatcherEntry("/login/apply", HttpMethod.GET, new LoginController());
        addMatcherEntry("/logout", HttpMethod.GET, new LogoutController());
        addMatcherEntry("/admin", HttpMethod.GET, new LoadAdminPageController());
        addMatcherEntry("/admin/update/course", HttpMethod.GET, new LoadEditCoursePageController());
        addMatcherEntry("/admin/update/course/save", HttpMethod.POST, new UpdateCourseController());
        addMatcherEntry("/admin/new-course", HttpMethod.GET, new LoadNewCoursePageController());
        addMatcherEntry("/admin/new-professor", HttpMethod.GET, new LoadNewProfessorPageController());
        addMatcherEntry("/admin/new-professor/save", HttpMethod.POST, new NewProfessorController());
        addMatcherEntry("/admin/delete-course", HttpMethod.POST, new DeleteCourseController());
        addMatcherEntry("/admin/new-course/save", HttpMethod.POST, new NewCourseController());
        addMatcherEntry("/admin/users", HttpMethod.GET, new LoadUsersListPageController());
        addMatcherEntry("/professor", HttpMethod.GET, new LoadProfessorPageController());
        addMatcherEntry("/professor/feedbacks", HttpMethod.GET, new LoadFeedbacksListPageController());
        addMatcherEntry("/professor/feedback", HttpMethod.GET, new LoadFeedbackPageController());
        addMatcherEntry("/professor/feedback/save", HttpMethod.POST, new SaveFeedbackController());
        addMatcherEntry("/student", HttpMethod.GET, new LoadStudentPageController());
        addMatcherEntry("/student/register", HttpMethod.POST, new RegisterStudentController());
        addMatcherEntry("/student/courses", HttpMethod.GET, new LoadStudentCoursesPageController());
        addMatcherEntry("/student/courses/unregister", HttpMethod.POST, new UnregisterStudentController());
        addMatcherEntry("/student/feedbacks", HttpMethod.GET, new LoadStudentsFeedbacksPageController());
        addMatcherEntry("/locale", HttpMethod.GET, new LocaleController());
        addMatcherEntry("/index", HttpMethod.GET, new IndexPageController());
        addMatcherEntry("/search", HttpMethod.GET, new CourseSearchController());
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
        HttpMatcherEntry entry = getMatcherEntry(httpWrapper.getRequest().getPathInfo(), httpWrapper.getRequest().getMethod());

        if(entry != null) {
            entry.executeController(httpWrapper);
        }
        else {
            NotificationService.notify(httpWrapper, MessagesConstants.MESSAGE_404);
        }
    }

    public List<HttpMatcherEntry> addMatcherEntry(String url, HttpMethod httpMethod, Controller controller){
        this.httpMatcher.add(new HttpMatcherEntry(url, httpMethod, controller));
        return this.httpMatcher;
    }

    public HttpMatcherEntry getMatcherEntry(String url, String method){
        for(HttpMatcherEntry entry: httpMatcher){
            if(entry.url.equals(url) && entry.httpMethod.getMethod().equals(method)){
                return entry;
            }
        }
        return null;
    }

    static class HttpMatcherEntry {

        private String url;

        private HttpMethod httpMethod;

        private final Controller controller;

        public HttpMatcherEntry (String url, HttpMethod httpMethod, Controller controller){
            this.url = url;
            this.httpMethod = httpMethod;
            this.controller = controller;
        }

        public void executeController(HttpWrapper httpWrapper){
            this.controller.execute(httpWrapper);
        }

        public String getUrl() {
            return url;
        }

        public HttpMethod getHttpMethod() {
            return httpMethod;
        }
    }

}
