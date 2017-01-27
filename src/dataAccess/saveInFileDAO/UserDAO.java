package dataAccess.saveInFileDAO;

import dataAccess.FiltersUtil;
import entities.User;

import java.util.*;
import java.util.stream.Stream;

/**
 * Created by g.zubenko on 26.01.2017.
 */
class UserDAO extends DAO<User>{
    private List<User> cache = new ArrayList<>();

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
        return FiltersUtil.filterUsers(params, getUserDAO());
    }
}
