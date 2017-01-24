import entities.Room;
import entities.User;
import exceptions.StringToDateConvertingException;
import exceptions.UnAuthorizedSessionException;
import utils.DateUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

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
    private Room[] lastRoomsFind;

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
        }
    }

    private void findRoom() {
        Map<String, String> params = new HashMap<>();
        controller.getRoomSearchFields().forEach(field->{
            String value = readString(field.toLowerCase().replace('_',' ')+" or press enter to skip this filter");
            params.put(field,value);
        });
        Collection<Room> c = controller.findRoom(params);
        lastRoomsFind = c.toArray(new Room[c.size()]);
        System.out.println(c);

        System.out.print("Would you like to book some room? ");
        askToContinue(this::bookRoom);
    }

    private User getRegisteredUser() throws UnAuthorizedSessionException {
        User user;
        try{
            user = session.getUser();
        }
        catch (UnAuthorizedSessionException e){
            System.out.println(e.getMessage());
            nextItem = Optional.of(this::bookRoom);
            System.out.print("Try to login?");
            askToContinue(this::login);
            throw e;
        }
        return user;
    }

    private void bookRoom(){
        User user;
        try {
            user = getRegisteredUser();
        }
        catch (UnAuthorizedSessionException e){
            return;
        }

        int numberOfRoom = readInt("number of room you want to book",lastRoomsFind.length);
        Room room = lastRoomsFind[numberOfRoom];
        //TODO don't ask dates again
        Date startDate = readFutureDate("start reservation date");
        Date endDate = readFutureDate("end reservation date");
        if (controller.isRoomFree(room,startDate,endDate)){
            System.out.println("Sorry, room has already reserved");
        }
        else{
            controller.registerOrder(user, lastRoomsFind[numberOfRoom],startDate,endDate);
        }
    }

    private void viewOrders() {

    }

    private void findHotelByCity() {
        String city = readString("city name",true);
        System.out.println(controller.findHotelByCity(city));
    }

    private void findHotelByName() {
        String hotelName = readString("hotel name",true);
        System.out.println(controller.findHotelByName(hotelName));
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
        return readInt(stringDescription,-1);
    }

    private int readInt(String stringDescription, int maxValue) {
        System.out.print("Input " + stringDescription + ": ");
        String descriptionWithUpperFirstLetter = stringDescription.substring(0, 1).toUpperCase() +
                stringDescription.substring(1, stringDescription.length());
        String stringValue = readString(stringDescription, true);
        int value;
        try {
            value = Integer.parseInt(stringValue);if (maxValue!=-1 && value > maxValue) {
                System.out.println(descriptionWithUpperFirstLetter + " should be less then " + maxValue + ". Try input again.");
                return readInt(stringDescription, maxValue);
            } else {
                return value;
            }
        } catch (IllegalFormatCodePointException e) {
            System.out.println(descriptionWithUpperFirstLetter + " should be an integer value. Try input again.");
            return readInt(stringDescription, maxValue);
        }
    }

    private Date readFutureDate(String stringDescription) {
        System.out.print("Input " + stringDescription + ": ");
        String descriptionWithUpperFirstLetter = stringDescription.substring(0, 1).toUpperCase() +
                stringDescription.substring(1, stringDescription.length());
        String stringValue = readString(stringDescription, true);
        Date value;
        try {
            value = DateUtil.getInstance().stringToDate(stringValue);
            Date currentDate =new Date();
            if (value.before(currentDate)) {
                System.out.println(descriptionWithUpperFirstLetter + " should be after " +
                        utils.DateUtil.getInstance().dateToStr(currentDate) + ". Try input again.");
                return readFutureDate(stringDescription);
            } else {
                return value;
            }
        } catch (StringToDateConvertingException e) {
            System.out.println(descriptionWithUpperFirstLetter + " should be a date in format dd.mm.yyyy. Try input again.");
            return readFutureDate(stringDescription);
        }

    }

    public boolean isNotClosed(){
        return notClosed;
    }
}
