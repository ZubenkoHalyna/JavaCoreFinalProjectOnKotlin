package dataAccess.saveInFileDAO;

import dataAccess.FiltersUtil;
import entities.Room;

import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.*;
import java.util.stream.Stream;

/**
 * Created by g.zubenko on 26.01.2017.
 */
@XmlRootElement
class RoomDAO extends DAO<Room> {
    @XmlElementWrapper
    private List<Room> cache = new ArrayList<>();

    private RoomDAO(){}

    public RoomDAO(FileBasedDB DB) {
        super(DB);
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
