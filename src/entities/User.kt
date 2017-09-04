package entities

import java.io.Serializable

data class User(var login: String, var password: String):BaseEntity(), Serializable {
    enum class FieldsForSearch {
        ID, LOGIN, PASSWORD
    }

    constructor():this("","")

    override val view: String get() {
        return login
    }

    override fun toString(): String {
        return "User{login='$login'}"
    }
}