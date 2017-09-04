package entities

import java.io.Serializable
import java.util.UUID
import javax.xml.bind.annotation.XmlElement
import javax.xml.bind.annotation.XmlRootElement

@XmlRootElement
abstract class BaseEntity : Serializable {
    var id: Long = -UUID.randomUUID().leastSignificantBits
        @XmlElement
        private set

    abstract val view: String

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || other !is BaseEntity) return false

        return id == other.id
    }

    override fun hashCode(): Int {
        return (id xor id.ushr(32)).toInt()
    }
}