package dataAccess.mockDAO;

import entities.Hotel;
import entities.Room;
import exceptions.EntityNotFoundById;
import exceptions.StringToDateConvertingException;
import utils.DateUtil;

import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Created by g.zubenko on 18.01.2017.
 */
class RoomDAO extends DAO<Room>{
    final double PRICE_VARIATION = 0.2;
    private static Set<Room> rooms = new HashSet<>();

    @Override
    protected Set<Room> getStorage() {
        return rooms;
    }

    @Override
    public Set<Room> select(Map<String, String> params) {
        String id        = params.get(Room.Fields.ID.toString());
        String price     = params.get(Room.Fields.PRICE.toString());
        String persons   = params.get(Room.Fields.PERSONS.toString());
        String hotelId   = params.get(Room.Fields.HOTEL_ID.toString());
        String city      = params.get(Room.Fields.CITY.toString());
        String startDate = params.get(Room.Fields.START_DATE.toString());
        String endDate   = params.get(Room.Fields.END_DATE.toString());

        Predicate<Room> predicate=r->true;

        if (!(id == null || id.isEmpty())){
            long castedId = Long.parseLong(id);
            predicate=predicate.and(r->r.getId()==castedId);
        }

        if (!(price == null || price.isEmpty())){
            long castedPrice = Long.parseLong(price);
            predicate=predicate.and(r->
                    r.getPrice() >= castedPrice*(1-PRICE_VARIATION) &&
                    r.getPrice() <= castedPrice*(1+PRICE_VARIATION));
        }

        if (!(persons == null || persons.isEmpty())){
            long castedPersons = Long.parseLong(persons);
            predicate=predicate.and(r->r.getPersons()==castedPersons);
        }

        if (!(hotelId == null || hotelId.isEmpty())){
            long castedHotelId = Long.parseLong(hotelId);
            predicate=predicate.and(r->r.getHotelId()==castedHotelId);
        }

        if (!(city == null || city.isEmpty())){
            predicate=predicate.and(r->{
                try {
                    Hotel h = getHotelDAO().getById(r.getHotelId());
                    if (h.getCity()==null) return false;
                    return h.getCity().equals(city);
                }
                catch (EntityNotFoundById e) {
                    System.err.print(e.getMessage());
                    return false;
                }
            });
        }

        if (!(startDate == null || startDate.isEmpty())) {
            try {
                Date castedStartDate = DateUtil.getInstance().stringToDate(startDate);
                Date castedEndDate;
                if (!(endDate == null || endDate.isEmpty())) {
                    castedEndDate = DateUtil.getInstance().stringToDate(endDate);
                } else {
                    castedEndDate = new Date(castedStartDate.getTime());
                }
                OrderDAO dao = getOrderDAO();
                predicate=predicate.and(r->! (dao.orderExists(r,castedStartDate,castedEndDate)));
            } catch (StringToDateConvertingException e) {
                System.err.print(e.getMessage());
            }
        }

        return rooms.stream().filter(predicate).collect(Collectors.toSet());
    }

    @Override
    protected Class getEntityClass() {
        return Room.class;
    }
}
