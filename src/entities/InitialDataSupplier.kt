package entities

import utils.DateUtil
import java.util.ArrayList

object InitialDataSupplier {

    val users: MutableList<User>
    val hotels: MutableList<Hotel>
    val rooms: MutableList<Room>
    val orders: MutableList<Order>

    init {
        users = ArrayList()
        users.add(User("admin", ""))

        hotels = ArrayList()
        hotels.add(Hotel("Hilton", "Kiev"))
        hotels.add(Hotel("Radisson", "Kiev"))
        hotels.add(Hotel("Hilton", "Odessa"))
        hotels.add(Hotel("Metropol", "Odessa"))

        rooms = ArrayList()
        rooms.add(Room(700, 1, hotels[0]))
        rooms.add(Room(700, 1, hotels[0]))

        rooms.add(Room(1000, 2, hotels[0]))
        rooms.add(Room(1000, 2, hotels[0]))

        rooms.add(Room(1500, 3, hotels[0]))
        rooms.add(Room(1500, 3, hotels[0]))

        rooms.add(Room(650, 1, hotels[1]))
        rooms.add(Room(900, 2, hotels[1]))
        rooms.add(Room(1400, 3, hotels[1]))
        rooms.add(Room(600, 1, hotels[2]))
        rooms.add(Room(850, 2, hotels[2]))
        rooms.add(Room(1300, 3, hotels[2]))
        rooms.add(Room(700, 1, hotels[3]))
        rooms.add(Room(950, 2, hotels[3]))
        rooms.add(Room(1450, 3, hotels[3]))
        rooms.add(Room(800, 1, hotels[0]))
        rooms.add(Room(1100, 2, hotels[0]))
        rooms.add(Room(1600, 3, hotels[0]))
        rooms.add(Room(700, 1, hotels[1]))
        rooms.add(Room(1000, 2, hotels[1]))
        rooms.add(Room(1500, 3, hotels[1]))
        rooms.add(Room(650, 1, hotels[2]))
        rooms.add(Room(900, 2, hotels[2]))
        rooms.add(Room(1400, 3, hotels[2]))
        rooms.add(Room(750, 1, hotels[3]))
        rooms.add(Room(1000, 2, hotels[3]))
        rooms.add(Room(1550, 3, hotels[3]))

        orders = ArrayList()
        orders.add(Order(users[0], rooms[6], DateUtil.stringToDate("10.02.2017"),
                DateUtil.stringToDate("20.02.2017")))
    }

}