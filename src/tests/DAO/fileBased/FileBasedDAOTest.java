package tests.DAO.fileBased;

import dataAccess.AbstractDB;
import dataAccess.DAOInterface;
import dataAccess.fileBased.FileBasedDB;
import entities.BaseEntity;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import tests.DAO.TestDataSupplier;

import java.util.*;

import static org.junit.Assert.*;

/**
 * Created by g.zubenko on 31.01.2017.
 */
@RunWith(value = Parameterized.class)
public class FileBasedDAOTest<T extends BaseEntity> {
    private static MockFileAccess mockFileAccess = new MockFileAccess();
    private DAOInterface<T> DAO;
    private T entity;
    private List<T> entityList;

    public FileBasedDAOTest(DAOInterface<T> DAO, T entity, List<T> entityList){
        this.DAO = DAO;
        this.entity = entity;
        this.entityList = entityList;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        AbstractDB DB = new FileBasedDB(mockFileAccess);
        TestDataSupplier data = TestDataSupplier.getInstance();
        Object[][] result = {
                {DB.getHotelDAO(), data.getHotel(), data.getHotelList()},
                {DB.getUserDAO(), data.getUser(), data.getUserList()},
                {DB.getRoomDAO(), data.getRoom(), data.getRoomList()},
                {DB.getOrderDAO(), data.getOrder(), data.getOrderList()}
        };
        return Arrays.asList(result);
    }

    @Test
    public void select() throws Exception {
        mockFileAccess.newStep();
        Map<String,String> params = new HashMap<>();
        params.put("ID",Long.toString(entity.getId()));
        assertEquals("Method select in class "+DAO.getClass().getName()+" doesn't work correct",1,DAO.select(params).size());
        checkNumbersOfReadsAndWrites("select",0,0);
    }

    @Test
    public void selectFirst() throws Exception {
        mockFileAccess.newStep();
        Map<String,String> params = new HashMap<>();
        params.put("ID",Long.toString(entity.getId()));
        assertTrue("Method selectFirst in class "+DAO.getClass().getName()+" doesn't work correct",DAO.selectFirst(params).isPresent());
        checkNumbersOfReadsAndWrites("selectFirst",0,0);
    }

    @Test
    public void selectAll() throws Exception {
        mockFileAccess.newStep();
        assertEquals("Method selectAll in class "+DAO.getClass().getName()+" doesn't work correct",3,DAO.selectAll().size());
        checkNumbersOfReadsAndWrites("selectAll",0,0);
    }

    @Test
    public void getById() throws Exception {
        mockFileAccess.newStep();
        assertEquals("Method getById in class "+DAO.getClass().getName()+" doesn't work correct",entity,DAO.getById(entity.getId()));
        checkNumbersOfReadsAndWrites("getById",0,0);
    }

    @Test
    public void updateExistentEntity() throws Exception {
        mockFileAccess.newStep();
        boolean updateResult = DAO.update(entity);
        assertTrue("Method update in class "+DAO.getClass().getName()+" doesn't work correct: it doesn't update existent entity",updateResult);
        checkNumbersOfReadsAndWrites("update",0,1);
    }

    @Test
    public void updateNonExistentEntity() throws Exception {
        DAO.delete(entity);
        mockFileAccess.newStep();
        boolean updateResult = DAO.update(entity);
        assertFalse("Method update in class "+DAO.getClass().getName()+" doesn't work correct: it successfully update nonexistent entity",
                updateResult);
        checkNumbersOfReadsAndWrites("update",0,0);
    }

    @Before
    public void insertAll() throws Exception {
        mockFileAccess.newStep();
        DAO.insert(entity);
        assertEquals("Method insert in class "+DAO.getClass().getName()+" doesn't work correct",1,DAO.selectAll().size());
        checkNumbersOfReadsAndWrites("insert",1,1);
        DAO.insertAll(entityList);
        assertEquals("Method insertAll in class "+DAO.getClass().getName()+" doesn't work correct",3,DAO.selectAll().size());
        checkNumbersOfReadsAndWrites("insertAll",1,2);
    }

    @After
    public void delete() throws Exception {
        mockFileAccess.newStep();
        DAO.delete(entity);
        assertEquals("Method delete in class "+DAO.getClass().getName()+" doesn't work correct",2,DAO.selectAll().size());
        checkNumbersOfReadsAndWrites("delete",0,1);
        DAO.delete(entityList.get(0));
        assertEquals("Method delete in class "+DAO.getClass().getName()+" doesn't work correct",1,DAO.selectAll().size());
        checkNumbersOfReadsAndWrites("delete",0,2);
        DAO.delete(entityList.get(1));
        assertEquals("Method delete in class "+DAO.getClass().getName()+" doesn't work correct",0,DAO.selectAll().size());
        checkNumbersOfReadsAndWrites("delete",1,3);
    }

    public  void checkNumbersOfReadsAndWrites(String methodName, int NumberOfReads, int NumberOfWrites){
        assertEquals("Method "+methodName+" in class "+DAO.getClass().getName()+
                " doesn't work correct: it has wrong numbers of reads",NumberOfReads,
                mockFileAccess.getNumberOfReads());
        assertEquals("Method "+methodName+" in class "+DAO.getClass().getName()+
                " doesn't work correct: it has wrong numbers of writes",NumberOfWrites,
                mockFileAccess.getNumberOfWrites());
    }
}