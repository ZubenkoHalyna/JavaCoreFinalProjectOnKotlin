package entities;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;

import static entities.FieldType.*;

public class Room extends BaseEntity implements Serializable, Comparable<Room> {
    public enum FieldsForSearch {
        ID(                 false, LONG,  "id"),
        PRICE(              true,  INT,   "price per day"),
        PRICE_VARIATION(    false, INT,   "acceptable price variation in percents"),
        NUMBER_OF_PERSONS(  true,  INT,   "number of persons"),
        HOTEL_ID(           false, LONG,  "hotel id"),
        CITY(               true,  STRING,"city"),
        START_DATE(         true,  DATE,  "start date of reservation"),
        END_DATE(           false, DATE,  "end date of reservation");

        public final String description;
        public final boolean directForUser;
        public final FieldType type;

        FieldsForSearch(boolean directForUser, FieldType type, String description) {
            this.description = description;
            this.directForUser = directForUser;
            this.type = type;
        }
    }

    private int price;
    private int persons;
    private long hotelId;
    private transient Hotel cacheHotel;

    private Room(){}

    public Room(int price, int persons, Hotel hotel) {
        this.price = price;
        this.persons = persons;
        this.cacheHotel = hotel;
        this.hotelId = hotel.getId();
    }

    @Override
    public int compareTo(Room r) {
        if (equals(r)) return 0;
        if (price>(r.getPrice())) {
            return -1;
        }
        if (price==r.getPrice()){
            return getId()>r.getId()? -1:1;
        }
        return 1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Room room = (Room) o;

        if (price != room.price) return false;
        if (persons != room.persons) return false;
        return hotelId == room.hotelId;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + price;
        result = 31 * result + persons;
        result = 31 * result + (int) (hotelId ^ (hotelId >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return cacheHotel+", "+
                price+" USD per day, "+
                persons+" person"+((persons==1)?"":"s");
    }

    public int getPrice() {
        return price;
    }

    @XmlElement
    public void setPrice(int price) {
        this.price = price;
    }

    public int getPersons() {
        return persons;
    }

    @XmlElement
    public void setPersons(int persons) {
        this.persons = persons;
    }

    public long getHotelId() {
        return hotelId;
    }

    @XmlElement
    private void setHotelId(long hotelId) {
        this.hotelId = hotelId;
    }

    @XmlTransient
    public void setHotel(Hotel hotel) {
        hotelId = hotel.getId();
        cacheHotel = hotel;
    }

    public Hotel getHotel() {
        return cacheHotel;
    }
}
