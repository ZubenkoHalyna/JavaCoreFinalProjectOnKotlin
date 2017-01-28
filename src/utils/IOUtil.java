package utils;

import entities.FieldType;
import exceptions.InputWasSkippedException;
import exceptions.StringToDateConvertingException;
import exceptions.UnsupportedFieldTypeException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.function.Consumer;

/**
 * Created by g.zubenko on 26.01.2017.
 */
public final class IOUtil {
    static final private String IOExceptionMsg = "Input failed.";
    static final private BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));

    static public void pressEnterToContinue() {
        System.out.print("Press enter to continue...");
        try {
            consoleReader.readLine();
        } catch (IOException e) {
            System.out.println(IOExceptionMsg);
        }
    }

    static public String readString(String stringDescription, boolean mayBeEmpty){
        return readString(stringDescription, mayBeEmpty, !mayBeEmpty);
    }

    static public String readString(String stringDescription, boolean mayBeEmpty, boolean informUser){
        System.out.print("Input "+stringDescription+
                ((mayBeEmpty && informUser)?" or press enter to skip this filter":"")+
                ": ");
        String value = "";
        try {
            value = consoleReader.readLine();
        }
        catch (IOException e){
            System.out.println(IOExceptionMsg);
            System.exit(1);
        }
        boolean shouldBemayBeEmpty = !mayBeEmpty;
        if (shouldBemayBeEmpty && value.isEmpty()) {
            System.out.println(stringDescription.substring(0,1).toUpperCase()+
                    stringDescription.substring(1,stringDescription.length())+
                    " cannot be empty. Try input again.");
            return readString(stringDescription, mayBeEmpty, informUser);
        }
        else{
            return value;
        }
    }

    static public long readLong(String description, boolean mayBeEmpty, boolean informUser) {
        String stringValue = readString(description, mayBeEmpty, informUser);
        if (stringValue.isEmpty()) {
            throw new InputWasSkippedException(description);
        }
        try {
            return Long.parseLong(stringValue);
        } catch (NumberFormatException e) {
            System.out.println(description + " should be an integer value. Try input again.");
            return readLong(description, mayBeEmpty, informUser);
        }
    }

    static public int readInt(String description, boolean mayBeEmpty, boolean informUser) {
        String stringValue = readString(description, mayBeEmpty, informUser);
        if (stringValue.isEmpty()) {
            throw new InputWasSkippedException(description);
        }
        try {
            return Integer.parseInt(stringValue);
        } catch (NumberFormatException e) {
            System.out.println(description + " should be an integer value. Try input again.");
            return readInt(description, mayBeEmpty, informUser);
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

    static public Date readDate(String description, boolean mayBeEmpty){
        Date currentDate = new Date();
        return readDate(description, currentDate, mayBeEmpty, mayBeEmpty);
    }

    static public Date readDate(String description, boolean mayBeEmpty, Date dateAfter){
        return readDate(description, dateAfter, mayBeEmpty, mayBeEmpty);
    }

    static public Date readDate(String description, boolean mayBeEmpty, boolean informUser){
        Date currentDate = new Date();
        return readDate(description, currentDate, mayBeEmpty, informUser);
    }

    static public Date readDate(String description, Date dateAfter, boolean mayBeEmpty, boolean informUser) {
        String stringValue = readString(description, mayBeEmpty, informUser);
        if (stringValue.isEmpty()) {
            throw new InputWasSkippedException(description);
        }
        Date value;
        try {
            value = DateUtil.stringToDate(stringValue);
        } catch (StringToDateConvertingException e) {
            System.out.println(description + " should be a date in format dd.mm.yyyy. Try input again.");
            return readDate(description, dateAfter, mayBeEmpty, informUser);
        }
        if (value.after(dateAfter)) {
            return value;
        } else {
            System.out.println(description + " should be after " +
                    DateUtil.dateToStr(dateAfter) + ". Try input again.");
            return readDate(description, dateAfter, mayBeEmpty, informUser);
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
                    return DateUtil.dateToStr(readDate(description, true, true));
                } catch (InputWasSkippedException e) {
                    return "";
                }
            default:
                throw new UnsupportedFieldTypeException(type);
        }
    }

    static public boolean askToContinue(String question){
        System.out.print(question+" (Y - yes/N - no) ");
        try {
            String s = IOUtil.consoleReader.readLine();
            if (!s.isEmpty() && (s.toLowerCase().equals("y"))){
                return true;
            }
            if (!s.isEmpty() && (s.toLowerCase().equals("n"))){
                return false;
            }
        } catch (IOException e){
            System.out.println(IOExceptionMsg);
        }
        System.out.print("You should input either \"y\" or \"n\". ");
        return askToContinue(question);
    }

    static public void askToContinue(String question, Operator operator) {
        if (askToContinue(question)) {
            operator.execute();
        }
    }

    static public <T> void askToContinue(String question, Consumer<List<T>> consumer, List<T> param) {
        if (askToContinue(question)) {
            consumer.accept(param);
        }
    }

    static public <T> void printCollection(String itemDescription, Collection<T> c) {
        printCollection(itemDescription, c, true);
    }

    static public <T> void printCollection(String itemDescription, Collection<T> c, boolean waitForUser){
        printCollection(itemDescription + " on your request",
                "There no "+itemDescription + " on your request.",
                waitForUser,
                c);
    }

    // The method prints eny collection if toString method of it's elements was automatically generated or
    // was written in a same form
    // If any exception occur it just print a list of strings generated by toString method
    static public <T> void printCollection(String description, String msgIfEmpty,
                                           boolean waitForUser, Collection<T> c) {
        if (c.isEmpty()) {
            System.out.println(msgIfEmpty);
            pressEnterToContinue();
        } else {
            try {
                Object obj = c.iterator().next();
                String objectToString = obj.toString();
                String[] objectToStrings = objectToString.substring(objectToString.lastIndexOf('{') + 1,
                                                                    objectToString.lastIndexOf('}'))
                                                                    .split(", ");
                final int maxColumnNumber = objectToStrings.length;
                final String[] columnNames = new String[maxColumnNumber + 1];
                int[] maxColumnLength = new int[maxColumnNumber];
                for (int j = 0; j < maxColumnNumber; j++) {
                    String currentString = objectToStrings[j];
                    columnNames[j] = currentString.substring(0, currentString.indexOf('='));
                    maxColumnLength[j] = columnNames[j].length();
                }
                String[][] table = new String[c.size()][maxColumnNumber];

                int rowNumber = 0;
                for (Object cOrder : c) {
                    String str = cOrder.toString();
                    table[rowNumber] = str.substring(str.lastIndexOf('{') + 1, str.lastIndexOf('}'))
                            .split(", ", maxColumnNumber);
                    for (int j = 0; j < maxColumnNumber; j++) {
                        String currentString = table[rowNumber][j];
                        currentString = currentString.substring(currentString.indexOf('=') + 1, currentString.length());
                        if (currentString.charAt(0) == '\'')
                            currentString = currentString.substring(1, currentString.length());
                        if (currentString.charAt(currentString.length() - 1) == '\'')
                            currentString = currentString.substring(0, currentString.length() - 1);
                        table[rowNumber][j] = currentString;
                        if (maxColumnLength[j] < table[rowNumber][j].length())
                            maxColumnLength[j] = table[rowNumber][j].length();
                    }
                    rowNumber++;
                }

                String[] formatString = new String[maxColumnNumber];
                for (int j = 0; j < maxColumnNumber; j++) {
                    formatString[j] = "%" + (maxColumnLength[j] + 3) + "s";
                }
                System.out.println(description + ":");
                System.out.print(" №");
                for (int j = 0; j < maxColumnNumber; j++) {
                    System.out.format(formatString[j], columnNames[j]);
                }
                System.out.println();
                for (int i = 0; i < c.size(); i++) {
                    System.out.format("%3d", i + 1);
                    for (int j = 0; j < maxColumnNumber; j++) {
                        System.out.format(formatString[j], table[i][j]);
                    }
                    System.out.println();
                }

            }catch (Exception e) {
                Iterator<T> iterator=c.iterator();
                for (int i = 0; i < c.size(); i++) {
                    System.out.format("%2d. %s\n",i+1,iterator.next().toString());
                }
            }

            if (waitForUser) pressEnterToContinue();
        }
    }

    static public void informUser(String information) {
        informUser(information, true);
    }

    static public void informUser(String information, boolean waitForUser) {
        if (waitForUser) {
            System.out.println(information);
            pressEnterToContinue();
        }else{
            System.out.println(information);
        }
    }

    static public void informUserAndAskToContinue(String information, String question, Operator operator) {
        System.out.print(information+" ");
        askToContinue(question, operator);
    }
}
