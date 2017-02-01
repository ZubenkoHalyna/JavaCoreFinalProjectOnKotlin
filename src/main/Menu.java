package main;

import entities.Order;
import entities.Room;
import entities.User;
import exceptions.InputWasSkippedException;
import exceptions.UnAuthorizedSessionException;
import utils.DateUtil;
import utils.IOUtil;

import java.util.*;

/**
 * Created by g.zubenko on 24.01.2017.
 */
public class Menu {
    private boolean notClosed;
    private Session session;
    private Controller controller;

    public Menu(Session session, Controller controller) {
        this.session = session;
        this.controller = controller;
    }

    public void open() {
        notClosed = true;
        mainMenu();
    }

    private void close() {
        notClosed = false;
    }

    public void nextStep() {
        mainMenu();
    }

    public boolean isNotClosed(){
        return notClosed;
    }

    private void mainMenu() {
        System.out.println("\nHello, "+session.toString()+". You can:");
        System.out.println("1. Find hotel by name\n2. Find hotel by city\n3. Find room, book room\n4. Login\n5. Register\n6. View orders, cancel reservation\n7. Quite");
        int numberOfAction = IOUtil.readInt("number of action you want to do",1,7);

        switch (numberOfAction){
            case 1: findHotelByName(); break;
            case 2: findHotelByCity(); break;
            case 3: findRoom(); break;
            case 4: login(); break;
            case 5: register(); break;
            case 6: viewOrders(); break;
            case 7: close(); break;
        }
        //TODO press enter to continue only there
    }

    private void register() {
        String login = IOUtil.readString("your login", false);
        String password = IOUtil.readString("your password", true, false);

        controller.registerUser(login, password);

        //TODO if was not.
        IOUtil.informUserAndAskToContinue("User was registered.", "Try to login? ", this::login);
    }

    private void login() {
        String login = IOUtil.readString("your login",false);
        String password = IOUtil.readString("your password",true,false);

        Optional<Session> buf = controller.startProtectedSession(login,password);
        if (buf.isPresent()){
            session = buf.get();
            IOUtil.informUser("Log in is successful.");
        }
        else{
            IOUtil.informUserAndAskToContinue("User unregistered or password is incorrect.",
                    "Try to log in again?",this::login);
        }
    }

    private void findRoom() {
        Map<String, String> params = new HashMap<>();
        for (Room.FieldsForSearch field : Room.FieldsForSearch.values()) {
            if (field.directForUser) {
                String value = IOUtil.readFormattedString(field.type, field.description);
                params.put(field.toString(), value);
                checkIndirectParams(params, field, value);
            }
        }

        ArrayList<Room> rooms = new ArrayList<>(controller.findRoom(params));
        IOUtil.printCollection("Rooms", rooms, false);
        if (!rooms.isEmpty()) {
            IOUtil.askToContinue("Would you like to book some room?", this::bookRoom, rooms);
        }
    }

    private void checkIndirectParams(Map<String, String> params, Room.FieldsForSearch field, String value) {
        if (field== Room.FieldsForSearch.START_DATE && !value.isEmpty()){
            try {String newValue = DateUtil.dateToStr(
                    IOUtil.readDate(Room.FieldsForSearch.END_DATE.description, true, DateUtil.stringToDate(value)));
            params.put(Room.FieldsForSearch.END_DATE.toString(), newValue);}
            catch (InputWasSkippedException e){}
        }
        if (field== Room.FieldsForSearch.PRICE && !value.isEmpty()){
            String newValue = IOUtil.readFormattedString(Room.FieldsForSearch.PRICE_VARIATION.type,
                    Room.FieldsForSearch.PRICE_VARIATION.description);
            params.put(Room.FieldsForSearch.PRICE_VARIATION.toString(), newValue);
        }
    }

    private User getRegisteredUser() throws UnAuthorizedSessionException {
        try {
            return session.getUser();
        } catch (UnAuthorizedSessionException e) {
            IOUtil.informUserAndAskToContinue(e.getMessage(), "Try to login?", this::login);
            return session.getUser();
        }
    }

    private void bookRoom(List<Room> rooms){
        User user;
        try {
            user = getRegisteredUser();
            if (session.isNew()){
                System.out.println();
                IOUtil.printCollection("Rooms",rooms,false);
            }
        }
        catch (UnAuthorizedSessionException e){
            return;
        }

        int numberOfRoom = IOUtil.readInt("number of the room you want to book",1,rooms.size())-1;
        Room room = rooms.get(numberOfRoom);
        //TODO don't ask dates again
        Date startDate = IOUtil.readDate("start reservation date",false);
        Date endDate = IOUtil.readDate("end reservation date",false,startDate);
        if (controller.isRoomFree(room,startDate,endDate)){
            Order order = controller.registerOrder(user, room, startDate, endDate);
            IOUtil.informUser("Registered order: "+order.getView());
        }
        else{
            IOUtil.informUser("Room has already reserved");
        }
    }

    private void viewOrders() {
        User user;
        try {
            user = getRegisteredUser();
            if (session.isNew()){
                System.out.println();
            }
        } catch (UnAuthorizedSessionException e) {
            return;
        }
        List<Order> orders = controller.findOrdersByUser(user);

        IOUtil.printCollection("Your orders", "You haven't made any orders yet", false, orders);
        if (!orders.isEmpty()) {
            IOUtil.askToContinue("Would you like to cancel reservation?", this::cancelReservation, orders);
        }
    }

    private void cancelReservation(List<Order> orders) {
        int numberOfRoom = IOUtil.readInt("number of the reservation", 1, orders.size()) - 1;
        controller.deleteOrder(orders.get(numberOfRoom));
        IOUtil.informUser("Reservation was canceled successfully!");
    }

    private void findHotelByCity() {
        String city = IOUtil.readString("city name",false);
        IOUtil.printCollection("hotels",controller.findHotelByCity(city));
    }

    private void findHotelByName() {
        String hotelName = IOUtil.readString("hotel name",true,true);
        IOUtil.printCollection("hotels",controller.findHotelByName(hotelName));
    }
}
