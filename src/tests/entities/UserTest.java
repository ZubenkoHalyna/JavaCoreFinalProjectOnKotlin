package tests.entities;

import entities.User;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by g.zubenko on 28.01.2017.
 */
public class UserTest {
    @Test
    public void getLogin() throws Exception {
        User u = new User("login","");
        assertEquals("Method getLogin in class User doesn't work correct","login",u.getLogin());
    }

    @Test
    public void setLogin() throws Exception {
        User u = new User("","");
        u.setLogin("login");
        assertEquals("Method setLogin in class User doesn't work correct","login",u.getLogin());
    }

    @Test
    public void getPassword() throws Exception {
        User u = new User("","password");
        assertEquals("Method getPassword in class User doesn't work correct","password",u.getPassword());
    }

    @Test
    public void setPassword() throws Exception {
        User u = new User("","");
        u.setPassword("password");
        assertEquals("Method setPassword in class User doesn't work correct","password",u.getPassword());
    }

    @Test
    public void getView() throws Exception {
        User u = new User("login","password");
        assertEquals("Method getView in class User doesn't work correct","login",u.getView());
    }

    @Test
    public void toStringTest() throws Exception {
        User u = new User("login","password");
        assertEquals("Method toString in class User doesn't work correct","User{login='login'}",u.toString());
    }
}