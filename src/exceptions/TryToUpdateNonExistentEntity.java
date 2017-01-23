package exceptions;

import entities.BaseEntity;

/**
 * Created by g.zubenko on 18.01.2017.
 */
public class TryToUpdateNonExistentEntity extends RuntimeException {
    public TryToUpdateNonExistentEntity(BaseEntity item) {
        super(getMsg(item));
    }

    public TryToUpdateNonExistentEntity(BaseEntity item, Throwable cause) {
        super(getMsg(item), cause);
    }

    static String getMsg(BaseEntity item){
        return "Try to update non existent entity "+item.getClass().getSimpleName()
                +". Entity id = "+item.getId();
    }
}
