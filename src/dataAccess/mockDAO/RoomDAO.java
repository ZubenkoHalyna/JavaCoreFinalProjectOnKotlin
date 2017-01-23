package dataAccess.mockDAO;

import dataAccess.exceptions.EntityNotFoundById;
import entities.Hotel;
import entities.Room;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by g.zubenko on 18.01.2017.
 */
public class RoomDAO extends DAO<Room>{
    private Set<Room> rooms = new HashSet<>();

    @Override
    protected Set<Room> getStorage() {
        return rooms;
    }

    @Override
    public Set<Room> select(Map<String, String> params) {
        String id       = params.get(Room.Fields.ID.toString());
        String price    = params.get(Room.Fields.PRICE.toString());
        String persons  = params.get(Room.Fields.PERSONS.toString());
        String hotelId  = params.get(Room.Fields.HOTEL_ID.toString());
        String city     = params.get(Room.Fields.CITY.toString());

        Stream<Room> hotelStream = rooms.stream();

        if (!(id == null || id.isEmpty())){
            long castedId = Long.parseLong(id);
            hotelStream = hotelStream.filter(r->r.getId()==castedId);
        }

        if (!(price == null || price.isEmpty())){
            long castedPrice = Long.parseLong(price);
            hotelStream = hotelStream.filter(r->r.getPrice()==castedPrice);
        }

        if (!(persons == null || persons.isEmpty())){
            long castedPersons = Long.parseLong(persons);
            hotelStream = hotelStream.filter(r->r.getPersons()==castedPersons);
        }

        if (!(hotelId == null || hotelId.isEmpty())){
            long castedHotelId = Long.parseLong(hotelId);
            hotelStream = hotelStream.filter(r->r.getHotelId()==castedHotelId);
        }

        if (!(city == null || city.isEmpty())){
            hotelStream = hotelStream.filter(r->{
                try {
                    Hotel h = getHotelDAO().getById(r.getHotelId());
                    if (h.getCity()==null) return false;
                    return h.getCity().equals(city);
                }
                catch (EntityNotFoundById e) {
                    System.err.print(e.getMessage());
                    return false;
                }
            });
        }

        return hotelStream.collect(Collectors.toSet());
    }

    @Override
    protected Class getEntityClass() {
        return Room.class;
    }
}
