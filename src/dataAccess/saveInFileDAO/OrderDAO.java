package dataAccess.saveInFileDAO;

import dataAccess.FiltersUtil;
import entities.Order;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

/**
 * Created by g.zubenko on 26.01.2017.
 */
class OrderDAO extends DAO<Order> {
    private Set<Order> cache = new HashSet<>();

    @Override
    Set<Order> getCache() {
        return cache;
    }

    @Override
    protected Class getEntityClass() {
        return Order.class;
    }

    @Override
    public Stream<Order> filter(Map<String, String> params) {
        return FiltersUtil.filterOrders(params,getOrderDAO());
    }

    @Override
    void setCacheValues(Set<Order> orders){
        for (Order order : orders) {
            order.setRoom(getRoomDAO().getById(order.getRoomId()));
            order.setUser(getUserDAO().getById(order.getUserId()));
        }
    }
}
