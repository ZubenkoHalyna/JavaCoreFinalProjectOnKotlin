package exceptions;

/**
 * Created by g.zubenko on 23.01.2017.
 */
public class EntityNotFoundById extends RuntimeException{
    public EntityNotFoundById(Class c, long id) {
        super(getMsg(c,id));
    }

    public EntityNotFoundById(Class c, long id, Throwable cause) {
        super(getMsg(c,id), cause);
    }

    static String getMsg(Class c, long id){
        return "Entity "+c.getSimpleName()+" not found by id. Id = "+id;
    }
}