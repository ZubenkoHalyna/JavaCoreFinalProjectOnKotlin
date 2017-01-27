package dataAccess.mockDAO;

import dataAccess.AbstractDB;
import entities.InitialDataSupplier;
import entities.Hotel;
import entities.Order;
import entities.Room;
import entities.User;

/**
 * Created by g.zubenko on 17.01.2017.
 */
public class MockDB implements AbstractDB {
    private HotelDAO hotelDAO;
    private UserDAO userDAO;
    private RoomDAO roomDAO;
    private OrderDAO orderDAO;

    public MockDB() {
        hotelDAO = new HotelDAO();
        userDAO = new UserDAO();
        roomDAO = new RoomDAO();
        orderDAO = new OrderDAO();
        Initialize();
    }

    @Override
    public DAO<User> getUserDAO() {
        return userDAO;
    }

    @Override
    public DAO<Hotel> getHotelDAO() {
        return hotelDAO;
    }

    @Override
    public DAO<Room> getRoomDAO() {
        return roomDAO;
    }

    @Override
    public DAO<Order> getOrderDAO() {
        return orderDAO;
    }

    @Override
    public boolean dataIsCorrect() {
        // Mock data don't need to be checked
        return true;
    }

    @Override
    public void Initialize() {
        getUserDAO().insertAll(InitialDataSupplier.getInstance().getUsers());
        getHotelDAO().insertAll(InitialDataSupplier.getInstance().getHotels());
        getRoomDAO().insertAll(InitialDataSupplier.getInstance().getRooms());
        getOrderDAO().insertAll(InitialDataSupplier.getInstance().getOrders());
    }
}
