package dataAccess.cacheBased;

import dataAccess.FiltersUtil;
import entities.Hotel;

import java.util.*;
import java.util.stream.Stream;

/**
 * Created by g.zubenko on 16.01.2017.
 */
class HotelDAO extends DAO<Hotel> {
    private List<Hotel> hotels = new ArrayList<>();

    public HotelDAO(CacheBasedDB DB) {
        super(DB);
    }

    @Override
    public Stream<Hotel> filter(Map<String,String> params) {
        return FiltersUtil.filterHotels(params,getDB().getHotelDAO());
    }

    @Override
    protected Class getEntityClass() {
        return Hotel.class;
    }

    @Override
    List<Hotel> getCache() {
        return hotels;
    }

    void setCache(List<Hotel> hotels) {
        this.hotels = hotels;
    }
}
