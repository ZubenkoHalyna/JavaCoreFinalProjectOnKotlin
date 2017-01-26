import dataAccess.DAOAbstractFactory;
import dataAccess.DAOInterface;
import dataAccess.mockDAO.MockDAOFactory;
import dataAccess.saveInFileDAO.SaveInFileDAOFactory;
import entities.Hotel;
import entities.Order;
import entities.Room;
import entities.User;
import identification.IdProvider;
import identification.UuidProvider;
import utils.DateUtil;
import utils.IOUtil;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by g.zubenko on 23.01.2017.
 */
public class Main {
    static DAOAbstractFactory DAOProvider;
    static IdProvider idProvider;

    public static void main(String[] args) {
       // DAOProvider = MockDAOFactory.getInstance();
        DAOProvider = SaveInFileDAOFactory.getInstance();
        idProvider = UuidProvider.getInstance();
        if (DAOProvider.getClass() == MockDAOFactory.class) {
            loadData(DAOProvider, idProvider);
        }

        Controller controller = new Controller(idProvider, DAOProvider);
        Menu mainMenu = new Menu(new Session(), controller);
        mainMenu.open();

        while (mainMenu.isNotClosed()) {
            mainMenu.nextStep();
        }

        if (IOUtil.askToContinue("\nWould you like to rewrite DB files with initial data?")){
            rewriteDBFiles(SaveInFileDAOFactory.getInstance(), controller.getIdProvider());
        }
    }

    public static void rewriteDBFiles(DAOAbstractFactory DAOProvider, IdProvider idProvider) {
        DAOInterface<User> userDAO = DAOProvider.getUserDAO();
        Set<User> users = new HashSet<>();
        User admin = new User(idProvider.getNewId(),"admin","admin");
        users.add(admin);
        users.add(new User(idProvider.getNewId(),"guest",""));
        userDAO.insertAll(users);
        userDAO.select(new HashMap<>());

        Hotel[] hotels = {new Hotel(idProvider.getNewId(),"Hilton","Kiev"),
                new Hotel(idProvider.getNewId(),"Hilton","Lvov"),
                new Hotel(idProvider.getNewId(),"Radisson","Kharkov"),
                new Hotel(idProvider.getNewId(),"Metropol","Odessa")
        };

        DAOInterface<Hotel> hotelDAO = DAOProvider.getHotelDAO();
        Set<Hotel> setOfHotels = new HashSet<>();
        setOfHotels.add(hotels[0]);
        setOfHotels.add(hotels[1]);
        setOfHotels.add(hotels[2]);
        setOfHotels.add(hotels[3]);
        hotelDAO.insertAll(setOfHotels);

        Set<Room> rooms =new HashSet<>();
        rooms.add(new Room(idProvider.getNewId(),750,1,hotels[0]));
        Room roomForOrder = new Room(idProvider.getNewId(),1000,2,hotels[0]);
        rooms.add(roomForOrder);
        rooms.add(new Room(idProvider.getNewId(),1500,3,hotels[0]));

        rooms.add(new Room(idProvider.getNewId(),650,1,hotels[1]));
        rooms.add(new Room(idProvider.getNewId(),900,2,hotels[1]));
        rooms.add(new Room(idProvider.getNewId(),1400,3,hotels[1]));

        rooms.add(new Room(idProvider.getNewId(),600,1,hotels[2]));
        rooms.add(new Room(idProvider.getNewId(),850,2,hotels[2]));
        rooms.add(new Room(idProvider.getNewId(),1300,3,hotels[2]));

        rooms.add(new Room(idProvider.getNewId(),700,1,hotels[3]));
        rooms.add(new Room(idProvider.getNewId(),950,2,hotels[3]));
        rooms.add(new Room(idProvider.getNewId(),1450,3,hotels[3]));


        rooms.add(new Room(idProvider.getNewId(),800,1,hotels[0]));
        rooms.add(new Room(idProvider.getNewId(),1100,2,hotels[0]));
        rooms.add(new Room(idProvider.getNewId(),1600,3,hotels[0]));

        rooms.add(new Room(idProvider.getNewId(),700,1,hotels[1]));
        rooms.add(new Room(idProvider.getNewId(),1000,2,hotels[1]));
        rooms.add(new Room(idProvider.getNewId(),1500,3,hotels[1]));

        rooms.add(new Room(idProvider.getNewId(),650,1,hotels[2]));
        rooms.add(new Room(idProvider.getNewId(),900,2,hotels[2]));
        rooms.add(new Room(idProvider.getNewId(),1400,3,hotels[2]));

        rooms.add(new Room(idProvider.getNewId(),750,1,hotels[3]));
        rooms.add(new Room(idProvider.getNewId(),1000,2,hotels[3]));
        rooms.add(new Room(idProvider.getNewId(),1550,3,hotels[3]));

        DAOProvider.getRoomDAO().insertAll(rooms);

        Set<Order> orders = new HashSet<>();
        orders.add(new Order(idProvider.getNewId(),admin,roomForOrder, DateUtil.stringToDate("10.02.2017"),
                DateUtil.stringToDate("20.02.2017")));
        DAOProvider.getOrderDAO().insertAll(orders);
    }

    static public void loadData(DAOAbstractFactory DAOProvider, IdProvider idProvider){
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
        roomDAO.insert(new Room(idProvider.getNewId(),750,1,hotels[0]));
        Room r=new Room(idProvider.getNewId(),1000,2,hotels[0]);
        roomDAO.insert(r);
        roomDAO.insert(new Room(idProvider.getNewId(),1500,3,hotels[0]));

        roomDAO.insert(new Room(idProvider.getNewId(),650,1,hotels[1]));
        roomDAO.insert(new Room(idProvider.getNewId(),900,2,hotels[1]));
        roomDAO.insert(new Room(idProvider.getNewId(),1400,3,hotels[1]));

        roomDAO.insert(new Room(idProvider.getNewId(),600,1,hotels[2]));
        roomDAO.insert(new Room(idProvider.getNewId(),850,2,hotels[2]));
        roomDAO.insert(new Room(idProvider.getNewId(),1300,3,hotels[2]));

        roomDAO.insert(new Room(idProvider.getNewId(),700,1,hotels[3]));
        roomDAO.insert(new Room(idProvider.getNewId(),950,2,hotels[3]));
        roomDAO.insert(new Room(idProvider.getNewId(),1450,3,hotels[3]));


        roomDAO.insert(new Room(idProvider.getNewId(),800,1,hotels[0]));
        roomDAO.insert(new Room(idProvider.getNewId(),1100,2,hotels[0]));
        roomDAO.insert(new Room(idProvider.getNewId(),1600,3,hotels[0]));

        roomDAO.insert(new Room(idProvider.getNewId(),700,1,hotels[1]));
        roomDAO.insert(new Room(idProvider.getNewId(),1000,2,hotels[1]));
        roomDAO.insert(new Room(idProvider.getNewId(),1500,3,hotels[1]));

        roomDAO.insert(new Room(idProvider.getNewId(),650,1,hotels[2]));
        roomDAO.insert(new Room(idProvider.getNewId(),900,2,hotels[2]));
        roomDAO.insert(new Room(idProvider.getNewId(),1400,3,hotels[2]));

        roomDAO.insert(new Room(idProvider.getNewId(),750,1,hotels[3]));
        roomDAO.insert(new Room(idProvider.getNewId(),1000,2,hotels[3]));
        roomDAO.insert(new Room(idProvider.getNewId(),1550,3,hotels[3]));

        DAOInterface<Order> orderDAO = DAOProvider.getOrderDAO();
        orderDAO.insert(new Order(idProvider.getNewId(),user,r, DateUtil.stringToDate("10.02.2017"),
                DateUtil.stringToDate("20.02.2017")));
    }
}
