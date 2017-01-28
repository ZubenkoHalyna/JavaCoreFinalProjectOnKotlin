package entities;

import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;

public class User extends BaseEntity  implements Serializable {
    public enum FieldsForSearch {ID, LOGIN, PASSWORD}
    private String login;
    private String password;

    private User(){}

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }

    @Override
    public String getView() {
        return login;
    }

    @Override
    public String toString() {
        return "User{" +
                "login='" + login + '\'' +
                '}';
    }

    public String getLogin() {
        return login;
    }

    @XmlElement
    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    @XmlElement
    public void setPassword(String password) {
        this.password = password;
    }
}
