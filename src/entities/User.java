package entities;

import java.io.Serializable;

public class User extends BaseEntity  implements Serializable {
    public enum Fields {ID, LOGIN, PASSWORD}
    private String login;
    private String password;

    public User(long id, String login, String password) {
        super(id);
        this.login = login;
        this.password = password;
    }

    @Override
    public String toString() {
        return login;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
