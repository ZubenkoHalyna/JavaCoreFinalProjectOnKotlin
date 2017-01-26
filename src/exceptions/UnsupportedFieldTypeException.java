package exceptions;

import entities.FieldType;

/**
 * Created by g.zubenko on 25.01.2017.
 */
public class UnsupportedFieldTypeException  extends RuntimeException {
    public UnsupportedFieldTypeException(FieldType item) {
        super(getMsg(item));
    }

    public UnsupportedFieldTypeException(FieldType item, Throwable cause) {
        super(getMsg(item), cause);
    }

    static String getMsg(FieldType item){
        return "Field type '"+item.getClass().getSimpleName()+"' is unsupported";
    }
}
