package dataAccess;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * Created by g.zubenko on 23.01.2017.
 */
public interface DAOInterface<T> {
    Set<T> select(Map<String,String> params);
    Optional<T> selectFirst(Map<String,String> params);
    boolean insert(T item);
    boolean update(T item);
    boolean delete(T item);
}
