package dataAccess;

import entities.Hotel;
import entities.Order;
import entities.Room;
import entities.User;
import exceptions.StringToDateConvertingException;
import utils.DateUtil;

import java.util.*;
import java.util.stream.Stream;

/**
 * Created by g.zubenko on 26.01.2017.
 */
public final class FiltersUtil {
    public static Stream<Hotel> filterHotels(Map<String,String> params, DAOInterface<Hotel> hotelDAO) {
        String id = params.get(Hotel.FieldsForSearch.ID.toString());
        String name = params.get(Hotel.FieldsForSearch.NAME.toString());
        String city = params.get(Hotel.FieldsForSearch.CITY.toString());

        Stream<Hotel> hotelStream = hotelDAO.selectAll().stream();

        if (!(id == null || id.isEmpty())) {
            long castedId = Long.parseLong(id);
            hotelStream = hotelStream.filter(h -> h.getId() == castedId);
        }

        if (!(name == null || name.isEmpty()) && name.length()>=3) {
            final String lowerCaseName = name.toLowerCase();
            hotelStream = hotelStream.filter(h -> h.getName().toLowerCase().contains(lowerCaseName));
        }

        if (!(city == null || city.isEmpty())) {
            hotelStream = hotelStream.filter(h -> h.getCity().equalsIgnoreCase(city));
        }

        return hotelStream;
    }

    public static Stream<Order> filterOrders(Map<String, String> params, DAOInterface<Order> orderDAO) {
        String id = params.get(Order.FieldsForSearch.ID.toString());
        String roomId = params.get(Order.FieldsForSearch.ROOM_ID.toString());
        String userId = params.get(Order.FieldsForSearch.USER_ID.toString());
        String startDate = params.get(Order.FieldsForSearch.START_DATE.toString());
        String endDate = params.get(Order.FieldsForSearch.END_DATE.toString());

        Stream<Order> orderStream = orderDAO.selectAll().stream();

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
                Date castedStartDate = DateUtil.stringToDate(startDate);
                orderStream = orderStream.filter(o -> o.getStartReservationDate().equals(castedStartDate));
            } catch (StringToDateConvertingException e) {
                System.err.print(e.getMessage());
            }
        }

        if (!(endDate == null || endDate.isEmpty())) {
            try {
                Date castedEndDate = DateUtil.stringToDate(endDate);
                orderStream = orderStream.filter(o -> o.getEndReservationDate().equals(castedEndDate));
            } catch (StringToDateConvertingException e) {
                System.err.print(e.getMessage());
            }
        }

        return orderStream;
    }

    public static Stream<Room> filterRooms(Map<String, String> params, DAOInterface<Room> roomDAO, DAOInterface<Order> orderDAO) {
        String id = params.get(Room.FieldsForSearch.ID.toString());
        String price = params.get(Room.FieldsForSearch.PRICE.toString());
        String priceVariation = params.get(Room.FieldsForSearch.PRICE_VARIATION.toString());
        String persons = params.get(Room.FieldsForSearch.NUMBER_OF_PERSONS.toString());
        String hotelId = params.get(Room.FieldsForSearch.HOTEL_ID.toString());
        String city = params.get(Room.FieldsForSearch.CITY.toString());
        String startDate = params.get(Room.FieldsForSearch.START_DATE.toString());
        String endDate = params.get(Room.FieldsForSearch.END_DATE.toString());

        Stream<Room> roomStream = roomDAO.selectAll().stream();

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
            roomStream = roomStream.filter(r -> r.getHotel().getCity().equalsIgnoreCase(city));
        }

        //Catch if room is free during the period of time
        if (!(startDate == null || startDate.isEmpty())) {
            try {
                Date castedStartDate = DateUtil.stringToDate(startDate);
                Date castedEndDate;
                if (!(endDate == null || endDate.isEmpty())) {
                    castedEndDate = DateUtil.stringToDate(endDate);
                } else {
                    castedEndDate = new Date(castedStartDate.getTime());
                }
                Map<String,String> emptyParams = new HashMap<>();
                List<Order> allOrders = orderDAO.select(emptyParams);
                roomStream = roomStream.filter(room -> !(orderExists(room, castedStartDate, castedEndDate, orderDAO)));
            } catch (StringToDateConvertingException e) {
                System.err.print(e.getMessage());
            }
        }

        return roomStream.distinct();
    }

    public static boolean orderExists(Room room, Date startDate, Date endDate, DAOInterface<Order> orderDAO){
        Optional<Order> order = orderDAO.selectAll().stream().filter(o->o.getRoomId()==room.getId() && (
                        o.getStartReservationDate().equals(startDate) || o.getEndReservationDate().equals(startDate)||
                                o.getStartReservationDate().equals(endDate)   || o.getEndReservationDate().equals(endDate)  ||
                                (o.getStartReservationDate().before(startDate) && o.getEndReservationDate().after(startDate))||
                                (o.getStartReservationDate().before(endDate)   && o.getEndReservationDate().after(endDate)  )||
                                (o.getStartReservationDate().after(startDate)  && o.getEndReservationDate().before(endDate) )
                )
        ).findFirst();

        return order.isPresent();
    }

    public static Stream<User> filterUsers(Map<String, String> params, DAOInterface<User> userDAO) {
        String id = params.get(User.FieldsForSearch.ID.toString());
        String login = params.get(User.FieldsForSearch.LOGIN.toString());
        String password = params.get(User.FieldsForSearch.PASSWORD.toString());

        Stream<User> userStream = userDAO.selectAll().stream();

        if (!(id == null || id.isEmpty()) ) {
            long castedId = Long.parseLong(id);
            userStream = userStream.filter(user -> user.getId() == castedId);
        }

        if (!(login == null || login.isEmpty())) {
            userStream = userStream.filter(user -> user.getLogin().equalsIgnoreCase(login));
        }

        if (!(password == null)) {
            userStream = userStream.filter(user -> user.getPassword().equals(password));
        }

        return userStream;
    }
}
