package dataAccess.saveInFileDAO;

import dataAccess.FiltersUtil;
import entities.User;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

/**
 * Created by g.zubenko on 26.01.2017.
 */
class UserDAO extends DAO<User>{
    private Set<User> cache = new HashSet<>();

    @Override
    Set<User> getCache() {
        return cache;
    }

    @Override
    protected Class getEntityClass() {
        return User.class;
    }

    @Override
    public Stream<User> filter(Map<String, String> params) {
        return FiltersUtil.filterUsers(params, getUserDAO());
    }
}
