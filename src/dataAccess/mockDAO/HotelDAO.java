package dataAccess.mockDAO;

import dataAccess.FiltersUtil;
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
    public Set<Hotel> selectAll() {
        return hotels;
    }

    @Override
    public Stream<Hotel> filter(Map<String,String> params) {
        return FiltersUtil.filterHotels(params,getHotelDAO());
    }

    @Override
    protected Class getEntityClass() {
        return Hotel.class;
    }
}
