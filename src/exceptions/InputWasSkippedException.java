package exceptions;

/**
 * Created by g.zubenko on 26.01.2017.
 */
public class InputWasSkippedException extends RuntimeException{
    public InputWasSkippedException(String description) {
        super(getMsg(description));
    }

    public InputWasSkippedException(String description, Throwable cause) {
        super(getMsg(description), cause);
    }

    static String getMsg(String description) {
        return "User skip " + description + " input";
    }
}
