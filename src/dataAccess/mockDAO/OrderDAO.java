package dataAccess.mockDAO;

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
    private static List<Order> orders = new ArrayList<>();

    @Override
    public List<Order> selectAll() {
        return orders;
    }

    @Override
    public Stream<Order> filter(Map<String, String> params) {
        return FiltersUtil.filterOrders(params, getOrderDAO());
    }

    @Override
    protected Class getEntityClass() {
        return Order.class;
    }
}
