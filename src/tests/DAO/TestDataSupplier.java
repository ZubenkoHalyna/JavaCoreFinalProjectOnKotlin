package tests.DAO;

import entities.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by g.zubenko on 31.01.2017.
 */
public class TestDataSupplier {
    private static TestDataSupplier instance;
    public static TestDataSupplier getInstance() {
        if (instance == null){
            instance = new TestDataSupplier();
        }
        return instance;
    }

    private Hotel hotel =new Hotel("hotelName","city");
    private User user = new User("userLogin","password");
    private Room room = new Room(0,0,hotel);
    private Order order = new Order(user,room,new GregorianCalendar(2017,0,1).getTime(),new GregorianCalendar(2017,0,10).getTime() );
    private List<Hotel> hotelList = new ArrayList<>();
    private List<User> userList = new ArrayList<>();
    private  List<Room> roomList = new ArrayList<>();
    private List<Order> orderList = new ArrayList<>();

    private TestDataSupplier()
    {
        hotelList.add(new Hotel("", ""));
        hotelList.add(new Hotel("", ""));
        userList.add(new User("", ""));
        userList.add(new User("", ""));
        roomList.add(new Room(0, 0, hotelList.get(0)));
        roomList.add(new Room(0, 0, hotelList.get(1)));
        Date date = new GregorianCalendar(2017, 0, 1).getTime();
        orderList.add(new Order(userList.get(0), roomList.get(0), date, date));
        orderList.add(new Order(userList.get(1), roomList.get(1), date, date));
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public List<Hotel> getHotelList() {
        return hotelList;
    }

    public void setHotelList(List<Hotel> hotelList) {
        this.hotelList = hotelList;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public List<Room> getRoomList() {
        return roomList;
    }

    public void setRoomList(List<Room> roomList) {
        this.roomList = roomList;
    }

    public List<Order> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<Order> orderList) {
        this.orderList = orderList;
    }
}
