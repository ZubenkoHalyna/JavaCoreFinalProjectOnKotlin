package tests;

import dataAccess.AbstractDB;
import dataAccess.FiltersUtil;
import dataAccess.cacheBased.CacheBasedDB;
import entities.Hotel;
import entities.Order;
import entities.Room;
import entities.User;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by g.zubenko on 30.01.2017.
 */
public class FiltersUtilTest {
    private static AbstractDB DB = new CacheBasedDB();
    static {
        DB.Initialize();
    }

    @Test
    public void filterHotelsByName() throws Exception {
        Map<String, String> params = new HashMap<>();
        params.put(Hotel.FieldsForSearch.NAME.toString(),"Hilton");
        assertEquals("Method filterHotels in class FiltersUtil doesn't work correct",
                2, FiltersUtil.filterHotels(params,DB.getHotelDAO()).count());
    }

    @Test
    public void filterHotelsByNotFullName() throws Exception {
        Map<String, String> params = new HashMap<>();
        params.put(Hotel.FieldsForSearch.NAME.toString(),"ilto");
        assertEquals("Method filterHotels in class FiltersUtil doesn't work correct",
                2, FiltersUtil.filterHotels(params,DB.getHotelDAO()).count());
    }

    @Test
    public void filterHotelsByCity() throws Exception {
        Map<String, String> params = new HashMap<>();
        params.put(Hotel.FieldsForSearch.CITY.toString(),"kiev");
        assertEquals("Method filterHotels in class FiltersUtil doesn't work correct",
                2, DB.getHotelDAO().select(params).size());
    }

    @Test
    public void filterHotelsByNameAndCity() throws Exception {
        Map<String, String> params = new HashMap<>();
        params.put(Hotel.FieldsForSearch.CITY.toString(),"kiev");
        params.put(Hotel.FieldsForSearch.NAME.toString(),"Hilton");
        assertEquals("Method filterHotels in class FiltersUtil doesn't work correct",
                1, DB.getHotelDAO().select(params).size());
    }

    @Test
    public void filterHotelsById() throws Exception {
        Map<String, String> params = new HashMap<>();
        params.put(Hotel.FieldsForSearch.ID.toString(),Long.toString(DB.getHotelDAO().selectAll().get(0).getId()));
        assertEquals("Method filterHotels in class FiltersUtil doesn't work correct",
                1, DB.getHotelDAO().select(params).size());
    }

    @Test
    public void filterUsersByLoginAndPassword() throws Exception {
        Map<String, String> params = new HashMap<>();
        params.put(User.FieldsForSearch.LOGIN.toString(),"admin");
        params.put(User.FieldsForSearch.PASSWORD.toString(),"");
        assertEquals("Method filterUsers in class FiltersUtil doesn't work correct",
                1, FiltersUtil.filterUsers(params,DB.getUserDAO()).count());
    }

    @Test
    public void filterUsersNotFound() throws Exception {
        Map<String, String> params = new HashMap<>();
        params.put(User.FieldsForSearch.LOGIN.toString(),"user");
        assertEquals("Method filterUsers in class FiltersUtil doesn't work correct",
                0, FiltersUtil.filterUsers(params,DB.getUserDAO()).count());
    }

    @Test
    public void filterRoomsByPrice() throws Exception {
        Map<String, String> params = new HashMap<>();
        params.put(Room.FieldsForSearch.PRICE.toString(),"1000");
        assertEquals("Method filterRooms in class FiltersUtil doesn't work correct",
                3, FiltersUtil.filterRooms(params,DB.getRoomDAO(),DB.getOrderDAO()).count());
    }

    @Test
    public void filterRoomsByStartDate() throws Exception {
        // There is order on the room in initial data from 10.02.2017 to 20.02.2017
        Map<String, String> params = new HashMap<>();
        params.put(Room.FieldsForSearch.PRICE.toString(),"650");
        params.put(Room.FieldsForSearch.CITY.toString(),"kiev");
        assertEquals("Method filterRooms in class FiltersUtil doesn't work correct: initial data changed",
                1, FiltersUtil.filterRooms(params,DB.getRoomDAO(),DB.getOrderDAO()).count());
        params.put(Room.FieldsForSearch.START_DATE.toString(),"15.02.2017");
        assertEquals("Method filterRooms in class FiltersUtil doesn't work correct: previous reservation wasn't taken into account",
                0, FiltersUtil.filterRooms(params,DB.getRoomDAO(),DB.getOrderDAO()).count());
    }

    @Test
    public void filterRoomsByEndDate() throws Exception {
        // There is order on the room in initial data from 10.02.2017 to 20.02.2017
        Map<String, String> params = new HashMap<>();
        params.put(Room.FieldsForSearch.PRICE.toString(),"650");
        params.put(Room.FieldsForSearch.CITY.toString(),"kiev");
        assertEquals("Method filterRooms in class FiltersUtil doesn't work correct: initial data changed",
                1, FiltersUtil.filterRooms(params,DB.getRoomDAO(),DB.getOrderDAO()).count());
        params.put(Room.FieldsForSearch.START_DATE.toString(),"01.02.2017");
        params.put(Room.FieldsForSearch.END_DATE.toString(),"11.02.2017");
        assertEquals("Method filterRooms in class FiltersUtil doesn't work correct: previous reservation on end date wasn't taken into account",
                0, FiltersUtil.filterRooms(params,DB.getRoomDAO(),DB.getOrderDAO()).count());
    }

    @Test
    public void filterRoomsByPersons() throws Exception {
        Map<String, String> params = new HashMap<>();
        params.put(Room.FieldsForSearch.NUMBER_OF_PERSONS.toString(),"2");
        assertEquals("Method filterRooms in class FiltersUtil doesn't work correct",
                8, FiltersUtil.filterRooms(params,DB.getRoomDAO(),DB.getOrderDAO()).count());
    }

    @Test
    public void filterRoomsByPersonsAndPrice() throws Exception {
        Map<String, String> params = new HashMap<>();
        params.put(Room.FieldsForSearch.NUMBER_OF_PERSONS.toString(),"2");
        params.put(Room.FieldsForSearch.PRICE.toString(),"1100");
        assertEquals("Method filterRooms in class FiltersUtil doesn't work correct",
                1, FiltersUtil.filterRooms(params,DB.getRoomDAO(),DB.getOrderDAO()).count());
    }

    @Test
    public void filterRoomsByHotelName() throws Exception {
        Map<String, String> params = new HashMap<>();
        params.put(Room.FieldsForSearch.HOTEL_NAME.toString(),"hilton");
        assertEquals("Method filterRooms in class FiltersUtil doesn't work correct",
                12, FiltersUtil.filterRooms(params,DB.getRoomDAO(),DB.getOrderDAO()).count());
    }

    @Test
    public void filterRoomsByCity() throws Exception {
        Map<String, String> params = new HashMap<>();
        params.put(Room.FieldsForSearch.CITY.toString(),"odessa");
        assertEquals("Method filterRooms in class FiltersUtil doesn't work correct",
                12, FiltersUtil.filterRooms(params,DB.getRoomDAO(),DB.getOrderDAO()).count());
    }

    @Test
    public void filterRoomsPriceVariation() throws Exception {
        Map<String, String> params = new HashMap<>();
        params.put(Room.FieldsForSearch.PRICE.toString(),"1000");
        params.put(Room.FieldsForSearch.PRICE_VARIATION.toString(),"20");
        assertEquals("Method filterRooms in class FiltersUtil doesn't work correct",
                9, FiltersUtil.filterRooms(params,DB.getRoomDAO(),DB.getOrderDAO()).count());
    }

    @Test
    public void filterOrdersByUser() throws Exception {
        Map<String, String> params = new HashMap<>();
        params.put(Order.FieldsForSearch.USER_ID.toString(),Long.toString(DB.getUserDAO().selectAll().get(0).getId()));
        assertEquals("Method filterOrders in class FiltersUtil doesn't work correct",
                1, FiltersUtil.filterOrders(params,DB.getOrderDAO()).count());
    }

    @Test
    public void filterOrdersById() throws Exception {
        Map<String, String> params = new HashMap<>();
        params.put(Order.FieldsForSearch.ID.toString(),Long.toString(DB.getOrderDAO().selectAll().get(0).getId()));
        assertEquals("Method filterOrders in class FiltersUtil doesn't work correct",
                1, FiltersUtil.filterOrders(params,DB.getOrderDAO()).count());
    }

    @Test
    public void orderExists() throws Exception {
        // There is order on the room in initial data from 10.02.2017 to 20.02.2017
        Map<String, String> params = new HashMap<>();
        params.put(Room.FieldsForSearch.PRICE.toString(), "650");
        params.put(Room.FieldsForSearch.CITY.toString(), "kiev");
        assertTrue("Method orderExists in class FiltersUtil doesn't work correct: initial data changed",
                FiltersUtil.orderExists(DB.getRoomDAO().selectFirst(params).get(),
                        new GregorianCalendar(2017, Calendar.FEBRUARY, 1).getTime(),
                        new GregorianCalendar(2017, Calendar.FEBRUARY, 15).getTime(),
                        DB.getOrderDAO()));
    }

    @Test
    public void orderExistsLongerPeriod() throws Exception {
        // There is order on the room in initial data from 10.02.2017 to 20.02.2017
        Map<String, String> params = new HashMap<>();
        params.put(Room.FieldsForSearch.PRICE.toString(), "650");
        params.put(Room.FieldsForSearch.CITY.toString(), "kiev");
        assertTrue("Method orderExists in class FiltersUtil doesn't work correct: initial data changed",
                FiltersUtil.orderExists(DB.getRoomDAO().selectFirst(params).get(),
                        new GregorianCalendar(2017, Calendar.FEBRUARY, 1).getTime(),
                        new GregorianCalendar(2017, Calendar.FEBRUARY, 30).getTime(),
                        DB.getOrderDAO()));
    }

    @Test
    public void orderNotExists() throws Exception {
        // There is order on the room in initial data from 10.02.2017 to 20.02.2017
        Map<String, String> params = new HashMap<>();
        params.put(Room.FieldsForSearch.PRICE.toString(), "650");
        params.put(Room.FieldsForSearch.CITY.toString(), "kiev");
        assertFalse("Method orderExists in class FiltersUtil doesn't work correct: initial data changed",
                FiltersUtil.orderExists(DB.getRoomDAO().selectFirst(params).get(),
                        new GregorianCalendar(2017, Calendar.FEBRUARY, 1).getTime(),
                        new GregorianCalendar(2017, Calendar.FEBRUARY, 9).getTime(),
                        DB.getOrderDAO()));
    }

}