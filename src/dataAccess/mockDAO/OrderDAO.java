package dataAccess.mockDAO;

import entities.Order;
import entities.Room;
import exceptions.StringToDateConvertingException;
import utils.DateUtil;

import java.util.*;
import java.util.stream.Stream;

/**
 * Created by g.zubenko on 23.01.2017.
 */
class OrderDAO extends DAO<Order>{
    private static Set<Order> orders = new HashSet<>();

    @Override
    protected Set<Order> getStorage() {
        return orders;
    }

    @Override
    public Stream<Order> filter(Map<String, String> params) {
        String id = params.get(Order.Fields.ID.toString());
        String roomId = params.get(Order.Fields.ROOM_ID.toString());
        String userId = params.get(Order.Fields.USER_ID.toString());
        String startDate = params.get(Order.Fields.START_DATE.toString());
        String endDate = params.get(Order.Fields.START_DATE.toString());

        Stream<Order> orderStream = orders.stream();

        if (!(id == null || id.isEmpty())) {
            long castedId = Long.parseLong(id);
            orderStream = orderStream.filter(o -> o.getId() == castedId);
        }

        if (!(roomId == null || roomId.isEmpty())) {
            long castedRoomId = Long.parseLong(roomId);
            orderStream = orderStream.filter(o -> o.getRoomId() == castedRoomId);
        }

        if (!(userId == null || userId.isEmpty())) {
            long castedUserId = Long.parseLong(userId);
            orderStream = orderStream.filter(o -> o.getUserId() == castedUserId);
        }

        if (!(startDate == null || startDate.isEmpty())) {
            try {
                Date castedStartDate = DateUtil.getInstance().stringToDate(startDate);
                orderStream = orderStream.filter(o -> o.getStartReservationDate().equals(castedStartDate));
            } catch (StringToDateConvertingException e) {
                System.err.print(e.getMessage());
            }
        }

        if (!(endDate == null || endDate.isEmpty())) {
            try {
                Date castedEndDate = DateUtil.getInstance().stringToDate(endDate);
                orderStream = orderStream.filter(o -> o.getEndReservationDate().equals(castedEndDate));
            } catch (StringToDateConvertingException e) {
                System.err.print(e.getMessage());
            }
        }

        return orderStream;
    }

    public boolean orderExists(Room room, Date startDate, Date endDate){
        Optional<Order> order = orders.stream().filter(o->o.getRoomId()==room.getId() && (
             o.getStartReservationDate().equals(startDate) || o.getEndReservationDate().equals(startDate)||
             o.getStartReservationDate().equals(endDate)   || o.getEndReservationDate().equals(endDate)  ||
            (o.getStartReservationDate().before(startDate) && o.getEndReservationDate().after(startDate))||
            (o.getStartReservationDate().before(endDate)   && o.getEndReservationDate().after(endDate)  )||
            (o.getStartReservationDate().after(startDate)  && o.getEndReservationDate().before(endDate) )
            )
        ).findFirst();

        return order.isPresent();
    }

    @Override
    protected Class getEntityClass() {
        return Order.class;
    }
}
