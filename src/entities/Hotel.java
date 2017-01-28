package entities;

import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;

public class Hotel extends BaseEntity implements Serializable, Comparable<Hotel> {
    public enum FieldsForSearch {ID, NAME, CITY}
    private String name;
    private String city;

    public Hotel(){}

    public Hotel(String name, String city) {
        this.name = name;
        this.city = city;
    }

    @Override
    public String getView(){
        return name+", "+city;
    }

    @Override
    public String toString() {
        return "Hotel{" +
                "name='" + name + '\'' +
                ", city='" + city + '\'' +
                '}';
    }

    @Override
    public int compareTo(Hotel h) {
        if (h.equals(this)) return 0;
        if (getCity().equals(h.getCity())) {
            return (getName().compareToIgnoreCase(h.getName()) > 0) ? 1 : -1;
        } else {
            return (getCity().compareToIgnoreCase(h.getCity()) > 0) ? 1 : -1;
        }
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
}
