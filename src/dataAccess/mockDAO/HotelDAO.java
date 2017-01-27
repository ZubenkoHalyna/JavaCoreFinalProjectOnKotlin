package dataAccess.mockDAO;

import dataAccess.FiltersUtil;
import entities.Hotel;

import java.util.*;
import java.util.stream.Stream;

/**
 * Created by g.zubenko on 16.01.2017.
 */
class HotelDAO extends DAO<Hotel> {
    private static List<Hotel> hotels = new ArrayList<>();

    @Override
    public List<Hotel> selectAll() {
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
