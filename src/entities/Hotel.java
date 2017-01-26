package entities;

import java.io.Serializable;

public class Hotel extends BaseEntity implements Serializable {
    public enum Fields {ID, NAME, CITY}
    private String name;
    private String city;

    public Hotel(long id, String name, String city) {
        super(id);
        this.name = name;
        this.city = city;
    }

    @Override
    public String toString() {
        return name+", "+city;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
