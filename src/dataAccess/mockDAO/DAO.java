package dataAccess.mockDAO;

import dataAccess.DAOInterface;
import entities.BaseEntity;
import exceptions.EntityNotFoundById;
import exceptions.TryToDeleteNonExistentEntity;
import exceptions.TryToUpdateNonExistentEntity;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by g.zubenko on 16.01.2017.
 */
abstract class DAO<T extends BaseEntity> implements DAOInterface<T> {
    abstract protected Set<T> getStorage();
    abstract public Stream<T> filter(Map<String,String> params);
    abstract protected Class getEntityClass();

    public Set<T> select(Map<String,String> params){
        return filter(params).collect(Collectors.toSet());
    }

    public Optional<T> selectFirst(Map<String,String> params){
        return filter(params).findFirst();
    }

    public void insert(T item){
        getStorage().add(item);
    }

    public void update(T item){
        Set<T> storage = getStorage();
        if (storage.contains(item)){
            storage.add(item);
        }
        else {
            throw new TryToUpdateNonExistentEntity(item);
        }
    }

    public void delete(T item){
        Set<T> storage = getStorage();
        if (storage.contains(item)){
            storage.remove(item);
        }
        else {
            throw new TryToDeleteNonExistentEntity(item);
        }
    }

    public T getById(long id) {
        Set<T> storage = getStorage();
        Optional<T> obj = storage.stream().filter(item -> item.getId()==id).findFirst();

        if (obj.isPresent()) {
            return obj.get();
        } else {
            throw new EntityNotFoundById(getEntityClass(), id);
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
