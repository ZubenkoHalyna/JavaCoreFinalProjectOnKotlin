package utils;

import exceptions.StringToDateConvertingException;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by g.zubenko on 23.01.2017.
 */
public class DateUtil {
    //Singleton pattern
    static private DateUtil instance;
    static public DateUtil getInstance(){
        if (instance==null){
            instance = new DateUtil();
        }
        return instance;
    }

    private DateUtil(){
    }

    public Date stringToDate(String str){
        int day,month,year;
        try{
            String[] buf = str.split("\\.");
            day = Integer.parseInt(buf[0]);
            month = Integer.parseInt(buf[1]);
            year = Integer.parseInt(buf[2]);
        }
        catch (Exception e){
            throw new StringToDateConvertingException(str,e);
        }

        Calendar c = new GregorianCalendar(day,month,year);
        return c.getTime();
    }
}
