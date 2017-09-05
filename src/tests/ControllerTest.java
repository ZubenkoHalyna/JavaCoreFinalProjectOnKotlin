package tests;

import entities.Hotel;
import entities.Order;
import entities.Room;
import entities.User;
import main.Controller;

import java.util.Date;
import java.util.HashMap;

import org.junit.Test;

import static org.junit.Assert.assertEquals;


/**
 * Created by g.zubenko on 01.02.2017.
 */
public class ControllerTest {
    MockDB DB = new MockDB();
    Controller controller = new Controller(DB);
    Hotel hotelDummy = new Hotel("","");
    Date dateDummy = new Date();

    private Order orderDummy = new Order(new User("",""),new Room(0,0,hotelDummy),
            dateDummy, dateDummy);

    @Test
    public void findHotelByName() throws Exception {
        DB.getHotelDAO().nextStep();
        controller.findHotelByName("");
        assertEquals("Method findHotelByName in class Controller doesn't work correct: it should call DB.getHotelDAO().select()",
                1,DB.getHotelDAO().getSelectNumber());
    }

    @Test
    public void findHotelByCity() throws Exception {
        DB.getHotelDAO().nextStep();
        controller.findHotelByCity("");
        assertEquals("Method findHotelByCity in class Controller doesn't work correct: it should call DB.getHotelDAO().select()",
                1,DB.getHotelDAO().getSelectNumber());
    }

    @Test
    public void findRoom() throws Exception {
        DB.getRoomDAO().nextStep();
        controller.findRoom(new HashMap<>());
        assertEquals("Method findRoom in class Controller doesn't work correct: it should call DB.getRoomDAO().select()",
                1,DB.getRoomDAO().getSelectNumber());
    }

    @Test
    public void registerUser() throws Exception {
        DB.getUserDAO().nextStep();
        controller.registerUser("","");
        assertEquals("Method registerUser in class Controller doesn't work correct: it should call DB.getUserDAO().insert()",
                1,DB.getUserDAO().getInsertNumber());
    }

    @Test
    public void registerOrder() throws Exception {
        DB.getOrderDAO().nextStep();
        controller.registerOrder(new User("",""),new Room(0,0, hotelDummy),dateDummy,dateDummy);
        assertEquals("Method registerOrder in class Controller doesn't work correct: it should call DB.getOrderDAO().insert()",
                1,DB.getOrderDAO().getInsertNumber());
    }

    @Test
    public void isRoomFree() throws Exception {
        DB.getRoomDAO().nextStep();
        controller.isRoomFree(new Room(0,0, hotelDummy),dateDummy,dateDummy);
        assertEquals("Method isRoomFree in class Controller doesn't work correct: it should call DB.getRoomDAO().selectFirst()",
                1,DB.getRoomDAO().getSelectFirstNumber());
    }

    @Test
    public void findOrdersByUser() throws Exception {
        DB.getOrderDAO().nextStep();
        controller.findOrdersByUser(new User("",""));
        assertEquals("Method findOrdersByUser in class Controller doesn't work correct: it should call DB.getOrderDAO().select()",
                1,DB.getOrderDAO().getSelectNumber());
    }

    @Test
    public void deleteOrder() throws Exception {
        DB.getOrderDAO().nextStep();
        controller.deleteOrder(orderDummy);
        assertEquals("Method deleteOrder in class Controller doesn't work correct: it should call DB.getOrderDAO().delete()",
                1,DB.getOrderDAO().getDeleteNumber());
    }

    @Test
    public void getDB() throws Exception {
        assertEquals("Method getDB in class Controller doesn't work correct: it should return the same value every time it runs",
                controller.getDB(),controller.getDB());
    }

}