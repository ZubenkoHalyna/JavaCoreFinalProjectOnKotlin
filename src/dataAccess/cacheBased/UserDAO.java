package dataAccess.cacheBased;

import dataAccess.FiltersUtil;
import entities.User;

import java.util.*;
import java.util.stream.Stream;

/**
 * Created by ssizov on 17.01.2017.
 */
class UserDAO extends DAO<User> {
    private List<User> users = new ArrayList<>();

    public UserDAO(CacheBasedDB DB) {
        super(DB);
    }

    @Override
    public Stream<User> filter(Map<String, String> params) {
        return FiltersUtil.filterUsers(params,getDB().getUserDAO());
    }

    @Override
    protected Class getEntityClass() {
        return User.class;
    }

    @Override
    List<User> getCache() {
        return users;
    }

    void setCache(List<User> users) {
        this.users = users;
    }
}
