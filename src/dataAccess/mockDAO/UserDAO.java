package dataAccess.mockDAO;

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
        String id = params.get(User.Fields.ID.toString());
        String login = params.get(User.Fields.LOGIN.toString());
        String password = params.get(User.Fields.PASSWORD.toString());

        Stream<User> userStream = users.stream();

        if (!(id == null || id.isEmpty()) ) {
            long castedId = Long.parseLong(id);
            userStream = userStream.filter(user -> user.getId() == castedId);
        }

        if (!(login == null || login.isEmpty())) {
            userStream = userStream.filter(user -> user.getLogin().equalsIgnoreCase(login));
        }

        if (!(password == null)) {
            userStream = userStream.filter(user -> user.getPassword().equals(password));
        }

        return userStream;
    }

    @Override
    protected Set<User> getStorage() {
        return users;
    }

    @Override
    protected Class getEntityClass() {
        return User.class;
    }

    @Override
    public String getView(User user) {
        return user.getLogin();
    }
}
