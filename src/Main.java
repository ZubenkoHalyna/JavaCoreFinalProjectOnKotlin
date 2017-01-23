import dataAccess.mockDAO.DAO;
import entities.User;
import dataAccess.mockDAO.UserDAO;

/**
 * Created by g.zubenko on 23.01.2017.
 */
public class Main {
    public static void main(String[] args) {
        DAO<User> dao = new UserDAO();
        dao.update(new User(1,"",""));
    }
}
