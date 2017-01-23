package dataAccess.mockDAO;

import entities.User;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by ssizov on 17.01.2017.
 */
class UserDAO extends DAO<User> {
    private static Set<User> users = new HashSet<>();

    @Override
    public Set<User> select(Map<String, String> params) {
        String id = params.get(User.Fields.ID.toString());
        String login = params.get(User.Fields.LOGIN.toString());

        Stream<User> userStream = users.stream();

        if (!(id == null || id.isEmpty()) ) {
            long castedId = Long.parseLong(id);
            userStream = userStream.filter(user -> user.getId() == castedId);
        }

        if (!(login == null || login.isEmpty())) {
            userStream = userStream.filter(user -> user.getLogin().equals(login));
        }

        return userStream.collect(Collectors.toSet());
    }

    @Override
    protected Set<User> getStorage() {
        return users;
    }

    @Override
    protected Class getEntityClass() {
        return User.class;
    }
}
