package entities;

public class User extends BaseEntity{
    private String login;
    private String password;
    public enum Fields {ID, LOGIN}

    public User(long id, String login, String password) {
        super(id);
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}
