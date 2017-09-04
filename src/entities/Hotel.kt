package entities

import java.io.Serializable

data class Hotel(var name: String, var city: String): BaseEntity(), Serializable, Comparable<Hotel>{
    constructor():this(kotlin.String(),kotlin.String())
    enum class FieldsForSearch {
        ID, NAME, CITY
    }

    override fun compareTo(other: Hotel): Int {
        if (other == this) return 0
        return if (city.equals(other.city, true)) {
            if (name.compareTo(other.name, true) > 0) 1 else -1
        } else {
            if (city.compareTo(other.city, true) > 0) 1 else -1
        }
    }

    override val view: String get() {
        return "$name, $city"
    }

    override fun toString(): String {
        return "Hotel{name='$name', city='$city'}"
    }
}