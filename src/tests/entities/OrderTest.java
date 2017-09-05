package tests.entities;

import entities.Hotel;
import entities.Order;
import entities.Room;
import entities.User;

import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by g.zubenko on 28.01.2017.
 */
public class OrderTest {
    private Hotel hotelStub = new Hotel("name","city");
    private Room roomStub = new Room(1000,2,hotelStub);
    private User userStub = new User("","");
    private Date firstDateStub = new GregorianCalendar(2017,0,1).getTime();
    private Date secondDateStub = new GregorianCalendar(2017,0,3).getTime();

    @Test
    public void compareTo() throws Exception {
        Order order1 = new Order(userStub,roomStub,firstDateStub,firstDateStub);
        Order order2 = new Order(userStub,roomStub,secondDateStub,secondDateStub);
        assertEquals("Method compareTo in class Order doesn't work correct",
                1, order1.compareTo(order2));
    }

    @Test
    public void getView() throws Exception {
        Order o = new Order(userStub,roomStub,firstDateStub,secondDateStub);
        assertEquals("Method getView in class Order doesn't work correct",
                "name, city, 1000 USD per day, 2 persons, from 01.01.2017 to 03.01.2017",
                o.getView());
    }

    @Test
    public void toStringTest() throws Exception {
        Order o = new Order(userStub,roomStub,firstDateStub,secondDateStub);
        assertEquals("Method toString in class Order doesn't work correct",
                "Order{city=city, hotel name=name, price per day=1000, persons=2, start date=01.01.2017, end date=03.01.2017}",
                o.toString());
    }

    @Test
    public void getUserId() throws Exception {
        Order o = new Order(userStub,roomStub, firstDateStub, firstDateStub);
        assertEquals("Method getUserId in class Order doesn't work correct",
                userStub.getId(), o.getUserId());
    }

    @Test
    public void getUser() throws Exception {
        Order o = new Order(userStub,roomStub, firstDateStub, firstDateStub);
        assertEquals("Method getUser in class Order doesn't work correct",
                userStub, o.getUser());
    }

    @Test
    public void setUser() throws Exception {
        Order o = new Order(new User("",""),roomStub, firstDateStub, firstDateStub);
        o.setUser(userStub);
        assertEquals("Method setUser in class Order doesn't work correct", userStub, o.getUser());
        assertEquals("Method setUser in class Order doesn't work correct: it doesn't set UserId", userStub.getId(), o.getUserId());
    }

    @Test
    public void getRoomId() throws Exception {
        Order o = new Order(userStub,roomStub,firstDateStub,firstDateStub);
        assertEquals("Method getRoomId in class Order doesn't work correct", roomStub.getId(), o.getRoomId());

    }

    @Test
    public void getRoom() throws Exception {
        Order o = new Order(userStub,roomStub,firstDateStub,firstDateStub);
        assertEquals("Method getRoom in class Order doesn't work correct",
                roomStub, o.getRoom());
    }

    @Test
    public void setRoom() throws Exception {
        Order o = new Order(userStub,new Room(0,0,hotelStub),firstDateStub,firstDateStub);
        o.setRoom(roomStub);
        assertEquals("Method setRoom in class Order doesn't work correct", roomStub, o.getRoom());
        assertEquals("Method setRoom in class Order doesn't work correct: it doesn't set RoomId", roomStub.getId(), o.getRoomId());
    }

    @Test
    public void getStartReservationDate() throws Exception {
        Order o = new Order(userStub,roomStub,firstDateStub,secondDateStub);
        assertEquals("Method getStartReservationDate in class Order doesn't work correct", firstDateStub,
                o.getStartReservationDate());
    }

    @Test
    public void setStartReservationDate() throws Exception {
        Order o = new Order(userStub,roomStub, secondDateStub, secondDateStub);
        o.setStartReservationDate(firstDateStub);
        assertEquals("Method setStartReservationDate in class Order doesn't work correct", firstDateStub,
                o.getStartReservationDate());
    }

    @Test
    public void getEndReservationDate() throws Exception {
        Order o = new Order(userStub,roomStub,secondDateStub,firstDateStub);
        assertEquals("Method getEndReservationDate in class Order doesn't work correct", firstDateStub,
                o.getEndReservationDate());
    }

    @Test
    public void setEndReservationDate() throws Exception {
        Order o = new Order(userStub,roomStub, secondDateStub, secondDateStub);
        o.setEndReservationDate(firstDateStub);
        assertEquals("Method setEndReservationDate in class Order doesn't work correct", firstDateStub,
                o.getEndReservationDate());
    }

}