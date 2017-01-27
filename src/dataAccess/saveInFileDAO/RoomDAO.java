package dataAccess.saveInFileDAO;

import dataAccess.FiltersUtil;
import entities.Room;

import java.util.*;
import java.util.stream.Stream;

/**
 * Created by g.zubenko on 26.01.2017.
 */
class RoomDAO extends DAO<Room> {
    private List<Room> cache = new ArrayList<>();

    @Override
    protected Class getEntityClass() {
        return Room.class;
    }

    @Override
    public Stream<Room> filter(Map<String, String> params) {
        return FiltersUtil.filterRooms(params, getRoomDAO(), getOrderDAO());
    }

    @Override
    void setTransientValuesForEntitiesInCache(){
        for (Room room : cache) {
            room.setHotel(getHotelDAO().getById(room.getHotelId()));
        }
    }

    @Override
    List<Room> getCache() {
        return cache;
    }

    @Override
    void setCache(List<Room> cache) {
        this.cache = cache;
    }
}
