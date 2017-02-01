package dataAccess.cacheBased;

import dataAccess.DAOInterface;
import entities.BaseEntity;
import exceptions.EntityNotFoundById;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by g.zubenko on 16.01.2017.
 */
abstract class DAO<T extends BaseEntity> implements DAOInterface<T> {
    private CacheBasedDB DB;
    abstract public Stream<T> filter(Map<String,String> params);
    abstract protected Class getEntityClass();
    abstract List<T> getCache();

    public List<T> selectAll(){
        return getCache();
    }

    public DAO(CacheBasedDB DB) {
        this.DB = DB;
    }

    public List<T> select(Map<String,String> params){
        return filter(params).collect(Collectors.toList());
    }

    public Optional<T> selectFirst(Map<String,String> params){
        return filter(params).findFirst();
    }

    @Override
    public boolean insert(T item) {
        List<T> storage = selectAll();
        return storage.add(item);
    }

    @Override
    public boolean insertAll(Collection<T> items){
        List<T> storage = selectAll();
        return storage.addAll(items);
    }

    @Override
    public boolean update(T item){
        //Nothing should be done. Objects in cache based DB is always up to date.
        if (getCache().contains(item)) return true;
        else return false;
    }

    public boolean delete(T item) {
        List<T> storage = selectAll();
        return storage.remove(item);
    }

    public T getById(long id) {
        Collection<T> storage = this.selectAll();
        Optional<T> obj = storage.stream().filter(item -> item.getId()==id).findFirst();

        if (obj.isPresent()) {
            return obj.get();
        } else {
            throw new EntityNotFoundById(getEntityClass(), id);
        }
    }

    public CacheBasedDB getDB() {
        return DB;
    }
}
