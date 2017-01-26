package dataAccess.mockDAO;

import dataAccess.FiltersUtil;
import entities.Room;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

/**
 * Created by g.zubenko on 18.01.2017.
 */
class RoomDAO extends DAO<Room>{
    private static Set<Room> rooms = new HashSet<>();

    @Override
    public Set<Room> selectAll() {
        return rooms;
    }

    @Override
    public Stream<Room> filter(Map<String, String> params) {
        return FiltersUtil.filterRooms(params,getRoomDAO(),getOrderDAO());
    }

    @Override
    protected Class getEntityClass() {
        return Room.class;
    }
}
