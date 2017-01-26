package utils;

import entities.BaseEntity;
import entities.FieldType;
import exceptions.InputWasSkippedException;
import exceptions.StringToDateConvertingException;
import exceptions.UnsupportedFieldTypeException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.function.Consumer;

/**
 * Created by g.zubenko on 26.01.2017.
 */
public final class IOUtil {
    static final private String IOExceptionMsg = "Sorry. Input failed. System error occur.";
    static final private BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));

    static public String readString(String stringDescription){
        return readString(stringDescription, false, false);
    }

    static public String readString(String stringDescription, boolean notEmpty){
        return readString(stringDescription, notEmpty, !notEmpty);
    }

    static public String readString(String stringDescription, boolean canBeEmpty, boolean informUser){
        System.out.print("Input "+stringDescription+
                ((canBeEmpty && informUser)?" or press enter to skip this filter":"")+
                ": ");
        String value = "";
        try {
            value = consoleReader.readLine();
        }
        catch (IOException e){
            System.out.println(IOExceptionMsg);
            System.exit(1);
        }
        boolean shouldBeNotEmpty = !canBeEmpty;
        if (shouldBeNotEmpty && value.isEmpty()) {
            System.out.println(stringDescription.substring(0,1).toUpperCase()+
                    stringDescription.substring(1,stringDescription.length())+
                    " cannot be empty. Try input again.");
            return readString(stringDescription, canBeEmpty, informUser);
        }
        else{
            return value;
        }
    }

    static public void pressAnyKeyToContinue()
    {
        System.out.println("Press any key to continue...");
        try
        {
            System.in.read();
        }
        catch(Exception e)
        {}
    }

    static public long readLong(String description, boolean notEmpty, boolean informUser) {
        String stringValue = readString(description, notEmpty, informUser);
        if (stringValue.isEmpty()) {
            throw new InputWasSkippedException(description);
        }
        try {
            return Long.parseLong(stringValue);
        } catch (NumberFormatException e) {
            System.out.println(description + " should be an integer value. Try input again.");
            return readLong(description, notEmpty, informUser);
        }
    }

    static public int readInt(String description, boolean notEmpty, boolean informUser) {
        String stringValue = readString(description, notEmpty, informUser);
        if (stringValue.isEmpty()) {
            throw new InputWasSkippedException(description);
        }
        try {
            return Integer.parseInt(stringValue);
        } catch (NumberFormatException e) {
            System.out.println(description + " should be an integer value. Try input again.");
            return readInt(description, notEmpty, informUser);
        }
    }

    static public int readInt(String description, int minValue, int maxValue) {
        int value = readInt(description,false,false);

        if (value > maxValue) {
            System.out.println(description + " should be less then " + (maxValue + 1) + ". Try input again.");
            return readInt(description, minValue, maxValue);
        }
        if (value < minValue) {
            System.out.println(description + " should be greater then " + (minValue - 1) + ". Try input again.");
            return readInt(description, minValue, maxValue);
        }
        return value;
    }

    static public Date readFutureDate(String description, boolean notEmpty){
        return readFutureDate( description, notEmpty, !notEmpty);
    }

    static public Date readFutureDate(String description, boolean notEmpty, boolean informUser) {
        String stringValue = readString(description, notEmpty, informUser);
        if (stringValue.isEmpty()) {
            throw new InputWasSkippedException(description);
        }
        Date value;
        try {
            value = DateUtil.stringToDate(stringValue);
        } catch (StringToDateConvertingException e) {
            System.out.println(description + " should be a date in format dd.mm.yyyy. Try input again.");
            return readFutureDate(description,notEmpty, informUser);
        }
        Date currentDate =new Date();
        if (value.before(currentDate)) {
            System.out.println(description + " should be after " +
                    DateUtil.dateToStr(currentDate) + ". Try input again.");
            return readFutureDate(description,notEmpty, informUser);
        } else {
            return value;
        }
    }

    static public String readFormattedString(FieldType type, String description) {
        switch (type) {
            case LONG:
                try {
                    return Long.toString(readLong(description, true, true));
                } catch (InputWasSkippedException e) {
                    return "";
                }
            case INT:
                try {
                    return Integer.toString(readInt(description, true, true));
                } catch (InputWasSkippedException e) {
                    return "";
                }
            case STRING:
                return readString(description, true, true);
            case DATE:
                try {
                    return DateUtil.dateToStr(readFutureDate(description, true, true));
                } catch (InputWasSkippedException e) {
                    return "";
                }
            default:
                throw new UnsupportedFieldTypeException(type);
        }
    }

    static public <T extends BaseEntity> void printCollection(String itemDescription, boolean waitForUser, Collection<T> c){
        printCollection(itemDescription + " on your request",
                "There no "+itemDescription + " on your request:",
                waitForUser,
                c);
    }

    static public <T extends BaseEntity> void printCollection(String itemDescription, Collection<T> c){
        printCollection(itemDescription, true, c);
    }

    static public <T extends BaseEntity> void printCollection(String description, String msgIfEmpty,
                                                        boolean waitForUser, Collection<T> c) {
        if (c.isEmpty()) {
            System.out.println(msgIfEmpty);
            pressAnyKeyToContinue();
        } else {
            System.out.println(description + ":");
            int elementNumber = 0;
            Iterator<T> iterator = c.iterator();
            while (iterator.hasNext()) {
                System.out.format("%2d %s \n", ++elementNumber, iterator.next());
            }
        }
        if (waitForUser) pressAnyKeyToContinue();
    }

    static public void askToContinue(Operator operator){
        System.out.print(" (Y - yes/N - no) ");
        try {
            String s = IOUtil.consoleReader.readLine();
            if (!s.isEmpty() && (s.charAt(0)=='y' || s.charAt(0)=='Y')){
                operator.execute();
            }
            else{
                System.out.println();
            }
        } catch (IOException e){
            System.out.println(IOExceptionMsg);
            askToContinue(operator);
        }
    }

    static public <T> void askToContinue(Consumer<ArrayList<T>> consumer, ArrayList<T> param) {
        try {
            String s = consoleReader.readLine();
            if (!s.isEmpty() && (s.charAt(0)=='y' || s.charAt(0)=='Y')){
                consumer.accept(param);
            }
            else{
                System.out.println();
            }
        } catch (IOException e){
            System.out.println(IOExceptionMsg);
            askToContinue(consumer, param);
        }
    }
}
