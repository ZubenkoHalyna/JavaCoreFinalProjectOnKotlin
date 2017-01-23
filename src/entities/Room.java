package entities;

public class Room extends BaseEntity {
    private int price;
    private int persons;
    private long hotelId;
    public enum Fields {ID, PRICE, PERSONS, HOTEL_ID, CITY}

    public Room(long id, int price, int persons, long hotelId) {
        super(id);
        this.price = price;
        this.persons = persons;
        this.hotelId = hotelId;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getPersons() {
        return persons;
    }

    public void setPersons(int persons) {
        this.persons = persons;
    }

    public long getHotelId() {
        return hotelId;
    }

    public void setHotelId(long hotelId) {
        this.hotelId = hotelId;
    }
}
