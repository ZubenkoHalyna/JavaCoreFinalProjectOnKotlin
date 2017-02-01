package tests.DAO.cacheBased;

import dataAccess.AbstractDB;
import dataAccess.DAOInterface;
import dataAccess.cacheBased.CacheBasedDB;
import entities.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import tests.DAO.TestDataSupplier;

import java.util.*;

import static org.junit.Assert.*;

/**
 * Created by g.zubenko on 30.01.2017.
 */
@RunWith(value = Parameterized.class)
public class CacheBasedDAOTest<T extends BaseEntity> {
    private DAOInterface<T> DAO;
    private T entity;
    private List<T> entityList;

    public CacheBasedDAOTest(DAOInterface<T> DAO, T entity, List<T> entityList){
        this.DAO = DAO;
        this.entity = entity;
        this.entityList = entityList;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        AbstractDB DB = new CacheBasedDB();
        TestDataSupplier data = TestDataSupplier.getInstance();
        Object[][] result = {{DB.getHotelDAO(), data.getHotel(), data.getHotelList()},
                {DB.getUserDAO(), data.getUser(), data.getUserList()},
                {DB.getRoomDAO(), data.getRoom(), data.getRoomList()},
                {DB.getOrderDAO(), data.getOrder(), data.getOrderList()}};
        return Arrays.asList(result);
    }

    @Test
    public void select() throws Exception {
        Map<String,String> params = new HashMap<>();
        params.put("ID",Long.toString(entity.getId()));
        assertEquals("Method select in class "+DAO.getClass().getName()+" doesn't work correct",1,DAO.select(params).size());
    }

    @Test
    public void selectFirst() throws Exception {
        Map<String,String> params = new HashMap<>();
        params.put("ID",Long.toString(entity.getId()));
        assertTrue("Method selectFirst in class "+DAO.getClass().getName()+" doesn't work correct",DAO.selectFirst(params).isPresent());
    }

    @Test
    public void selectAll() throws Exception {
        assertEquals("Method selectAll in class "+DAO.getClass().getName()+" doesn't work correct",3,DAO.selectAll().size());
    }

    @Test
    public void getById() throws Exception {
        assertEquals("Method getById in class "+DAO.getClass().getName()+" doesn't work correct",entity,DAO.getById(entity.getId()));
    }

    @Test
    public void update() throws Exception {
        assertTrue("Method update in class "+DAO.getClass().getName()+" doesn't work correct: it doesn't update existent entity",
                DAO.update(entity));
        DAO.delete(entity);
        assertFalse("Method update in class "+DAO.getClass().getName()+
                " doesn't work correct: it successfully update nonexistent entity", DAO.update(entity));
    }

    @Before
    public void insertAll() throws Exception {
        DAO.insert(entity);
        assertEquals("Method insert in class "+DAO.getClass().getName()+" doesn't work correct",1,DAO.selectAll().size());
        DAO.insertAll(entityList);
        assertEquals("Method insertAll in class "+DAO.getClass().getName()+" doesn't work correct",3,DAO.selectAll().size());
    }

    @After
    public void delete() throws Exception {
        DAO.delete(entity);
        assertEquals("Method delete in class "+DAO.getClass().getName()+" doesn't work correct",2,DAO.selectAll().size());
        DAO.delete(entityList.get(0));
        assertEquals("Method delete in class "+DAO.getClass().getName()+" doesn't work correct",1,DAO.selectAll().size());
        DAO.delete(entityList.get(1));
        assertEquals("Method delete in class "+DAO.getClass().getName()+" doesn't work correct",0,DAO.selectAll().size());
    }
}