package tests.DAO.cacheBased;

import dataAccess.AbstractDB;
import dataAccess.DAOInterface;
import dataAccess.cacheBased.CacheBasedDB;
import entities.Hotel;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by g.zubenko on 30.01.2017.
 */
public class CacheBasedDBTest {
    protected AbstractDB DB = new CacheBasedDB();

    @Test
    public void getUserDAO() throws Exception {
        assertTrue("Method getUserDAO in class "+DB.getClass().getSimpleName()+" doesn't work correct",
                DB.getUserDAO()==DB.getUserDAO());
    }

    @Test
    public void getHotelDAO() throws Exception {
        assertTrue("Method getHotelDAO in class "+DB.getClass().getSimpleName()+" doesn't work correct",
                DB.getHotelDAO()==DB.getHotelDAO());
    }

    @Test
    public void getRoomDAO() throws Exception {
        assertTrue("Method getRoomDAO in class "+DB.getClass().getSimpleName()+" doesn't work correct",
                DB.getRoomDAO()==DB.getRoomDAO());
    }

    @Test
    public void getOrderDAO() throws Exception {
        assertTrue("Method getRoomDAO in class "+DB.getClass().getSimpleName()+" doesn't work correct",
                DB.getOrderDAO()==DB.getOrderDAO());
    }

    @Test
    public void dataIsCorrect() throws Exception {
        assertFalse("Method dataIsCorrect in class "+DB.getClass().getSimpleName()+
                " doesn't work correct: before initialization data is incorrect",
                DB.dataIsCorrect());
        DB.Initialize();
        assertTrue("Method dataIsCorrect in class "+DB.getClass().getSimpleName()+
                " doesn't work correct: after initialization data is correct",
                DB.dataIsCorrect());
    }

    @Test
    public void initialize() throws Exception {
        DB.Initialize();
        DAOInterface<Hotel> hotelDAO = DB.getHotelDAO();
        int numberOfHotels = hotelDAO.selectAll().size();
        DB.Initialize();
        DAOInterface<Hotel> newHotelDAO = DB.getHotelDAO();
        assertTrue("Method initialize in class "+DB.getClass().getSimpleName()+
                " doesn't work correct: it shouldn't create new hotelDAO",
                newHotelDAO == hotelDAO);
        assertTrue("Method initialize in class "+DB.getClass().getSimpleName()+
                " doesn't work correct: it should return the same data every time it runs",
                newHotelDAO.selectAll().size()==numberOfHotels);
    }

}