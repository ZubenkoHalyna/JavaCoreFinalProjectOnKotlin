package dataAccess.fileBased;

import dataAccess.AbstractDB;
import entities.InitialDataSupplier;
import exceptions.EntityNotFoundById;
import exceptions.ReadFromDBException;

/**
 * Created by g.zubenko on 26.01.2017.
 */
public class FileBasedDB implements AbstractDB {
    private HotelDAO hotelDAO;
    private UserDAO userDAO;
    private RoomDAO roomDAO;
    private OrderDAO orderDAO;
    private FileAccessInterface fileAccess;

    public FileBasedDB(FileAccessInterface fileAccess) {
        userDAO = new UserDAO(this);
        hotelDAO = new HotelDAO(this);
        roomDAO = new RoomDAO(this);
        orderDAO = new OrderDAO(this);
        this.fileAccess = fileAccess;
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
        getUserDAO().setCache(InitialDataSupplier.INSTANCE.getUsers());
        getUserDAO().writeCacheToFile();
        getHotelDAO().setCache(InitialDataSupplier.INSTANCE.getHotels());
        getHotelDAO().writeCacheToFile();
        getRoomDAO().setCache(InitialDataSupplier.INSTANCE.getRooms());
        getRoomDAO().writeCacheToFile();
        getOrderDAO().setCache(InitialDataSupplier.INSTANCE.getOrders());
        getOrderDAO().writeCacheToFile();
    }

    public FileAccessInterface getFileAccessObj() {
        return fileAccess;
    }
}
