package dataAccess.saveInFileDAO;

import dataAccess.DAOInterface;
import entities.BaseEntity;

import java.io.*;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by g.zubenko on 26.01.2017.
 */
abstract class DAO<T extends BaseEntity> implements DAOInterface<T> {
    abstract public Stream<T> filter(Map<String,String> params);
    abstract Set<T> getCache();
    abstract protected Class getEntityClass();

    void setCacheValues(Set<T> items) {}

    public Set<T> readFromFile() {
        Set<T> cache = getCache();
        if (cache.isEmpty()) {
            Set<T> items = new HashSet<T>();
            File file = new File(getEntityClass().getSimpleName() + ".dat");
            if (file.exists()) {
                try (ObjectInputStream in = new ObjectInputStream(new BufferedInputStream(
                        new FileInputStream(file.getAbsolutePath())))) {
                    items = (Set) in.readObject();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            setCacheValues(items);
            return items;
        }
        else{
            return cache;
        }
    }

    public Set<T> selectAll(){
        return readFromFile();
    }

    void setCache(Set<T> cache){
        Set<T> buf = getCache();
        buf = cache;
    }

    public Optional<T> selectFirst(Map<String,String> params){
        return filter(params).findFirst();
    }

    public Set<T> select(Map<String,String> params){
        return filter(params).collect(Collectors.toSet());
    }

    public boolean insert(T item) {
        Set<T> set = readFromFile();
        set.add(item);
        insertAll(set);
        return true;
    }

    public boolean insertAll(Set<T> items) {
        setCache(items);
        File file;
        try {
            file = new File(getEntityClass().getSimpleName() + ".dat");
            if (!file.exists()) {
                file.createNewFile();
                System.out.println("File '" + file.getAbsolutePath() + "' was created");
            }
            try (ObjectOutputStream out = new ObjectOutputStream(new BufferedOutputStream(
                    new FileOutputStream(file.getAbsolutePath())))) {
                out.writeObject(items);

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } catch (IOException e) {
        }
        return true;
    }

    public boolean update(T item) {
        //TODO
        return true;
    }

    public boolean delete(T item){
        Set<T> set = readFromFile();
        set.remove(item);
        insertAll(set);
        return true;
    }

    public T getById(long id) {
        Optional<T> item = readFromFile().stream().filter(i->i.getId()==id).findFirst();
        if (item.isPresent()){
            return item.get();
        }
        //TODO
        return null;
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
