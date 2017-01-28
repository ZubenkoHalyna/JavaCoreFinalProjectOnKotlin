package dataAccess.saveInFileDAO;

import dataAccess.FiltersUtil;
import entities.User;

import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.*;
import java.util.stream.Stream;

/**
 * Created by g.zubenko on 26.01.2017.
 */
@XmlRootElement
class UserDAO extends DAO<User>{
    @XmlElementWrapper
    private List<User> cache = new ArrayList<>();

    private UserDAO(){}

    UserDAO(FileBasedDB DB) {
        super(DB);
    }

    @Override
    protected Class getEntityClass() {
        return User.class;
    }

    @Override
    void setTransientValuesForEntitiesInCache() {
        // there no transient values in class User
    }

    @Override
    List<User> getCache() {
        return cache;
    }

    @Override
    void setCache(List<User> cache) {
        this.cache = cache;
    }

    @Override
    public Stream<User> filter(Map<String, String> params) {
        return FiltersUtil.filterUsers(params, getDB().getUserDAO());
    }
}
