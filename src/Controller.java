import dataAccess.AbstractDB;
import dataAccess.DAOInterface;
import entities.Hotel;
import entities.Order;
import entities.Room;
import entities.User;
import utils.DateUtil;

import java.util.*;

/**
 * Created by g.zubenko on 16.01.2017.
 */
public class Controller {
    private final AbstractDB DB;

    public Controller(AbstractDB DB) {
        this.DB = DB;
    }

    public List<Hotel> findHotelByName(String name){
        DAOInterface<Hotel> daObj = DB.getHotelDAO();
        Map<String, String> params = new HashMap<>();
        params.put(Hotel.FieldsForSearch.NAME.toString(),name);

        return daObj.select(params);
    }

    public List<Hotel> findHotelByCity(String city){
        DAOInterface<Hotel> daObj = DB.getHotelDAO();
        Map<String, String> params = new HashMap<>();
        params.put(Hotel.FieldsForSearch.CITY.toString(),city);

        return daObj.select(params);
    }

    public List<Room> findRoom(Map<String, String> params){

        return DB.getRoomDAO().select(params);
    }

    public User registerUser(String login, String password) {
        //TODO if cannot register, user has already registered.
        //TODO only english letters supported. Make some mask for login and password.

        User user = new User(login, password);
        DB.getUserDAO().insert(user);
        return user;

    }

    public Optional<Session> startProtectedSession(String login, String password){
        Map<String, String> params = new HashMap<>();
        params.put(User.FieldsForSearch.LOGIN.toString(),login);
        params.put(User.FieldsForSearch.PASSWORD.toString(),password);
        Optional<User> user = DB.getUserDAO().selectFirst(params);
        if (user.isPresent()){
            return Optional.of(new Session(user.get()));
        }
        else {
            return Optional.empty();
        }
    }

    public Order registerOrder(User user, Room room, Date startDate, Date endDate){
        Order order = new Order(user, room, startDate, endDate);
        DB.getOrderDAO().insert(order);
        return order;
    }

    public boolean isRoomFree(Room room, Date startDate, Date endDate){
        Map<String,String> params = new HashMap<>();
        params.put(Room.FieldsForSearch.START_DATE.toString(), DateUtil.dateToStr(startDate));
        params.put(Room.FieldsForSearch.END_DATE.toString(), DateUtil.dateToStr(endDate));
        params.put(Room.FieldsForSearch.ID.toString(), room.getId()+"");
        return DB.getRoomDAO().selectFirst(params).isPresent();
    }

    public List<Order> findOrdersByUser(User user){
        Map<String, String> params = new HashMap<>();
        params.put(Order.FieldsForSearch.USER_ID.toString(),user.getId()+"");
        return DB.getOrderDAO().select(params);
    }

    public void deleteOrder(Order order){
        DB.getOrderDAO().delete(order);
    }

    public AbstractDB getDB() {
        return DB;
    }
}
