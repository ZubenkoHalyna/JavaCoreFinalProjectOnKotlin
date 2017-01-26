package dataAccess.saveInFileDAO;

import dataAccess.FiltersUtil;
import entities.Room;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

/**
 * Created by g.zubenko on 26.01.2017.
 */
class RoomDAO extends DAO<Room> {
    private Set<Room> cache = new HashSet<>();

    @Override
    Set<Room> getCache() {
        return cache;
    }

    @Override
    protected Class getEntityClass() {
        return Room.class;
    }

    @Override
    public Stream<Room> filter(Map<String, String> params) {
        return FiltersUtil.filterRooms(params, getRoomDAO(), getOrderDAO());
    }

    @Override
    void setCacheValues(Set<Room> rooms){
        for (Room room : rooms) {
            room.setHotel(getHotelDAO().getById(room.getHotelId()));
        }
    }
}
