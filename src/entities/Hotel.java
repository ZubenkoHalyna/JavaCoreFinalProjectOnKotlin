package entities;

import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;

public class Hotel extends BaseEntity implements Serializable {
    public enum FieldsForSearch {ID, NAME, CITY}
    private String name;
    private String city;

    public Hotel(){}

    public Hotel(String name, String city) {
        this.name = name;
        this.city = city;
    }

    public String getName() {
        return name;
    }

    @XmlElement
    public void setName(String name) {
        this.name = name;
    }

    @XmlElement
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return name+", "+city;
    }
}
