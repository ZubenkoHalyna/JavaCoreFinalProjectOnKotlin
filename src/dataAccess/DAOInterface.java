package dataAccess;

import java.util.Map;
import java.util.Set;

/**
 * Created by g.zubenko on 23.01.2017.
 */
public interface DAOInterface<T> {
    Set<T> select(Map<String,String> params);
    void insert(T item);
    void update(T item);
    void delete(T item);
}
