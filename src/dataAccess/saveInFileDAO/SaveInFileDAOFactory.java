package dataAccess.saveInFileDAO;

import dataAccess.DAOAbstractFactory;
import entities.Hotel;
import entities.Order;
import entities.Room;
import entities.User;

/**
 * Created by g.zubenko on 26.01.2017.
 */
public class SaveInFileDAOFactory implements DAOAbstractFactory {
    //SaveInFileDAOFactory realise singleton pattern
    private static SaveInFileDAOFactory instance;
    private SaveInFileDAOFactory(){}
    static public SaveInFileDAOFactory getInstance(){
        if (instance==null) instance=new SaveInFileDAOFactory();
        return  instance;
    }

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

    @Override
    public DAO<Order> getOrderDAO() {
        return new OrderDAO();
    }
}
