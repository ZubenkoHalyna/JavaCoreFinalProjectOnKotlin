package utils;

import exceptions.StringToDateConvertingException;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by g.zubenko on 23.01.2017.
 */
public final class DateUtil {
    public static Date stringToDate(String str){
        try{
            SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
            return format.parse(str);
        }
        catch (Exception e){
            throw new StringToDateConvertingException(str,e);
        }
    }

    public static String dateToStr(Date date){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        return dateFormat.format(date);
    }
}
