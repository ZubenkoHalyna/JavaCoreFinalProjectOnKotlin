package dataAccess.fileBased;

import dataAccess.FiltersUtil;
import entities.Room;

import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

/**
 * Created by g.zubenko on 26.01.2017.
 */
@XmlRootElement
class RoomDAO extends DAO<Room> {
    @XmlElementWrapper
    private List<Room> cache = new ArrayList<>();

    private RoomDAO(){}

    RoomDAO(FileBasedDB DB) {
        super(DB);
    }

    @Override
    protected Class getEntityClass() {
        return Room.class;
    }

    @Override
    public Stream<Room> filter(Map<String, String> params) {
        return FiltersUtil.filterRooms(params, getDB().getRoomDAO(), getDB().getOrderDAO());
    }

    @Override
    void setTransientValuesForEntitiesInCache(){
        for (Room room : cache) {
            room.setHotel(getDB().getHotelDAO().getById(room.getHotelId()));
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
