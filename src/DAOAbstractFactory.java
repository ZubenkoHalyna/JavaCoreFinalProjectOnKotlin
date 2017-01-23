import dataAccess.mockDAO.DAO;
import entities.Hotel;
import entities.Room;
import entities.User;

/**
 * Created by g.zubenko on 17.01.2017.
 */

public interface DAOAbstractFactory {
    DAO<User> getUserDAO();
    DAO<Hotel> getHotelDAO();
    DAO<Room> getRoomDAO();
}

