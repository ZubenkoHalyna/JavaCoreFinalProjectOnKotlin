package dataAccess.saveInFileDAO;

import dataAccess.AbstractDB;
import entities.InitialDataSupplier;
import entities.Hotel;
import entities.Order;
import entities.Room;
import entities.User;
import exceptions.EntityNotFoundById;
import exceptions.ReadFromDBException;

/**
 * Created by g.zubenko on 26.01.2017.
 */
public class SaveInFileDB implements AbstractDB {
    private HotelDAO hotelDAO;
    private UserDAO userDAO;
    private RoomDAO roomDAO;
    private OrderDAO orderDAO;

    public SaveInFileDB() {
        hotelDAO = new HotelDAO();
        userDAO = new UserDAO();
        roomDAO = new RoomDAO();
        orderDAO = new OrderDAO();
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
        try {
            getUserDAO().readCacheFromFile();
            getHotelDAO().readCacheFromFile();
            getRoomDAO().readCacheFromFile();
            getOrderDAO().readCacheFromFile();
        } catch (ReadFromDBException | EntityNotFoundById e) {
            return false;
        }
        return true;
    }

    @Override
    public void Initialize() {
        getUserDAO().setCache(InitialDataSupplier.getInstance().getUsers());
        getUserDAO().writeCacheToFile();
        getHotelDAO().setCache(InitialDataSupplier.getInstance().getHotels());
        getHotelDAO().writeCacheToFile();
        getRoomDAO().setCache(InitialDataSupplier.getInstance().getRooms());
        getRoomDAO().writeCacheToFile();
        getOrderDAO().setCache(InitialDataSupplier.getInstance().getOrders());
        getOrderDAO().writeCacheToFile();
    }
}
