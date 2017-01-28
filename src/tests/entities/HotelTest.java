package tests.entities;

import entities.Hotel;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


/**
 * Created by g.zubenko on 28.01.2017.
 */
public class HotelTest {
    @Test
    public void getName() throws Exception {
        Hotel h = new Hotel("name","");
        assertEquals("Method getName in class Hotel doesn't work correct","name",h.getName());
    }

    @Test
    public void setName() throws Exception {
        Hotel h = new Hotel("","");
        h.setName("name");
        assertEquals("Method setName in class Hotel doesn't work correct","name",h.getName());
    }

    @Test
    public void getCity() throws Exception {
        Hotel h = new Hotel("","city");
        assertEquals("Method getCity in class Hotel doesn't work correct","city",h.getCity());
    }

    @Test
    public void setCity() throws Exception {
        Hotel h = new Hotel("","");
        h.setCity("city");
        assertEquals("Method setName in class Hotel doesn't work correct","city",h.getCity());
    }

    @Test
    public void getView() throws Exception {
        Hotel h = new Hotel("name","city");
        assertEquals("Method getView in class Hotel doesn't work correct","name, city",h.getView());
    }

    @Test
    public void toStringTest() throws Exception {
        Hotel h = new Hotel("name","city");
        assertEquals("Method toString in class Hotel doesn't work correct","Hotel{name='name', city='city'}",h.toString());
    }
}