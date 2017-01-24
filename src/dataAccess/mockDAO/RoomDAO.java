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
import java.util.stream.Stream;

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
    public Stream<Room> filter(Map<String, String> params) {
        String id = params.get(Room.Fields.ID.toString());
        String price = params.get(Room.Fields.PRICE.toString());
        String persons = params.get(Room.Fields.PERSONS.toString());
        String hotelId = params.get(Room.Fields.HOTEL_ID.toString());
        String city = params.get(Room.Fields.CITY.toString());
        String startDate = params.get(Room.Fields.START_DATE.toString());
        String endDate = params.get(Room.Fields.END_DATE.toString());

        Stream<Room> roomStream = rooms.stream();

        if (!(id == null || id.isEmpty())) {
            long castedId = Long.parseLong(id);
            roomStream = roomStream.filter(r -> r.getId() == castedId);
        }

        if (!(price == null || price.isEmpty())) {
            long castedPrice = Long.parseLong(price);
            roomStream = roomStream.filter(r ->
                    r.getPrice() >= castedPrice * (1 - PRICE_VARIATION) &&
                            r.getPrice() <= castedPrice * (1 + PRICE_VARIATION));
        }

        if (!(persons == null || persons.isEmpty())) {
            long castedPersons = Long.parseLong(persons);
            roomStream = roomStream.filter(r -> r.getPersons() == castedPersons);
        }

        if (!(hotelId == null || hotelId.isEmpty())) {
            long castedHotelId = Long.parseLong(hotelId);
            roomStream = roomStream.filter(r -> r.getHotelId() == castedHotelId);
        }

        if (!(city == null || city.isEmpty())) {
            roomStream = roomStream.filter(r -> {
                try {
                    Hotel h = getHotelDAO().getById(r.getHotelId());
                    if (h.getCity() == null) return false;
                    return h.getCity().equalsIgnoreCase(city);
                } catch (EntityNotFoundById e) {
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
                roomStream = roomStream.filter(r -> !(dao.orderExists(r, castedStartDate, castedEndDate)));
            } catch (StringToDateConvertingException e) {
                System.err.print(e.getMessage());
            }
        }

        return roomStream;
    }

    @Override
    protected Class getEntityClass() {
        return Room.class;
    }
}
