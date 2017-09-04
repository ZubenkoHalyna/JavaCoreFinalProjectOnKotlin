package tests.entities;

import entities.Hotel;
import entities.Room;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by g.zubenko on 28.01.2017.
 */
public class RoomTest {
    private Hotel firstHotelStub = new Hotel();
    private Hotel secondHotelStub = new Hotel();

    @Test
    public void getPrice() throws Exception {
        Room r = new Room(1000,0, firstHotelStub);
        assertEquals("Method getPrice in class Room doesn't work correct",1000,r.getPrice());
    }

    @Test
    public void setPrice() throws Exception {
        Room r = new Room(0,0, firstHotelStub);
        r.setPrice(1000);
        assertEquals("Method setPrice in class Room doesn't work correct",1000,r.getPrice());
    }

    @Test
    public void getPersons() throws Exception {
        Room r = new Room(0,2, firstHotelStub);
        assertEquals("Method getPersons in class Room doesn't work correct",2,r.getPersons());
    }

    @Test
    public void setPersons() throws Exception {
        Room r = new Room(0,0, firstHotelStub);
        r.setPersons(2);
        assertEquals("Method setPrice in class Room doesn't work correct",2,r.getPersons());
    }

    @Test
    public void getHotelId() throws Exception {
        Room r = new Room(0,0, firstHotelStub);
        assertEquals("Method getHotelId in class Room doesn't work correct",firstHotelStub.getId(),r.getHotelId());
    }

    @Test
    public void getHotel() throws Exception {
        Room r = new Room(0,0, firstHotelStub);
        assertEquals("Method getHotel in class Room doesn't work correct",firstHotelStub,r.getHotel());
    }

    @Test
    public void setHotel() throws Exception {
        Room r = new Room(0,0, secondHotelStub);
        r.setHotel(firstHotelStub);
        assertEquals("Method setHotel in class Room doesn't work correct",firstHotelStub,r.getHotel());
        assertEquals("Method setHotel in class Room doesn't work correct: it doesn't set HotelId",
                firstHotelStub,r.getHotel());
    }

    @Test
    public void compareToEqualRoom() throws Exception {
        Room firstRoom = new Room(1000,2,firstHotelStub);
        Room secondRoom = new Room(1000,2,firstHotelStub);
        assertEquals("Method compareTo in class Room doesn't work correct",0,firstRoom.compareTo(secondRoom));
    }

    @Test
    public void compareToNotEqualPrice() throws Exception {
        Room firstRoom = new Room(900,2,firstHotelStub);
        Room secondRoom = new Room(1000,2,firstHotelStub);
        assertEquals("Method compareTo in class Room doesn't work correct",1,firstRoom.compareTo(secondRoom));
    }

    @Test
    public void compareToNotEqualPersons() throws Exception {
        Room firstRoom = new Room(1000,1,firstHotelStub);
        Room secondRoom = new Room(1000,2,firstHotelStub);
        assertEquals("Method compareTo in class Room doesn't work correct",1,firstRoom.compareTo(secondRoom));
    }

    @Test
    public void compareToNotEqualHotelNames() throws Exception {
        Room firstRoom = new Room(1000,2,firstHotelStub);
        Room secondRoom = new Room(1000,2,secondHotelStub);
        firstHotelStub.setName("name");
        secondHotelStub.setName("nameE");
        firstHotelStub.setCity("city");
        secondHotelStub.setCity("city");
        assertEquals("Method compareTo in class Room doesn't work correct",-1,firstRoom.compareTo(secondRoom));
        firstHotelStub.setName("Name");
        assertEquals("Method compareTo in class Room doesn't work correct: it doesn't use ignore case",
                -1,firstRoom.compareTo(secondRoom));
    }

    @Test
    public void compareToNotEqualCities() throws Exception {
        Room firstRoom = new Room(1000,2,firstHotelStub);
        Room secondRoom = new Room(1000,2,secondHotelStub);
        firstHotelStub.setName("name");
        secondHotelStub.setName("name");
        firstHotelStub.setCity("cityY");
        secondHotelStub.setCity("city");
        assertEquals("Method compareTo in class Room doesn't work correct",1,firstRoom.compareTo(secondRoom));
        firstHotelStub.setCity("CityY");
        assertEquals("Method compareTo in class Room doesn't work correct: it doesn't use ignore case",
                1,firstRoom.compareTo(secondRoom));
    }

    @Test
    public void equals() throws Exception {
        Room firstRoom = new Room(1000,2,firstHotelStub);
        Room secondRoom = new Room(1000,2,firstHotelStub);
        assertTrue("Method equals in class Room doesn't work correct",firstRoom.equals(secondRoom));
    }

    @Test
    public void notEqualPrice() throws Exception {
        Room firstRoom = new Room(900,2,firstHotelStub);
        Room secondRoom = new Room(1000,2,firstHotelStub);
        assertFalse("Method equals in class Room doesn't work correct",firstRoom.equals(secondRoom));
    }

    @Test
    public void notEqualPersons() throws Exception {
        Room firstRoom = new Room(1000,1,firstHotelStub);
        Room secondRoom = new Room(1000,2,firstHotelStub);
        assertFalse("Method equals in class Room doesn't work correct",firstRoom.equals(secondRoom));
    }

    @Test
    public void notEqualHotels() throws Exception {
        Room firstRoom = new Room(1000,1,firstHotelStub);
        Room secondRoom = new Room(1000,2,secondHotelStub);
        assertFalse("Method equals in class Room doesn't work correct",firstRoom.equals(secondRoom));
    }

    @Test
    public void hashCodeTest() throws Exception {
        Room firstRoom = new Room(1000,2,firstHotelStub);
        Room secondRoom = new Room(1000,2,firstHotelStub);
        assertEquals("Method hashCode in class Room doesn't work correct",firstRoom.hashCode(),secondRoom.hashCode());
    }

    @Test
    public void getView() throws Exception {
        firstHotelStub = new Hotel("name","city");
        Room r = new Room(1000,2,firstHotelStub);
        assertEquals("Method getView in class Room doesn't work correct","name, city, 1000 USD per day, 2 persons",
                r.getView());
    }

}