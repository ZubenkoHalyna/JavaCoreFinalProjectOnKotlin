package entities

import java.io.Serializable
import javax.xml.bind.annotation.XmlTransient
import entities.FieldType.*
import javax.xml.bind.annotation.XmlElement

class Room(var price: Int, var persons: Int, _hotel:Hotel) : BaseEntity(), Serializable, Comparable<Room> {
    var hotelId: Long = _hotel.id
        @XmlElement
        private set

    @Transient
    // Hotel is a cashed value. It shouldn't be saved in xml or binary file.
    var hotel: Hotel = _hotel
        get() = field
        @XmlTransient
        set(hotel) {
            hotelId = hotel.id
            field = hotel
        }

    enum class FieldsForSearch(val directForUser: Boolean, val type: FieldType, val description: String) {
        ID(false, LONG, "id"),
        PRICE(true, INT, "price per day"),
        PRICE_VARIATION(false, INT, "acceptable price variation in percents"),
        NUMBER_OF_PERSONS(true, INT, "number of persons"),
        HOTEL_ID(false, LONG, "hotel id"),
        HOTEL_NAME(true, STRING, "hotel name"),
        CITY(true, STRING, "city"),
        START_DATE(true, DATE, "start date of reservation"),
        END_DATE(false, DATE, "end date of reservation")
    }

    override fun compareTo(other: Room): Int {
        if (equals(other)) return 0
        if (persons > other.persons) return -1
        if (persons < other.persons) return 1
        if (price > other.price) return -1
        return if (price < other.price) 1 else hotel.compareTo(other.hotel)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || other !is Room) return false

        return price == other.price && persons == other.persons && hotelId == other.hotelId
    }

    override fun hashCode(): Int {
        var result = price
        result = 31 * result + persons
        result = 31 * result + (hotelId xor hotelId.ushr(32)).toInt()
        return result
    }

    override val view: String get() {
        return "${hotel.view}, $price USD per day, $persons person${if (persons == 1) "" else "s"}"
    }

    override fun toString(): String {
        return "Room{price=$price, persons=$persons, hotel name=${hotel.name}, city=${hotel.city}}"
    }
}