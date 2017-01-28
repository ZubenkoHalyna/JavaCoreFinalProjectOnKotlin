package dataAccess.cacheBased;

import dataAccess.FiltersUtil;
import entities.Room;

import java.util.*;
import java.util.stream.Stream;

/**
 * Created by g.zubenko on 18.01.2017.
 */
class RoomDAO extends DAO<Room>{
    private static List<Room> rooms = new ArrayList<>();

    public RoomDAO(CacheDB DB) {
        super(DB);
    }

    @Override
    public List<Room> selectAll() {
        return rooms;
    }

    @Override
    public Stream<Room> filter(Map<String, String> params) {
        return FiltersUtil.filterRooms(params,getDB().getRoomDAO(),getDB().getOrderDAO());
    }

    @Override
    protected Class getEntityClass() {
        return Room.class;
    }
}
