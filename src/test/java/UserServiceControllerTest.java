import entities.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import persistence.dao.UserDao;
import persistence.dao.factory.DaoFactory;
import service.impl.UserServiceImpl;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Optional;

/**
 * Created by Ярослав on 05.07.2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class UserServiceControllerTest {

    @Mock UserDao userDao;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    public void testDaoInvocation() {
        userService.getAllUsers();
        verify(userDao).findAll();
    }

    @Test
    public void testGetAll() {
        when(userDao.findAll()).thenReturn(Arrays.asList(new User(), new User()));
        assertEquals(userService.getAllUsers().size(), 2);
    }

    @Test
    public void testGetUserByLogin() {
        when(userDao.getUser("login")).thenReturn(Optional.of(new User()));
        assertNotEquals(userService.getUserByLogin("login"), null);
    }
}
