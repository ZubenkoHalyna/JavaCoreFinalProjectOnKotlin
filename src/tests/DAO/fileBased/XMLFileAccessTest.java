package tests.DAO.fileBased;

import dataAccess.AbstractDB;
import dataAccess.DAOInterface;
import dataAccess.fileBased.FileBasedDB;
import dataAccess.fileBased.XMLFileAccess;
import entities.BaseEntity;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import tests.DAO.TestDataSupplier;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by g.zubenko on 31.01.2017.
 */
@RunWith(value = Parameterized.class)
public class XMLFileAccessTest<T extends BaseEntity>{
    static AbstractDB XMLDB;
    private DAOInterface<T> DAO;
    private T entity;
    private List<T> entityList;

    public XMLFileAccessTest(DAOInterface<T> DAO, T entity, List<T> entityList){
        this.DAO = DAO;
        this.entity = entity;
        this.entityList = entityList;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        XMLDB = new FileBasedDB(new XMLFileAccess());
        TestDataSupplier data = TestDataSupplier.getInstance();
        Object[][] result = {
                {XMLDB.getHotelDAO(), data.getHotel(), data.getHotelList()},
                {XMLDB.getUserDAO(), data.getUser(), data.getUserList()},
                {XMLDB.getRoomDAO(), data.getRoom(), data.getRoomList()},
                {XMLDB.getOrderDAO(), data.getOrder(), data.getOrderList()}
        };
        return Arrays.asList(result);
    }

    @BeforeClass
    public static void createTmpFiles() throws Exception {

        File file = new File("Hotel.xml");
        if (file.exists()) {
            File newFile = new File("tmpHotel.xml");
            file.renameTo(newFile);
        }
        file = new File("User.xml");
        if (file.exists()) {
            File newFile = new File("tmpUser.xml");
            file.renameTo(newFile);
        }
        file = new File("Room.xml");
        if (file.exists()) {
            File newFile = new File("tmpRoom.xml");
            file.renameTo(newFile);
        }
        file = new File("Order.xml");
        if (file.exists()) {
            File newFile = new File("tmpOrder.xml");
            file.renameTo(newFile);
        }
    }

    @Test
    public void insert() throws Exception {
        DAO.insert(entity);
        assertEquals("Method insert in class "+DAO.getClass().getName()+" doesn't work correct",1,DAO.selectAll().size());
        // Next line create new instance of the class FileBasedDB. This instance should get data from DB file.
        // So next assert statement check if data in DB file was written correctly.
        FileBasedDB newDB = new FileBasedDB(new XMLFileAccess());
        assertEquals("Method insert in class "+DAO.getClass().getName()+" doesn't work correct: it doesn't write correct data in XML file",
                1,getNewDAO(newDB,DAO).selectAll().size());

        DAO.insertAll(entityList);

        assertEquals("Method insertAll in class DAO doesn't work correct",3,DAO.selectAll().size());
        newDB = new FileBasedDB(new XMLFileAccess());
        assertEquals("Method insertAll in class "+DAO.getClass().getName()+" doesn't work correct: it doesn't write correct data in XML file",
                3,getNewDAO(newDB,DAO).selectAll().size());
    }

    @Test
    public void delete() throws Exception {
        DAO.insert(entity);
        DAO.insertAll(entityList);
        DAO.delete(entity);
        assertEquals("Method delete in class "+DAO.getClass().getName()+" doesn't work correct",2,DAO.selectAll().size());
        FileBasedDB newDB = new FileBasedDB(new XMLFileAccess());
        assertEquals("Method delete in class "+DAO.getClass().getName()+" doesn't work correct: it doesn't write correct data in XML file",2,
                getNewDAO(newDB,DAO).selectAll().size());
        DAO.delete(entityList.get(0));
        assertEquals("Method delete in class "+DAO.getClass().getName()+" doesn't work correct",1,DAO.selectAll().size());
        newDB = new FileBasedDB(new XMLFileAccess());
        assertEquals("Method delete in class "+DAO.getClass().getName()+" doesn't work correct: it doesn't write correct data in XML file",1,
                getNewDAO(newDB,DAO).selectAll().size());
        DAO.delete(entityList.get(1));
        assertEquals("Method delete in class "+DAO.getClass().getName()+" doesn't work correct",0,DAO.selectAll().size());
        newDB = new FileBasedDB(new XMLFileAccess());
        assertEquals("Method delete in class "+DAO.getClass().getName()+" doesn't work correct: it doesn't write correct data in XML file",0,
                getNewDAO(newDB,DAO).selectAll().size());
    }

    public DAOInterface getNewDAO(FileBasedDB newDB, DAOInterface DAO){
        if (DAO.getClass().getSimpleName().equals("HotelDAO")){return newDB.getHotelDAO();}
        if (DAO.getClass().getSimpleName().equals("UserDAO")){return newDB.getUserDAO();}
        if (DAO.getClass().getSimpleName().equals("RoomDAO")){return newDB.getRoomDAO();}
        return newDB.getOrderDAO();
    }

    @AfterClass
    public static void deleteTmpFiles() throws Exception {
        File file = new File("Hotel.xml");
        if (file.exists()) {
            file.delete();
            File newFile = new File("tmpHotel.xml");
            if (newFile.exists()) {
                newFile.renameTo(file);
            }
        }
        file = new File("User.xml");
        if (file.exists()) {
            file.delete();
            File newFile = new File("tmpUser.xml");
            if (newFile.exists()) {
                newFile.renameTo(file);
            }
        }
        file = new File("Room.xml");
        if (file.exists()) {
            file.delete();
            File newFile = new File("tmpRoom.xml");
            if (newFile.exists()) {
                newFile.renameTo(file);
            }
        }
        file = new File("Order.xml");
        if (file.exists()) {
            file.delete();
            File newFile = new File("tmpOrder.xml");
            if (newFile.exists()) {
                newFile.renameTo(file);
            }
        }
    }
}
