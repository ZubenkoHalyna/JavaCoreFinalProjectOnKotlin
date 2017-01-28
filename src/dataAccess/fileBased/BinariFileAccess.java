package dataAccess.fileBased;

import entities.BaseEntity;
import exceptions.ReadFromDBException;
import exceptions.WriteToDBException;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by g.zubenko on 27.01.2017.
 */
public class BinariFileAccess implements FileAccessInterface {
    public <T extends BaseEntity> void readCacheFromFile(DAO<T> dao) {
        ArrayList<T> items;
        File file = new File(dao.getEntityClass().getSimpleName() + ".dat");
        if (file.exists()) {
            try (ObjectInputStream in = new ObjectInputStream(new BufferedInputStream(
                    new FileInputStream(file.getAbsolutePath())))) {
                items = (ArrayList) in.readObject();
            } catch (Exception ex) {
                throw new ReadFromDBException(file,ex);
            }
        }
        else {
            throw new ReadFromDBException(file);
        }
        dao.setCache(items);
        dao.setTransientValuesForEntitiesInCache();
    }

    public void writeCacheToFile(DAO dao){
        File file = new File(dao.getEntityClass().getSimpleName() + ".dat");
        try {
            if (!file.exists()) {
                file.createNewFile();
                System.out.println("File '" + file.getAbsolutePath() + "' was created");
            }
            try (ObjectOutputStream out = new ObjectOutputStream(new BufferedOutputStream(
                    new FileOutputStream(file.getAbsolutePath())))) {
                out.writeObject(dao.getCache());
            } catch (IOException ex) {
                throw new WriteToDBException(file);
            }
        } catch (IOException e) {
            throw  new WriteToDBException(file);
        }
    }
}
