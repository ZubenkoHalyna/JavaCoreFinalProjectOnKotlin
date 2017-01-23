package dataAccess.mockDAO;

import entities.Order;
import entities.Room;
import exceptions.StringToDateConvertingException;
import utils.DateUtil;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

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
    public Set<Order> select(Map<String, String> params) {
        String id           = params.get(Order.Fields.ID.toString());
        String roomId       = params.get(Order.Fields.ROOM_ID.toString());
        String userId       = params.get(Order.Fields.USER_ID.toString());
        String startDate    = params.get(Order.Fields.START_DATE.toString());
        String endDate      = params.get(Order.Fields.START_DATE.toString());

        Predicate<Order> predicate = o->true;

        if (!(id == null || id.isEmpty())){
            long castedId = Long.parseLong(id);
            predicate.and(o->o.getId()==castedId);
        }

        if (!(roomId == null || roomId.isEmpty())){
            long castedRoomId = Long.parseLong(roomId);
            predicate.and(o->o.getRoomId()==castedRoomId);
        }

        if (!(userId == null || userId.isEmpty())){
            long castedUserId = Long.parseLong(userId);
            predicate.and(o->o.getUserId()==castedUserId);
        }

        if (!(startDate == null || startDate.isEmpty())){
            try {
                Date castedStartDate = DateUtil.getInstance().stringToDate(startDate);
                predicate.and(o->o.getStartReservationDate().equals(castedStartDate));
            }
            catch (StringToDateConvertingException e){
                System.err.print(e.getMessage());
            }
        }

        if (!(endDate == null || endDate.isEmpty())){
            try {
                Date castedEndDate = DateUtil.getInstance().stringToDate(endDate);
                predicate.and(o->o.getEndReservationDate().equals(castedEndDate));
            }
            catch (StringToDateConvertingException e){
                System.err.print(e.getMessage());
            }
        }

        return orders.stream().filter(predicate).collect(Collectors.toSet());
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
