package entities;

import java.io.Serializable;

public class Hotel extends BaseEntity implements Serializable {
    public enum FieldsForSearch {ID, NAME, CITY}
    private String name;
    private String city;

    public Hotel(String name, String city) {
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
