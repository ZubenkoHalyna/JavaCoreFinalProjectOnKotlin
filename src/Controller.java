import dataAccess.DAOAbstractFactory;
import dataAccess.DAOInterface;
import entities.Hotel;
import entities.Order;
import entities.Room;
import entities.User;
import identification.IdProvider;
import utils.DateUtil;

import java.util.*;

/**
 * Created by g.zubenko on 16.01.2017.
 */
public class Controller {
    private final IdProvider idProvider;
    private final DAOAbstractFactory DAOProvider;

    public Controller(IdProvider idProvider, DAOAbstractFactory DAOProvider) {
        this.idProvider = idProvider;
        this.DAOProvider = DAOProvider;
    }

    public boolean addHotel(String name, String city){
        DAOInterface<Hotel> daObj = DAOProvider.getHotelDAO();

        daObj.insert(new Hotel(idProvider.getNewId(), name,city));
        return true;
    }

    public Collection<Hotel> findHotelByName(String name){
        DAOInterface<Hotel> daObj = DAOProvider.getHotelDAO();
        Map<String, String> params = new HashMap<>();
        params.put(Hotel.Fields.NAME.toString(),name);

        return daObj.select(params);
    }

    public Collection<Hotel> findHotelByCity(String city){
        DAOInterface<Hotel> daObj = DAOProvider.getHotelDAO();
        Map<String, String> params = new HashMap<>();
        params.put(Hotel.Fields.CITY.toString(),city);

        return daObj.select(params);
    }

    public Collection<Room> findRoom(Map<String, String> params){

        return DAOProvider.getRoomDAO().select(params);
    }

    public User registerUser(String login, String password) {
        //TODO if cannot register, user has already registered.
        //TODO only english letters supported. Make some mask for login and password.
        if (login.isEmpty()) {
            throw new IllegalArgumentException("Login cannot be empty");
        } else {
            User user = new User(idProvider.getNewId(), login, password);
            DAOProvider.getUserDAO().insert(user);
            return user;
        }
    }

    public Optional<Session> startProtectedSession(String login, String password){
        Map<String, String> params = new HashMap<>();
        params.put(User.Fields.LOGIN.toString(),login);
        params.put(User.Fields.PASSWORD.toString(),password);
        Optional<User> user = DAOProvider.getUserDAO().selectFirst(params);
        if (user.isPresent()){
            return Optional.of(new Session(user.get()));
        }
        else {
            return Optional.empty();
        }
    }

    public Order registerOrder(User user, Room room, Date startDate, Date endDate){
        Order order = new Order(idProvider.getNewId(),user.getId(), room.getId(), startDate, endDate);
        DAOProvider.getOrderDAO().insert(order);
        return order;
    }

    public boolean isRoomFree(Room room, Date startDate, Date endDate){
        Map<String,String> params = new HashMap<>();
        params.put(Room.Fields.START_DATE.toString(), DateUtil.dateToStr(startDate));
        params.put(Room.Fields.END_DATE.toString(), DateUtil.dateToStr(endDate));
        params.put(Room.Fields.ID.toString(), room.getId()+"");
        return DAOProvider.getRoomDAO().selectFirst(params).isPresent();
    }

    public Collection<Order> findOrdersByUser(User user){
        Map<String, String> params = new HashMap<>();
        params.put(Order.Fields.USER_ID.toString(),user.getId()+"");
        return DAOProvider.getOrderDAO().select(params);
    }

    public void deleteOrder(Order order){
        DAOProvider.getOrderDAO().delete(order);
    }

    public IdProvider getIdProvider() {
        return idProvider;
    }

    public DAOAbstractFactory getDAOProvider() {
        return DAOProvider;
    }

    public void createData(){
        DAOInterface<User> userDAO = DAOProvider.getUserDAO();
        User user = new User(idProvider.getNewId(),"admin","admin");
        userDAO.insert(user);
        userDAO.insert(new User(idProvider.getNewId(),"guest",""));

        DAOInterface<Hotel> hotelDAO = DAOProvider.getHotelDAO();
        Hotel[] hotels = {new Hotel(idProvider.getNewId(),"Hilton","Kiev"),
                new Hotel(idProvider.getNewId(),"Hilton","Lvov"),
                new Hotel(idProvider.getNewId(),"Radisson","Kharkov"),
                new Hotel(idProvider.getNewId(),"Metropol","Odessa")
        };
        hotelDAO.insert(hotels[0]);
        hotelDAO.insert(hotels[1]);
        hotelDAO.insert(hotels[2]);
        hotelDAO.insert(hotels[3]);

        DAOInterface<Room> roomDAO = DAOProvider.getRoomDAO();
        roomDAO.insert(new Room(idProvider.getNewId(),750,1,hotels[0].getId()));
        Room r=new Room(idProvider.getNewId(),1000,2,hotels[0].getId());
        roomDAO.insert(r);
        roomDAO.insert(new Room(idProvider.getNewId(),1500,3,hotels[0].getId()));

        roomDAO.insert(new Room(idProvider.getNewId(),650,1,hotels[1].getId()));
        roomDAO.insert(new Room(idProvider.getNewId(),900,2,hotels[1].getId()));
        roomDAO.insert(new Room(idProvider.getNewId(),1400,3,hotels[1].getId()));

        roomDAO.insert(new Room(idProvider.getNewId(),600,1,hotels[2].getId()));
        roomDAO.insert(new Room(idProvider.getNewId(),850,2,hotels[2].getId()));
        roomDAO.insert(new Room(idProvider.getNewId(),1300,3,hotels[2].getId()));

        roomDAO.insert(new Room(idProvider.getNewId(),700,1,hotels[3].getId()));
        roomDAO.insert(new Room(idProvider.getNewId(),950,2,hotels[3].getId()));
        roomDAO.insert(new Room(idProvider.getNewId(),1450,3,hotels[3].getId()));


        roomDAO.insert(new Room(idProvider.getNewId(),800,1,hotels[0].getId()));
        roomDAO.insert(new Room(idProvider.getNewId(),1100,2,hotels[0].getId()));
        roomDAO.insert(new Room(idProvider.getNewId(),1600,3,hotels[0].getId()));

        roomDAO.insert(new Room(idProvider.getNewId(),700,1,hotels[1].getId()));
        roomDAO.insert(new Room(idProvider.getNewId(),1000,2,hotels[1].getId()));
        roomDAO.insert(new Room(idProvider.getNewId(),1500,3,hotels[1].getId()));

        roomDAO.insert(new Room(idProvider.getNewId(),650,1,hotels[2].getId()));
        roomDAO.insert(new Room(idProvider.getNewId(),900,2,hotels[2].getId()));
        roomDAO.insert(new Room(idProvider.getNewId(),1400,3,hotels[2].getId()));

        roomDAO.insert(new Room(idProvider.getNewId(),750,1,hotels[3].getId()));
        roomDAO.insert(new Room(idProvider.getNewId(),1000,2,hotels[3].getId()));
        roomDAO.insert(new Room(idProvider.getNewId(),1550,3,hotels[3].getId()));

        DAOInterface<Order> orderDAO = DAOProvider.getOrderDAO();
        orderDAO.insert(new Order(idProvider.getNewId(),user.getId(),r.getId(), DateUtil.stringToDate("10.02.2017"),
                DateUtil.stringToDate("20.02.2017")));
    }

}
