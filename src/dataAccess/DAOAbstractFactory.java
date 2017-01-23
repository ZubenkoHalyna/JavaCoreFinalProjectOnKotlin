package dataAccess;

import entities.Hotel;
import entities.Order;
import entities.Room;
import entities.User;

/**
 * Created by g.zubenko on 17.01.2017.
 */

public interface DAOAbstractFactory {
    DAOInterface<User> getUserDAO();
    DAOInterface<Hotel> getHotelDAO();
    DAOInterface<Room> getRoomDAO();
    DAOInterface<Order> getOrderDAO();
}

