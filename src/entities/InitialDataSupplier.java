package entities;

import utils.DateUtil;

import java.util.*;

/**
 * Created by g.zubenko on 27.01.2017.
 */
public class InitialDataSupplier {
    private static InitialDataSupplier instance;
    public static InitialDataSupplier getInstance() {
        if (instance == null){
            instance = new InitialDataSupplier();
        }
        return instance;
    }

    private List<User> users;
    private List<Hotel> hotels;
    private List<Room> rooms;
    private List<Order> orders;

    private InitialDataSupplier(){
        users = new ArrayList<>();
        users.add(new User("admin",""));

        hotels = new ArrayList<>();
        hotels.add(new Hotel("Hilton",  "Kiev"));
        hotels.add(new Hotel("Radisson","Kiev"));
        hotels.add(new Hotel("Hilton",  "Odessa"));
        hotels.add(new Hotel("Metropol","Odessa"));

        rooms = new ArrayList<>();
        rooms.add(new Room(750,1,hotels.get(0)));
        rooms.add(new Room(1000,2,hotels.get(0)));
        rooms.add(new Room(1500,3,hotels.get(0)));
        rooms.add(new Room(650,1,hotels.get(1)));
        rooms.add(new Room(900,2,hotels.get(1)));
        rooms.add(new Room(1400,3,hotels.get(1)));
        rooms.add(new Room(600,1,hotels.get(2)));
        rooms.add(new Room(850,2,hotels.get(2)));
        rooms.add(new Room(1300,3,hotels.get(2)));
        rooms.add(new Room(700,1,hotels.get(3)));
        rooms.add(new Room(950,2,hotels.get(3)));
        rooms.add(new Room(1450,3,hotels.get(3)));

        rooms.add(new Room(750,1,hotels.get(0)));
        rooms.add(new Room(1000,2,hotels.get(0)));
        rooms.add(new Room(1500,3,hotels.get(0)));
        rooms.add(new Room(650,1,hotels.get(1)));
        rooms.add(new Room(900,2,hotels.get(1)));
        rooms.add(new Room(1400,3,hotels.get(1)));
        rooms.add(new Room(600,1,hotels.get(2)));
        rooms.add(new Room(850,2,hotels.get(2)));
        rooms.add(new Room(1300,3,hotels.get(2)));
        rooms.add(new Room(700,1,hotels.get(3)));
        rooms.add(new Room(950,2,hotels.get(3)));
        rooms.add(new Room(1450,3,hotels.get(3)));

        orders = new ArrayList<>();
        orders.add(new Order(users.get(0), rooms.get(1), DateUtil.stringToDate("10.02.2017"),
                DateUtil.stringToDate("20.02.2017")));
    }

    public List<User> getUsers() {
        return users;
    }

    public List<Hotel> getHotels() {
        return hotels;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public List<Order> getOrders() {
        return orders;
    }
}
