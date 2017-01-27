package dataAccess.saveInFileDAO;

import dataAccess.FiltersUtil;
import entities.Hotel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

/**
 * Created by g.zubenko on 26.01.2017.
 */
class HotelDAO extends DAO<Hotel>  {
    private List<Hotel> cache = new ArrayList<>();

    @Override
    protected Class getEntityClass() {
        return Hotel.class;
    }

    @Override
    void setTransientValuesForEntitiesInCache() {
       // there no transient values in class Hotel
    }

    @Override
    List<Hotel> getCache() {
        return cache;
    }

    @Override
    void setCache(List<Hotel> cache) {
        this.cache = cache;
    }

    @Override
    public Stream<Hotel> filter(Map<String, String> params) {
        return FiltersUtil.filterHotels(params, getHotelDAO());
    }
}
