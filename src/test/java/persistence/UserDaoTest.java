package persistence;

import entities.Feedback;
import entities.Location;
import entities.Role;
import entities.User;
import org.junit.Before;
import org.junit.Test;
import persistence.dao.FeedbackDao;
import persistence.dao.LocationDao;
import persistence.dao.UserDao;
import persistence.dao.impl.FeedbackJdbcDao;
import persistence.dao.impl.LocationJdbcDao;
import persistence.dao.impl.UserJdbcDao;

import javax.jws.soap.SOAPBinding;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.Assert.*;

/**
 * Tests for UserDao
 *
 * @see persistence.dao.UserDao
 * @author Yaroslav Baranov
 */
public class UserDaoTest {
    private UserDao userDao;

    @Before
    public void setUp(){
        ConnectionManager connectionManager = H2Db.init("database.sql");
        userDao = new UserJdbcDao(connectionManager);
    }

    @Test
    public void addUserTest() {
        int currentUserNumber = userDao.findAll().size();
        userDao.add(createTestUserBuilder().build());

        assertEquals(currentUserNumber + 1, userDao.findAll().size());
    }

    @Test(expected = NoSuchElementException.class)
    public void deleteUserTest() {
        User.Builder builder = createTestUserBuilder();

        int id = userDao.add(builder.build());
        userDao.delete(builder.setId(id).build());

        userDao.find(id).get();
    }

    @Test
    public void updateUserTest() {
        User.Builder builder = createTestUserBuilder();

        int id = userDao.add(builder.build());

        User beforeUpdateUser = builder.setId(id).build();
        int afterUpdateId = userDao.update(builder.setId(id).setLogin("").build());
        User afterUpdateUser = userDao.find(afterUpdateId).get();

        assertNotEquals(beforeUpdateUser, afterUpdateUser);
    }

    @Test
    public void findUserByIdTest() {
        User.Builder builder = createTestUserBuilder();
        int id = userDao.add(builder.build());
        assertEquals(builder.setId(id).build(), userDao.find(id).get());
    }

    private User.Builder createTestUserBuilder() {
        User.Builder builder = User.newBuilder();

        builder.setFullName("Test User")
               .setLogin("tetUser")
               .setEmail("test@email.com")
               .setPassword("qwer1234")
               .setRole(new Role("admin"));

        return builder;
    }
}
