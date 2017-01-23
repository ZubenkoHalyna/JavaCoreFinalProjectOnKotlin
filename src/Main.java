import java.util.HashMap;
import java.util.Map;

/**
 * Created by g.zubenko on 23.01.2017.
 */
public class Main {
    public static void main(String[] args) {
        Controller c = new Controller();
        c.createData();

        Map<String,String> params = new HashMap<>();
        params.put("CITY","Kiev");
        params.put("PRICE","1000");
        params.put("START_DATE", "11.02.2017");
        params.put("PRICE","1000");
        System.out.println(c.findHotelByCity("Kiev"));
        System.out.println();
        System.out.println(c.findRoom(params));

       /* OrderDAO d = new OrderDAO();
        Date dd=DateUtil.getInstance().stringToDate("11.02.2017");
        System.out.println(d.orderExists(new RoomDAO().getById(2), dd, dd));*/
    }
}
