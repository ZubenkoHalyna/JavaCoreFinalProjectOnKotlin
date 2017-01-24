import entities.BaseEntity;
import entities.Order;
import entities.Room;
import entities.User;
import exceptions.StringToDateConvertingException;
import exceptions.UnAuthorizedSessionException;
import utils.DateUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.function.Consumer;

/**
 * Created by g.zubenko on 24.01.2017.
 */
public class Menu {
    static final BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
    static final String IOExceptionMsg = "Input failed. Please, repeat input.";

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
        System.out.println("1. Find hotel by name\n2. Find hotel by city\n3. Find room\n4. Login\n5. Register\n6. View orders\n7. Quite");
        String numberOfAction = readString("number of action you want to do");

        switch (numberOfAction){
            case "1": findHotelByName(); break;
            case "2": findHotelByCity(); break;
            case "3": findRoom(); break;
            case "4": login(); break;
            case "5": register(); break;
            case "6": viewOrders(); break;
            case "7": close(); break;
        }
    }

    private void close() {
        notClosed = false;
    }

    private void register() {
        String login = readString("your login",true);
        String password = readString("your password");

        try{
            controller.registerUser(login, password);
        }catch (IllegalArgumentException e){
            System.out.print(e.getMessage()+" Try again? ");
            askToContinue(this::login);
        }

        //TODO if was not.
        System.out.print("User was registered. Try to login? ");
        askToContinue(this::login);
    }

    private void login() {
        String login = readString("your login",true);
        String password = readString("your password");

        Optional<Session> buf = controller.startProtectedSession(login,password);
        if (buf.isPresent()){
            session = buf.get();
            System.out.println("Log in successful.");
        }
        else{
            System.out.println("User unregistered or password is incorrect");
            System.out.print("Try to log in again?");
            askToContinue(this::login);
        }
    }

    private void findRoom() {
        Map<String, String> params = new HashMap<>();
        controller.getRoomSearchFields().forEach(field->{
            String value = readString(field.toLowerCase().replace('_',' ')+" or press enter to skip this filter");
            params.put(field,value);
        });
        ArrayList<Room> c = new ArrayList<>(controller.findRoom(params));
        printCollection(c);

        System.out.print("Would you like to book some room?");
        askToContinue(this::bookRoom, c);
    }

    private User getRegisteredUser() throws UnAuthorizedSessionException {
        try {
            return session.getUser();
        } catch (UnAuthorizedSessionException e) {
            System.out.print(e.getMessage() + " Try to login?");
            askToContinue(this::login);
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

        int numberOfRoom = readInt("number of the room you want to book",1,rooms.size())-1;
        Room room = rooms.get(numberOfRoom);
        //TODO don't ask dates again
        //TODO startSate before endDate
        Date startDate = readFutureDate("start reservation date");
        Date endDate = readFutureDate("end reservation date");
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

        if (orders.size() > 0) {
            printCollection(orders);
            System.out.println("Would you like to cancel reservation?");
            askToContinue(this::cancelReservation,orders);
        } else {
            System.out.println("You haven't made any orders yet");
        }
    }

    public void cancelReservation(ArrayList<Order> orders) {
        User user;
        try {
            user = getRegisteredUser();
        } catch (UnAuthorizedSessionException e) {
            return;
        }

        int numberOfRoom = readInt("number of the reservation", 1, orders.size()) - 1;
        controller.deleteOrder(orders.get(numberOfRoom));
        System.out.println("Order was deleted successfully!");

    }

    private void findHotelByCity() {
        String city = readString("city name",true);
        printCollection(controller.findHotelByCity(city));
    }

    private void findHotelByName() {
        String hotelName = readString("hotel name",true);
        printCollection(controller.findHotelByName(hotelName));
    }


    private void askToContinue(Operator operator){
        System.out.print(" (Y - yes/N - no) ");
        try {
            String s = consoleReader.readLine();
            if (!s.isEmpty() && (s.charAt(0)=='y' || s.charAt(0)=='Y')){
                operator.execute();
            }
        } catch (IOException e){
            System.out.println(IOExceptionMsg);
            askToContinue(operator);
        }
    }

    private <T> void askToContinue(Consumer<ArrayList<T>> consumer, ArrayList<T> param){
        System.out.print(" (Y - yes/N - no) ");
        try {
            String s = consoleReader.readLine();
            if (!s.isEmpty() && (s.charAt(0)=='y' || s.charAt(0)=='Y')){
                consumer.accept(param);
            }
        } catch (IOException e){
            System.out.println(IOExceptionMsg);
            askToContinue(consumer, param);
        }
    }

    private String readString(String stringDescription){
        return readString(stringDescription, false);
    }

    private String readString(String stringDescription, boolean notEmpty){
        System.out.print("Input "+stringDescription+": ");
        String value;
        try {
            value = consoleReader.readLine();
        }
        catch (IOException e){
            System.out.println(IOExceptionMsg);
            return readString(stringDescription, notEmpty);
        }
        if (notEmpty && value.isEmpty()) {
            System.out.println(stringDescription.substring(0,1).toUpperCase()+
                    stringDescription.substring(1,stringDescription.length())+
                    " cannot be empty. Try input again.");
            return readString(stringDescription, notEmpty);
        }
        else{
            return value;
        }
    }

    private int readInt(String stringDescription) {
        String stringValue = readString(stringDescription, true);
        try {
            return Integer.parseInt(stringValue);
        } catch (IllegalFormatCodePointException e) {
            System.out.println(stringDescription + " should be an integer value. Try input again.");
            return readInt(stringDescription);
        }
    }

    private int readInt(String stringDescription, int minValue, int maxValue) {
        int value = readInt(stringDescription);
        if (value > maxValue) {
            System.out.println(stringDescription + " should be less then " + (maxValue + 1) + ". Try input again.");
            return readInt(stringDescription, minValue, maxValue);
        }
        if (value < minValue) {
            System.out.println(stringDescription + " should be greater then " + (minValue - 1) + ". Try input again.");
            return readInt(stringDescription, minValue, maxValue);
        }
        return value;
    }

    private Date readFutureDate(String stringDescription) {
        String stringValue = readString(stringDescription, true);
        Date value;
        try {
            value = DateUtil.stringToDate(stringValue);
        } catch (StringToDateConvertingException e) {
            System.out.println(stringDescription + " should be a date in format dd.mm.yyyy. Try input again.");
            return readFutureDate(stringDescription);
        }
        Date currentDate =new Date();
        if (value.before(currentDate)) {
            System.out.println(stringDescription + " should be after " +
                    DateUtil.dateToStr(currentDate) + ". Try input again.");
            return readFutureDate(stringDescription);
        } else {
            return value;
        }
    }

    private <T extends BaseEntity> void printCollection(Collection<T> c){
        int elementNumber = 0;
        for (T item : c) {
            elementNumber++;
            System.out.format("%2d %s \n",elementNumber,item.getView());
        }
    }

    public boolean isNotClosed(){
        return notClosed;
    }
}
