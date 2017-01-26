import entities.*;
import exceptions.UnAuthorizedSessionException;
import utils.IOUtil;
import utils.Operator;

import java.util.*;

/**
 * Created by g.zubenko on 24.01.2017.
 */
public class Menu {
    private boolean notClosed;
    private Session session;
    private Controller controller;
    private Optional<Operator> nextItem = Optional.empty();

    public Menu(Session session, Controller controller) {
        this.session = session;
        this.controller = controller;
    }

    public void open() {
        notClosed = true;
        mainMenu();
    }

    public void nextStep(){
        if (nextItem.isPresent()){
            nextItem.get().execute();
            nextItem = Optional.empty();
        }
        else {
            mainMenu();
        }
    }

    private void mainMenu() {
        System.out.println("Hello, "+session.toString()+". You can:");
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
    }

    private void close() {
        notClosed = false;
    }

    private void register() {
        String login = IOUtil.readString("your login",false);
        String password = IOUtil.readString("your password",true,false);

        try{
            controller.registerUser(login, password);
        }catch (IllegalArgumentException e){
            System.out.print(e.getMessage()+" Try again? ");
            IOUtil.askToContinue(this::login);
        }

        //TODO if was not.
        System.out.print("User was registered. Try to login? ");
        IOUtil.askToContinue(this::login);
    }

    private void login() {
        String login = IOUtil.readString("your login",false);
        String password = IOUtil.readString("your password",true,false);

        Optional<Session> buf = controller.startProtectedSession(login,password);
        if (buf.isPresent()){
            session = buf.get();
            System.out.println("Log in successful.");
            IOUtil.pressAnyKeyToContinue();
        }
        else{
            System.out.println("User unregistered or password is incorrect");
            System.out.print("Try to log in again?");
            IOUtil.askToContinue(this::login);
        }
    }

    private void findRoom() {
        Map<String, String> params = new HashMap<>();
        for (Room.Fields field : Room.Fields.values()) {
            if (field.forUser) {
                String value = IOUtil.readFormattedString(field.type, field.description);
                params.put(field.toString(), value);
            }
        }
        ArrayList<Room> rooms = new ArrayList<>(controller.findRoom(params));
        IOUtil.printCollection("rooms", false, rooms);
        if (!rooms.isEmpty()) {
            System.out.print("Would you like to book some room?");
            IOUtil.askToContinue(this::bookRoom, rooms);
        }
    }

    private User getRegisteredUser() throws UnAuthorizedSessionException {
        try {
            return session.getUser();
        } catch (UnAuthorizedSessionException e) {
            System.out.print(e.getMessage() + " Try to login?");
            IOUtil.askToContinue(this::login);
            return session.getUser();
        }
    }

    private void bookRoom(ArrayList<Room> rooms){
        User user;
        try {
            user = getRegisteredUser();
        }
        catch (UnAuthorizedSessionException e){
            return;
        }

        int numberOfRoom = IOUtil.readInt("number of the room you want to book",1,rooms.size())-1;
        Room room = rooms.get(numberOfRoom);
        //TODO don't ask dates again
        //TODO startSate before endDate
        Date startDate = IOUtil.readFutureDate("start reservation date",false);
        Date endDate = IOUtil.readFutureDate("end reservation date",false);
        if (controller.isRoomFree(room,startDate,endDate)){
            Order order = controller.registerOrder(user, room, startDate, endDate);
            System.out.println("Registered order: "+order.toString());
        }
        else{
            System.out.println("Sorry, room has already reserved");
        }
    }

    private void viewOrders() {
        User user;
        try {
            user = getRegisteredUser();
        } catch (UnAuthorizedSessionException e) {
            return;
        }
        ArrayList<Order> orders = new ArrayList<>(controller.findOrdersByUser(user));
        Collections.sort(orders);

        IOUtil.printCollection("Your orders", "You haven't made any orders yet", false, orders);
        if (!orders.isEmpty()) {
            System.out.println("Would you like to cancel reservation?");
            IOUtil.askToContinue(this::cancelReservation, orders);
        }
    }

    public void cancelReservation(ArrayList<Order> orders) {
        /*TODO check of User
        User user;
        try {
            user = getRegisteredUser();
        } catch (UnAuthorizedSessionException e) {
            return;
        }*/

        int numberOfRoom = IOUtil.readInt("number of the reservation", 1, orders.size()) - 1;
        controller.deleteOrder(orders.get(numberOfRoom));
        System.out.println("Order was deleted successfully!");

    }

    private void findHotelByCity() {
        String city = IOUtil.readString("city name",false);
        IOUtil.printCollection("hotels",controller.findHotelByCity(city));
    }

    private void findHotelByName() {
        String hotelName = IOUtil.readString("hotel name",true,true);
        IOUtil.printCollection("hotels",controller.findHotelByName(hotelName));
    }

    public boolean isNotClosed(){
        return notClosed;
    }
}
