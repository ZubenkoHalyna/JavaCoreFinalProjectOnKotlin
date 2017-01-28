package dataAccess.saveInFileDAO;

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
 * Created by g.zubenko on 26.01.2017.
 */
abstract class DAO<T extends BaseEntity> implements DAOInterface<T> {
    private FileBasedDB DB;
    abstract Stream<T> filter(Map<String, String> params);
    abstract List<T> getCache();
    abstract Class getEntityClass();
    abstract void setTransientValuesForEntitiesInCache();
    abstract void setCache(List<T> cache);

    protected DAO(){}

    public DAO(FileBasedDB DB) {
        this.DB = DB;
    }

    @Override
    public List<T> selectAll(){
        return getCacheOrReadDataFromDB();
    }

    @Override
    public Optional<T> selectFirst(Map<String,String> params){
        return filter(params).findFirst();
    }

    @Override
    public List<T> select(Map<String,String> params){
        return filter(params).collect(Collectors.toList());
    }

    @Override
    public boolean insert(T item) {
        getCacheOrReadDataFromDB().add(item);
        return writeCacheToFile();
    }

    @Override
    public boolean insertAll(Collection<T> items) {
        getCacheOrReadDataFromDB().addAll(items);
        return writeCacheToFile();
    }

    public boolean update(T item) {
        getCacheOrReadDataFromDB().remove(item);
        return writeCacheToFile();
    }

    public boolean delete(T item){
        getCacheOrReadDataFromDB().remove(item);
        return writeCacheToFile();
    }

    public T getById(long id) {
        Optional<T> item = getCacheOrReadDataFromDB().stream().filter(i->i.getId()==id).findFirst();
        if (item.isPresent()){
            return item.get();
        }
        throw new EntityNotFoundById(getEntityClass(),id);
    }

    public List<T> getCacheOrReadDataFromDB(){
        List<T> cache = getCache();
        if (cache.isEmpty()){
            readCacheFromFile();
            cache = getCache();
        }
        return cache;
    }

    boolean readCacheFromFile() {
        DB.getFileAccessObj().readCacheFromFile(this);
        return true;
    }

    boolean writeCacheToFile(){
        DB.getFileAccessObj().writeCacheToFile(this);
        return true;
    }

    public FileBasedDB getDB() {
        return DB;
    }
}
