package dataAccess.saveInFileDAO;

import dataAccess.FiltersUtil;
import entities.Order;

import java.util.*;
import java.util.stream.Stream;

/**
 * Created by g.zubenko on 26.01.2017.
 */
class OrderDAO extends DAO<Order> {
    private List<Order> cache = new ArrayList<>();

    @Override
    protected Class getEntityClass() {
        return Order.class;
    }

    @Override
    public Stream<Order> filter(Map<String, String> params) {
        return FiltersUtil.filterOrders(params,getOrderDAO());
    }

    @Override
    void setTransientValuesForEntitiesInCache(){
        for (Order order : cache) {
            order.setRoom(getRoomDAO().getById(order.getRoomId()));
            order.setUser(getUserDAO().getById(order.getUserId()));
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
