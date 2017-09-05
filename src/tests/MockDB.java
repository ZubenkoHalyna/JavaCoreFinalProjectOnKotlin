package tests;

import dataAccess.AbstractDB;
import dataAccess.DAOInterface;
import entities.Hotel;
import entities.Order;
import entities.Room;
import entities.User;

import java.util.*;

/**
 * Created by g.zubenko on 01.02.2017.
 */
public class MockDB implements AbstractDB {

    class MockDAO<T> implements DAOInterface<T> {
        private int selectNumber;
        private int selectFirstNumber;
        private int selectAllNumber;
        private int getByIdNumber;
        private int insertNumber;
        private int insertAllNumber;
        private int updateNumber;
        private int deleteNumber;

        public void nextStep() {
            selectNumber = 0;
            selectFirstNumber = 0;
            selectAllNumber = 0;
            getByIdNumber = 0;
            insertNumber = 0;
            insertAllNumber = 0;
            updateNumber = 0;
            deleteNumber = 0;
        }

        @Override
        public List select(Map params) {
            selectNumber++;
            return new ArrayList();
        }

        @Override
        public Optional selectFirst(Map params) {
            selectFirstNumber++;
            return Optional.empty();
        }

        @Override
        public List selectAll() {
            selectAllNumber++;
            return new ArrayList();
        }

        @Override
        public T getById(long id) {
            getByIdNumber++;
            return null;
        }

        @Override
        public boolean insert(Object item) {
            insertNumber++;
            return false;
        }

        @Override
        public boolean insertAll(Collection items) {
            insertAllNumber++;
            return false;
        }

        @Override
        public boolean update(Object item) {
            updateNumber++;
            return false;
        }

        @Override
        public boolean delete(Object item) {
            deleteNumber++;
            return false;
        }

        public int getSelectNumber() {
            return selectNumber;
        }

        public int getSelectFirstNumber() {
            return selectFirstNumber;
        }

        public int getSelectAllNumber() {
            return selectAllNumber;
        }

        public int getGetByIdNumber() {
            return getByIdNumber;
        }

        public int getInsertNumber() {
            return insertNumber;
        }

        public int getInsertAllNumber() {
            return insertAllNumber;
        }

        public int getUpdateNumber() {
            return updateNumber;
        }

        public int getDeleteNumber() {
            return deleteNumber;
        }
    }

    private MockDAO<Hotel> hotelDAO = new MockDAO<>();
    private MockDAO<User> userDAO = new MockDAO<>();
    private MockDAO<Room> roomDAO = new MockDAO<>();
    private MockDAO<Order> orderDAO = new MockDAO<>();

    @Override
    public MockDAO<User> getUserDAO() {
        return userDAO;
    }

    @Override
    public MockDAO<Hotel> getHotelDAO() {
        return hotelDAO;
    }

    @Override
    public MockDAO<Room> getRoomDAO() {
        return roomDAO;
    }

    @Override
    public MockDAO<Order> getOrderDAO() {
        return orderDAO;
    }

    @Override
    public boolean dataIsCorrect() {
        return false;
    }

    @Override
    public void Initialize() {

    }
}


