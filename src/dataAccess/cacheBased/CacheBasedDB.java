package dataAccess.cacheBased;

import dataAccess.AbstractDB;
import entities.InitialDataSupplier;

/**
 * Created by g.zubenko on 17.01.2017.
 */
public class CacheBasedDB implements AbstractDB {
    private HotelDAO hotelDAO;
    private UserDAO userDAO;
    private RoomDAO roomDAO;
    private OrderDAO orderDAO;

    public CacheBasedDB() {
        hotelDAO = new HotelDAO(this);
        userDAO = new UserDAO(this);
        roomDAO = new RoomDAO(this);
        orderDAO = new OrderDAO(this);
    }

    @Override
    public UserDAO getUserDAO() {
        return userDAO;
    }

    @Override
    public HotelDAO getHotelDAO() {
        return hotelDAO;
    }

    @Override
    public RoomDAO getRoomDAO() {
        return roomDAO;
    }

    @Override
    public OrderDAO getOrderDAO() {
        return orderDAO;
    }

    @Override
    public boolean dataIsCorrect() {
        if (getRoomDAO().selectAll().isEmpty() || getHotelDAO().selectAll().isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void Initialize() {
        getUserDAO().setCache(InitialDataSupplier.getInstance().getUsers());
        getHotelDAO().setCache(InitialDataSupplier.getInstance().getHotels());
        getRoomDAO().setCache(InitialDataSupplier.getInstance().getRooms());
        getOrderDAO().setCache(InitialDataSupplier.getInstance().getOrders());
    }
}
