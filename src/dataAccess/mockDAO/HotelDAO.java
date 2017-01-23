package dataAccess.mockDAO;

import entities.Hotel;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Created by g.zubenko on 16.01.2017.
 */
class HotelDAO extends DAO<Hotel> {
    private static Set<Hotel> hotels = new HashSet<>();

    @Override
    protected Set<Hotel> getStorage() {
        return hotels;
    }

    @Override
    public Set<Hotel> select(Map<String,String> params) {
        String id = params.get(Hotel.Fields.ID.toString());
        String name = params.get(Hotel.Fields.NAME.toString());
        String city = params.get(Hotel.Fields.CITY.toString());

        Predicate<Hotel> predicate = h->true;

        if (!(id == null || id.isEmpty())){
            long castedId = Long.parseLong(id);
            predicate.and(h->h.getId()==castedId);
        }

        if (!(name == null || name.isEmpty())){
            predicate.and(h->h.getName().equals(name));
        }

        if (!(city == null || city.isEmpty())){
            predicate.and(h->h.getCity().equals(city));
        }

        return hotels.stream().filter(predicate).collect(Collectors.toSet());
    }

    @Override
    protected Class getEntityClass() {
        return Hotel.class;
    }
}
