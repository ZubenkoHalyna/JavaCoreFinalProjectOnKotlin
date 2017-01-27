package dataAccess.mockDAO;

import dataAccess.AbstractDB;
import entities.InitialDataSupplier;

/**
 * Created by g.zubenko on 17.01.2017.
 */
public class MockDB implements AbstractDB {
    private HotelDAO hotelDAO;
    private UserDAO userDAO;
    private RoomDAO roomDAO;
    private OrderDAO orderDAO;

    public MockDB() {
        hotelDAO = new HotelDAO(this);
        userDAO = new UserDAO(this);
        roomDAO = new RoomDAO(this);
        orderDAO = new OrderDAO(this);
        Initialize();
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
