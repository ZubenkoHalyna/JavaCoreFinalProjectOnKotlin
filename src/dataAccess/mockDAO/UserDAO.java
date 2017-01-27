package dataAccess.mockDAO;

import dataAccess.FiltersUtil;
import entities.User;

import java.util.*;
import java.util.stream.Stream;

/**
 * Created by ssizov on 17.01.2017.
 */
class UserDAO extends DAO<User> {
    private static List<User> users = new ArrayList<>();

    public UserDAO(MockDB DB) {
        super(DB);
    }

    @Override
    public Stream<User> filter(Map<String, String> params) {
        return FiltersUtil.filterUsers(params,getUserDAO());
    }

    @Override
    public List<User> selectAll() {
        return users;
    }

    @Override
    protected Class getEntityClass() {
        return User.class;
    }
}
