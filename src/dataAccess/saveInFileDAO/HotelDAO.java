package dataAccess.saveInFileDAO;

import dataAccess.FiltersUtil;
import entities.Hotel;

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
class HotelDAO extends DAO<Hotel>  {
    @XmlElementWrapper
    private List<Hotel> cache = new ArrayList<>();

    private HotelDAO(){}

    HotelDAO(FileBasedDB DB) {
        super(DB);
    }

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
        return FiltersUtil.filterHotels(params, getDB().getHotelDAO());
    }
}
