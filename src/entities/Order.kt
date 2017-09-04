package entities

import utils.DateUtil
import java.io.Serializable
import java.util.*
import javax.xml.bind.annotation.XmlElement
import javax.xml.bind.annotation.XmlTransient

class Order(_user: User, _room: Room, var startReservationDate: Date, var endReservationDate: Date) : BaseEntity(), Comparable<Order>, Serializable {
    enum class FieldsForSearch {ID, USER_ID, ROOM_ID, START_DATE, END_DATE }

    var userId = _user.id
        @XmlElement private set
    var roomId = _room.id
        @XmlElement private set

    @Transient
    var user: User = _user
        get() = field
        @XmlTransient
        set(user) {
            userId = user.id
            field = user
        }

    @Transient
    var room: Room = _room
        get() = field
        @XmlTransient
        set(room) {
            roomId = room.id
            field = room
        }

    override fun compareTo(other: Order): Int {
        if (equals(other)) return 0
        if (startReservationDate > other.startReservationDate) return -1
        return if (startReservationDate < other.startReservationDate) 1 else room.compareTo(other.room)
    }

    override val view: String get() {
        return "${room.view}, from ${DateUtil.dateToStr(startReservationDate)} to ${DateUtil.dateToStr(endReservationDate)}"
    }

    override fun toString(): String {
        return "Order{city=${room.hotel.city}, hotel name=${room.hotel.name}, price per day=${room.price}, persons=${room.persons}, start date=${DateUtil.dateToStr(startReservationDate)}, end date=${DateUtil.dateToStr(endReservationDate)}}"
    }
}
