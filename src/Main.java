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

/**
 * Created by g.zubenko on 23.01.2017.
 */
public class Main {

    public static void main(String[] args) {
        Controller controller = new Controller(UuidProvider.getInstance(), MockDAOFactory.getInstance());
        loadData(controller.getDAOProvider(), controller.getIdProvider());

        Menu mainMenu = new Menu(new Session(), controller);
        mainMenu.open();

        while (mainMenu.isNotClosed()) {
            mainMenu.nextStep();
        }
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
