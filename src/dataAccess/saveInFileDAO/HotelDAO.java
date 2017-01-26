package dataAccess.saveInFileDAO;

import dataAccess.FiltersUtil;
import entities.Hotel;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

/**
 * Created by g.zubenko on 26.01.2017.
 */
class HotelDAO extends DAO<Hotel>  {
    private Set<Hotel> cache = new HashSet<>();

    @Override
    Set<Hotel> getCache() {
        return cache;
    }

    @Override
    protected Class getEntityClass() {
        return Hotel.class;
    }

    @Override
    public Stream<Hotel> filter(Map<String, String> params) {
        return FiltersUtil.filterHotels(params, getHotelDAO());
    }
}
