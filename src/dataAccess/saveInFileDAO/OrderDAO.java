package dataAccess.saveInFileDAO;

import dataAccess.FiltersUtil;
import entities.Order;

import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.*;
import java.util.stream.Stream;

/**
 * Created by g.zubenko on 26.01.2017.
 */
@XmlRootElement
class OrderDAO extends DAO<Order> {
    @XmlElementWrapper
    private List<Order> cache = new ArrayList<>();

    private OrderDAO(){}

    OrderDAO(FileBasedDB DB) {
        super(DB);
    }

    @Override
    protected Class getEntityClass() {
        return Order.class;
    }

    @Override
    public Stream<Order> filter(Map<String, String> params) {
        return FiltersUtil.filterOrders(params,getDB().getOrderDAO());
    }

    @Override
    void setTransientValuesForEntitiesInCache(){
        for (Order order : cache) {
            order.setRoom(getDB().getRoomDAO().getById(order.getRoomId()));
            order.setUser(getDB().getUserDAO().getById(order.getUserId()));
        }
    }

    @Override
    List<Order> getCache() {
        return cache;
    }

    @Override
    void setCache(List<Order> cache) {
        this.cache = cache;
    }
}
