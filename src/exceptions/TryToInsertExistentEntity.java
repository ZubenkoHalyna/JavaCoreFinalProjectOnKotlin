package exceptions;

import entities.BaseEntity;

/**
 * Created by g.zubenko on 25.01.2017.
 */
public class TryToInsertExistentEntity extends RuntimeException {
    public TryToInsertExistentEntity(BaseEntity item) {
        super(getMsg(item));
    }

    public TryToInsertExistentEntity(BaseEntity item, Throwable cause) {
        super(getMsg(item), cause);
    }

    static String getMsg(BaseEntity item){
        return "Try to insert existent entity "+item.getClass().getSimpleName()
                +". Entity id = "+item.getId();
    }
}
