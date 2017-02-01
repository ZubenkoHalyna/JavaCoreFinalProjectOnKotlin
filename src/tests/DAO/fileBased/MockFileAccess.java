package tests.DAO.fileBased;

import dataAccess.fileBased.DAO;
import dataAccess.fileBased.FileAccessInterface;
import entities.BaseEntity;

/**
 * Created by g.zubenko on 31.01.2017.
 */
class MockFileAccess implements FileAccessInterface {
    private int numberOfReads = 0;
    private int numberOfWrites = 0;

    @Override
    public <T extends BaseEntity> void readCacheFromFile(DAO<T> dao) {
        numberOfReads++;
    }

    @Override
    public <T extends BaseEntity> void writeCacheToFile(DAO<T> dao) {
        numberOfWrites++;
    }

    public void newStep(){
        numberOfReads = 0;
        numberOfWrites = 0;
    }

    public int getNumberOfReads() {
        return numberOfReads;
    }

    public void setNumberOfReads(int numberOfReads) {
        this.numberOfReads = numberOfReads;
    }

    public int getNumberOfWrites() {
        return numberOfWrites;
    }

    public void setNumberOfWrites(int numberOfWrites) {
        this.numberOfWrites = numberOfWrites;
    }
}
