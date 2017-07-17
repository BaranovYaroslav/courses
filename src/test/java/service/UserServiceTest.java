package service;

import entities.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import persistence.dao.UserDao;
import service.impl.UserServiceImpl;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Optional;

/**
 * Tests for UserService
 *
 * @see service.UserService
 * @author Yaroslav Baranov
 */
@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

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
        when(userDao.findByLogin("login")).thenReturn(Optional.of(new User()));
        assertNotEquals(userService.getUserByLogin("login"), null);
    }
}
