package dataAccess.mockDAO;

import entities.User;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Created by ssizov on 17.01.2017.
 */
class UserDAO extends DAO<User> {
    private static Set<User> users = new HashSet<>();

    @Override
    public Set<User> select(Map<String, String> params) {
        String id = params.get(User.Fields.ID.toString());
        String login = params.get(User.Fields.LOGIN.toString());

        Predicate<User> predicate = u->true;

        if (!(id == null || id.isEmpty()) ) {
            long castedId = Long.parseLong(id);
            predicate.and(user -> user.getId() == castedId);
        }

        if (!(login == null || login.isEmpty())) {
            predicate.and(user -> user.getLogin().equals(login));
        }

        return users.stream().filter(predicate).collect(Collectors.toSet());
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
