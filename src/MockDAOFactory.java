import dataAccess.mockDAO.DAO;
import dataAccess.mockDAO.HotelDAO;
import dataAccess.mockDAO.RoomDAO;
import dataAccess.mockDAO.UserDAO;
import entities.Hotel;
import entities.Room;
import entities.User;

/**
 * Created by g.zubenko on 17.01.2017.
 */
public class MockDAOFactory implements DAOAbstractFactory {
    @Override
    public DAO<User> getUserDAO() {
        return new UserDAO();
    }

    @Override
    public DAO<Hotel> getHotelDAO() {
        return new HotelDAO();
    }

    @Override
    public DAO<Room> getRoomDAO() {
        return new RoomDAO();
    }
}
