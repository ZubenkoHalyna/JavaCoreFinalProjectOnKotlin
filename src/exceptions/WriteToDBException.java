package exceptions;

import java.io.File;

/**
 * Created by g.zubenko on 27.01.2017.
 */
public class WriteToDBException extends RuntimeException {
    public WriteToDBException(File f) {
        super(getMsg(f));
    }

    public WriteToDBException(File f, Throwable cause) {
        super(getMsg(f), cause);
    }

    static String getMsg(File f){
        return "Cannot write DB file '"+f.getAbsolutePath()+"'";
    }
}
