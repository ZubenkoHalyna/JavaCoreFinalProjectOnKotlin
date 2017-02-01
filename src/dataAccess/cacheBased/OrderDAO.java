package dataAccess.cacheBased;

import dataAccess.FiltersUtil;
import entities.Order;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

/**
 * Created by g.zubenko on 23.01.2017.
 */
class OrderDAO extends DAO<Order>{
    private List<Order> orders = new ArrayList<>();

    public OrderDAO(CacheBasedDB DB) {
        super(DB);
    }

    @Override
    public Stream<Order> filter(Map<String, String> params) {
        return FiltersUtil.filterOrders(params, getDB().getOrderDAO());
    }

    @Override
    protected Class getEntityClass() {
        return Order.class;
    }

    @Override
    List<Order> getCache() {
        return orders;
    }

    void setCache(List<Order> orders) {
        this.orders = orders;
    }
}
