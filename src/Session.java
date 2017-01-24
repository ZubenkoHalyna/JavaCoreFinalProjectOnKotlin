import entities.User;
import exceptions.UnAuthorizedSessionException;

import java.util.Optional;

/**
 * Created by g.zubenko on 24.01.2017.
 */
public class Session {
    private final String GUEST_LOGIN="guest";
    private Optional<User> user;

    public Session start(){
        return this;
    }

    public Session(){
        user = Optional.empty();
    }

    public Session(User u){
        user = Optional.of(u);
    }

    public boolean close(){
        return true;
    }

    @Override
    public String toString() {
        if (user.isPresent()){
            return user.get().getLogin();
        }
        else
        {
            return GUEST_LOGIN;
        }
    }

    public User getUser() throws UnAuthorizedSessionException {
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new UnAuthorizedSessionException();
        }
    }
}
