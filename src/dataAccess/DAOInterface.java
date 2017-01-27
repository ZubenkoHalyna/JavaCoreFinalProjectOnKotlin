package dataAccess;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Created by g.zubenko on 23.01.2017.
 */
public interface DAOInterface<T> {
    List<T>         select(Map<String,String> params);
    Optional<T>     selectFirst(Map<String,String> params);
    List<T>         selectAll();
    T               getById(long id);
    boolean         insert(T item);
    boolean         insertAll(Collection<T> items);
    boolean         update(T item);
    boolean         delete(T item);
}
