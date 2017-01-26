package dataAccess.mockDAO;

import dataAccess.FiltersUtil;
import entities.User;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

/**
 * Created by ssizov on 17.01.2017.
 */
class UserDAO extends DAO<User> {
    private static Set<User> users = new HashSet<>();

    @Override
    public Stream<User> filter(Map<String, String> params) {
        return FiltersUtil.filterUsers(params,getUserDAO());
    }

    @Override
    public Set<User> selectAll() {
        return users;
    }

    @Override
    protected Class getEntityClass() {
        return User.class;
    }
}
