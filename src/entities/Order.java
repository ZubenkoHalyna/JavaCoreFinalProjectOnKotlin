package entities;

import utils.DateUtil;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by g.zubenko on 23.01.2017.
 */
public class Order extends BaseEntity implements Comparable<Order>, Serializable {
    public enum FieldsForSearch {ID, USER_ID, ROOM_ID, START_DATE, END_DATE, }
    private long userId;
    private transient User cacheUser;
    private long roomId;
    private transient Room cacheRoom;
    private Date startReservationDate;
    private Date endReservationDate;

    public Order(User user, Room room, Date startReservationDate, Date endReservationDate) {
        this.userId = user.getId();
        this.cacheUser = user;
        this.roomId = room.getId();
        this.cacheRoom = room;
        this.startReservationDate = startReservationDate;
        this.endReservationDate = endReservationDate;
    }

    @Override
    public int compareTo(Order o) {
        if (equals(o)) return 0;
        if (startReservationDate.after(o.getStartReservationDate())) {
            return -1;
        }
        if (startReservationDate.equals(endReservationDate)){
            return roomId>o.getId()? -1:1;
        }
        return 1;
    }

    @Override
    public String toString() {
        return cacheRoom+", from "+
                DateUtil.dateToStr(startReservationDate)+ " to "+
                DateUtil.dateToStr(endReservationDate);
    }

    public long getUserId() {
        return userId;
    }

    public User getUser(User user) {
        return cacheUser;
    }

    public void setUser(User user) {
        userId = user.getId();
        cacheUser = user;
    }

    public long getRoomId() {
        return roomId;
    }

    public Room getRoom() {
        return cacheRoom;
    }

    public void setRoom(Room room) {
        roomId = room.getId();
        cacheRoom = room;
    }

    public Date getStartReservationDate() {
        return startReservationDate;
    }

    public void setStartReservationDate(Date startReservationDate) {
        this.startReservationDate = startReservationDate;
    }

    public Date getEndReservationDate() {
        return endReservationDate;
    }

    public void setEndReservationDate(Date endReservationDate) {
        this.endReservationDate = endReservationDate;
    }
}
