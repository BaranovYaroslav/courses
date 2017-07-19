package controller;

import controller.RegisterStudentController;
import controller.StudentRegistrationController;
import dispatcher.HttpWrapper;
import entities.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import service.UserService;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.mockito.Mockito.*;

/**
 * Tests for RegistrationController
 *
 * @see controller.StudentRegistrationController
 * @author Yaroslav Baranov
 */
@RunWith(MockitoJUnitRunner.class)
public class RegistrationControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private StudentRegistrationController registerController;

    @Test
    public void serviceInvocationTest() {
        HttpWrapper httpWrapper = createHttpWrapper("login", "full name", "mail@mail.com", "Qwer1234", "/app/registration");

        registerController.execute(httpWrapper);
        verify(userService).getUserByLogin("login");
    }

    @Test
    public void incorrectValidationTest() {
        HttpWrapper httpWrapper = createHttpWrapper("", "", "", "", "/app/registration");

        registerController.execute(httpWrapper);
        verify(userService, never()).addUser(new User());
    }

    private HttpWrapper createHttpWrapper(String... params) {
        HttpWrapper httpWrapper = new HttpWrapper();
        httpWrapper.setRequest(createHttpRequest(params));
        httpWrapper.setResponse(mock(HttpServletResponse.class));

        return httpWrapper;
    }

    private HttpServletRequest createHttpRequest (String... params) {
        HttpServletRequest request = mock(HttpServletRequest.class);

        when(request.getParameter("login")).thenReturn(params[0]);
        when(request.getParameter("fullName")).thenReturn(params[1]);
        when(request.getParameter("email")).thenReturn(params[2]);
        when(request.getParameter("password")).thenReturn(params[3]);
        when(request.getRequestDispatcher(params[4])).thenReturn(mock(RequestDispatcher.class));

        return request;
    }

}
