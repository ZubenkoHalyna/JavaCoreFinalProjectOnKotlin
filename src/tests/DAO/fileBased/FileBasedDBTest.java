package tests.DAO.fileBased;

import dataAccess.fileBased.FileBasedDB;
import org.junit.Test;
import tests.DAO.cacheBased.CacheBasedDBTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by g.zubenko on 01.02.2017.
 */
public class FileBasedDBTest extends CacheBasedDBTest {
    MockFileAccess fileAccess = new MockFileAccess();
    FileBasedDB localDB = new FileBasedDB(fileAccess);

    public FileBasedDBTest(){
        DB = localDB;
    }

    @Test
    public void dataIsCorrect() throws Exception {
        fileAccess.newStep();
        localDB.dataIsCorrect();
        assertEquals("Method dataIsCorrect in class FileBasedDB doesn't work correct: it should call readCacheFromFile for each DAO ",
                4,fileAccess.getNumberOfReads());
        localDB.Initialize();
        assertTrue("Method dataIsCorrect in class FileBasedDB doesn't work correct: after initialization data is correct",
                localDB.dataIsCorrect());
        assertEquals("Method Initialize in class FileBasedDB doesn't work correct: it should call writeCacheToFile for each DAO ",
                4,fileAccess.getNumberOfWrites());
    }

}
