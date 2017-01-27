package exceptions;

/**
 * Created by g.zubenko on 24.01.2017.
 */
public class UnAuthorizedSessionException extends RuntimeException {
    public UnAuthorizedSessionException() {
        super("You should log in first!");
    }
}
