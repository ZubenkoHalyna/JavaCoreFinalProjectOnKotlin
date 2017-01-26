package dataAccess;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * Created by g.zubenko on 23.01.2017.
 */
public interface DAOInterface<T> {
    T           getById(long id);
    Optional<T> selectFirst(Map<String,String> params);
    Set<T>      select(Map<String,String> params);
    Set<T>      selectAll();
    boolean     insert(T item);
    boolean     insertAll(Set<T> items);
    boolean     update(T item);
    boolean     delete(T item);
}
