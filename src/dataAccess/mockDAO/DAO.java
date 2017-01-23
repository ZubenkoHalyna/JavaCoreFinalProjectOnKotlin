package dataAccess.mockDAO;

import dataAccess.DAOInterface;
import dataAccess.exceptions.EntityNotFoundById;
import dataAccess.exceptions.TryToDeleteNonExistentEntity;
import dataAccess.exceptions.TryToUpdateNonExistentEntity;
import entities.BaseEntity;
import entities.Hotel;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by g.zubenko on 16.01.2017.
 */
public abstract class DAO<T extends BaseEntity> implements DAOInterface<T> {
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

    public T getById(long id){
        Set<T> storage = getStorage();
        Set<T> objectsSet = storage.stream().filter(item->item.getId()==id).collect(Collectors.toSet());

        if (objectsSet.size()==1){
            return objectsSet.iterator().next();
        }
        else {
            throw new EntityNotFoundById(getEntityClass(),id);
        }
    }

    protected DAO<Hotel> getHotelDAO(){
        return new HotelDAO();
    }
}
