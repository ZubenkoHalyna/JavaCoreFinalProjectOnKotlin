package dataAccess.saveInFileDAO;

import dataAccess.DAOInterface;
import entities.BaseEntity;
import exceptions.EntityNotFoundById;
import exceptions.ReadFromDBException;
import exceptions.WriteToDBException;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by g.zubenko on 26.01.2017.
 */
abstract class DAO<T extends BaseEntity> implements DAOInterface<T> {
    abstract Stream<T> filter(Map<String, String> params);
    abstract List<T> getCache();
    abstract Class getEntityClass();
    abstract void setTransientValuesForEntitiesInCache();
    abstract void setCache(List<T> cache);

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
        ArrayList<T> items;
        File file = new File(getEntityClass().getSimpleName() + ".dat");
        if (file.exists()) {
            try (ObjectInputStream in = new ObjectInputStream(new BufferedInputStream(
                    new FileInputStream(file.getAbsolutePath())))) {
                items = (ArrayList) in.readObject();
            } catch (Exception ex) {
                throw new ReadFromDBException(file,ex);
            }
        }
        else {
            throw new ReadFromDBException(file);
        }
        setCache(items);
        setTransientValuesForEntitiesInCache();
        return true;
    }

    boolean writeCacheToFile(){
        File file = new File(getEntityClass().getSimpleName() + ".dat");
        try {
            if (!file.exists()) {
                file.createNewFile();
                System.out.println("File '" + file.getAbsolutePath() + "' was created");
            }
            try (ObjectOutputStream out = new ObjectOutputStream(new BufferedOutputStream(
                    new FileOutputStream(file.getAbsolutePath())))) {
                out.writeObject(getCache());
                return true;
            } catch (IOException ex) {
                throw new WriteToDBException(file);
            }
        } catch (IOException e) {
            throw  new WriteToDBException(file);
        }
    }

    protected HotelDAO getHotelDAO(){
        return new HotelDAO();
    }
    protected RoomDAO getRoomDAO(){
        return new RoomDAO();
    }
    protected UserDAO getUserDAO(){
        return new UserDAO();
    }
    protected OrderDAO getOrderDAO(){
        return new OrderDAO();
    }
}
