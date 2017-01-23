package exceptions;

/**
 * Created by g.zubenko on 23.01.2017.
 */
public class StringToDateConvertingException extends RuntimeException{
    public StringToDateConvertingException(String stringToConvert) {
        super(getMsg(stringToConvert));
    }

    public StringToDateConvertingException(String stringToConvert, Throwable cause) {
        super(getMsg(stringToConvert), cause);
    }

    static String getMsg(String stringToConvert){
        return "Cannot convert string '"+stringToConvert+"' to date";
    }
}
