package entities;

public class User extends BaseEntity{
    public enum Fields {ID, LOGIN}
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
}
