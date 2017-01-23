package dataAccess.mockDAO;

import dataAccess.DAOInterface;
import exceptions.EntityNotFoundById;
import exceptions.TryToDeleteNonExistentEntity;
import exceptions.TryToUpdateNonExistentEntity;
import entities.*;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * Created by g.zubenko on 16.01.2017.
 */
abstract class DAO<T extends BaseEntity> implements DAOInterface<T> {
    abstract protected Set<T> getStorage();
    abstract public Set<T> select(Map<String,String> params);
    abstract protected Class getEntityClass();

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
