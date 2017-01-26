package dataAccess.mockDAO;

import entities.Hotel;
import entities.Room;
import exceptions.EntityNotFoundById;
import exceptions.StringToDateConvertingException;
import utils.DateUtil;

import java.util.*;
import java.util.stream.Stream;

/**
 * Created by g.zubenko on 18.01.2017.
 */
class RoomDAO extends DAO<Room>{
    private static Set<Room> rooms = new HashSet<>();

    @Override
    protected Set<Room> getStorage() {
        return rooms;
    }

    @Override
    public Stream<Room> filter(Map<String, String> params) {
        String id = params.get(Room.Fields.ID.toString());
        String price = params.get(Room.Fields.PRICE.toString());
        String priceVariation = params.get(Room.Fields.PRICE_VARIATION.toString());
        String persons = params.get(Room.Fields.NUMBER_OF_PERSONS.toString());
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
            final int castedPriceVariation =
                    (priceVariation == null || priceVariation.isEmpty())
                    ? 0
                    : Integer.parseInt(priceVariation);

            roomStream = roomStream.filter(r ->
                    r.getPrice() >= castedPrice * (1 - castedPriceVariation/100.0) &&
                            r.getPrice() <= castedPrice * (1 + castedPriceVariation/100.0));
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
                Date castedStartDate = DateUtil.stringToDate(startDate);
                Date castedEndDate;
                if (!(endDate == null || endDate.isEmpty())) {
                    castedEndDate = DateUtil.stringToDate(endDate);
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

    @Override
    public String getView(Room room) {
        return getHotelDAO().getById(room.getHotelId()).getView()+", "+
                room.getPrice()+"$ per day, "+
                room.getPersons()+" person"+((room.getPersons()==1)?"":"s");
    }
}
