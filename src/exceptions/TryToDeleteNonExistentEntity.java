package exceptions;

import entities.BaseEntity;

/**
 * Created by g.zubenko on 18.01.2017.
 */
public class TryToDeleteNonExistentEntity extends RuntimeException{
    public TryToDeleteNonExistentEntity(BaseEntity item) {
        super(getMsg(item));
    }

    public TryToDeleteNonExistentEntity(BaseEntity item, Throwable cause) {
        super(getMsg(item), cause);
    }

    static String getMsg(BaseEntity item){
        return "Try to delete non existent entity "+item.getClass().getSimpleName()
                +". Entity id = "+item.getId();
    }
}
