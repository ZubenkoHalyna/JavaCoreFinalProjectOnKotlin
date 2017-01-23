import dataAccess.DAOAbstractFactory;
import dataAccess.DAOInterface;
import dataAccess.mockDAO.MockDAOFactory;
import entities.Hotel;
import entities.Order;
import entities.Room;
import entities.User;
import identification.IdProvider;
import identification.UuidProvider;
import utils.DateUtil;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by g.zubenko on 16.01.2017.
 */
public class Controller {
    private IdProvider idProvider = UuidProvider.getInstance();
    private DAOAbstractFactory DAOProvider = new MockDAOFactory();

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

    public void createData(){
        DAOInterface<User> userDAO = DAOProvider.getUserDAO();
        User user = new User(idProvider.getNewId(),"admin","@dmin");
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
        DateUtil util = DateUtil.getInstance();
        orderDAO.insert(new Order(idProvider.getNewId(),user.getId(),r.getId(), util.stringToDate("10.02.2017"),
                util.stringToDate("20.02.2017")));
    }

    public IdProvider getIdProvider() {
        return idProvider;
    }

    public void setIdProvider(IdProvider idProvider) {
        this.idProvider = idProvider;
    }

    public DAOAbstractFactory getDAOProvider() {
        return DAOProvider;
    }

    public void setDAOProvider(DAOAbstractFactory DAOProvider) {
        this.DAOProvider = DAOProvider;
    }
}
