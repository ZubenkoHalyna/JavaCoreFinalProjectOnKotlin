package dataAccess.saveInFileDAO;

import entities.BaseEntity;

/**
 * Created by g.zubenko on 27.01.2017.
 */
public interface FileAccessInterface {
    <T extends BaseEntity> void readCacheFromFile(DAO<T> dao);
    <T extends BaseEntity> void writeCacheToFile(DAO<T> dao);
}
