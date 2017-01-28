import entities.User;
import exceptions.UnAuthorizedSessionException;

/**
 * Created by g.zubenko on 24.01.2017.
 */
public class Session {
    private final String GUEST_LOGIN="guest";
    private User user;
    private boolean isNew = true;

    public Session start(){
        return this;
    }

    public Session(){}

    public Session(User u){
        user = u;
    }

    public boolean close(){
        return true;
    }

    @Override
    public String toString() {
        if (user!=null){
            return user.getLogin();
        }
        else
        {
            return GUEST_LOGIN;
        }
    }

    public User getUser() throws UnAuthorizedSessionException {
        if (user!=null) {
            return user;
        } else {
            throw new UnAuthorizedSessionException();
        }
    }

    public boolean isNew() {
        return isNew;
    }

    public void setNew(boolean aNew) {
        isNew = aNew;
    }

}
