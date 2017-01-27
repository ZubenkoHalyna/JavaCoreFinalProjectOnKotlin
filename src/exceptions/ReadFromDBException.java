package exceptions;

import java.io.File;

/**
 * Created by g.zubenko on 27.01.2017.
 */
public class ReadFromDBException extends RuntimeException {
    public ReadFromDBException(File f) {
        super(getMsg(f));
    }

    public ReadFromDBException(File f, Throwable cause) {
        super(getMsg(f), cause);
    }

    static String getMsg(File f){
        return "Cannot read DB file '"+f.getAbsolutePath()+"'";
    }
}
