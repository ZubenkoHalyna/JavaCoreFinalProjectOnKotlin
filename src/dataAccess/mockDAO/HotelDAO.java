package dataAccess.mockDAO;

import entities.Hotel;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

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
    public Stream<Hotel> filter(Map<String,String> params) {
        String id = params.get(Hotel.Fields.ID.toString());
        String name = params.get(Hotel.Fields.NAME.toString());
        String city = params.get(Hotel.Fields.CITY.toString());

        Stream<Hotel> hotelStream = hotels.stream();

        if (!(id == null || id.isEmpty())) {
            long castedId = Long.parseLong(id);
            hotelStream = hotelStream.filter(h -> h.getId() == castedId);
        }

        if (!(name == null || name.isEmpty()) && name.length()>=3) {
            final String lowerCaseName = name.toLowerCase();
            hotelStream = hotelStream.filter(h -> h.getName().toLowerCase().contains(lowerCaseName));
        }

        if (!(city == null || city.isEmpty())) {
            hotelStream = hotelStream.filter(h -> h.getCity().equalsIgnoreCase(city));
        }

        return hotelStream;
    }

    @Override
    protected Class getEntityClass() {
        return Hotel.class;
    }

    @Override
    public String getView(Hotel hotel) {
        return hotel.getName()+", "+hotel.getCity();
    }
}
