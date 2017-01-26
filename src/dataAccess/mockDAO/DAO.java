package dataAccess.mockDAO;

import dataAccess.DAOInterface;
import entities.BaseEntity;
import exceptions.EntityNotFoundById;
import exceptions.TryToDeleteNonExistentEntity;
import exceptions.TryToInsertExistentEntity;
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
    abstract public Stream<T> filter(Map<String,String> params);
    abstract protected Class getEntityClass();

    public Set<T> select(Map<String,String> params){
        return filter(params).collect(Collectors.toSet());
    }

    public Optional<T> selectFirst(Map<String,String> params){
        return filter(params).findFirst();
    }

    public boolean insert(T item){
        Set<T> storage = this.selectAll();
        if (storage.contains(item)) {
            throw new TryToInsertExistentEntity(item);
        }else{
            this.selectAll().add(item);
            return true;
        }
    }
    public boolean insertAll(Set<T> items){
        items.stream().forEach(this::insert);
        //TODO correct return
        return true;
    }

    public boolean update(T item){
        Set<T> storage = this.selectAll();
        if (storage.contains(item)){
            storage.add(item);
            return true;
        }
        else {
            throw new TryToUpdateNonExistentEntity(item);
        }
    }

    public boolean delete(T item){
        Set<T> storage = this.selectAll();
        if (storage.contains(item)){
            storage.remove(item);
            return true;
        }
        else {
            throw new TryToDeleteNonExistentEntity(item);
        }
    }

    public T getById(long id) {
        Set<T> storage = this.selectAll();
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
