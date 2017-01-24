import dataAccess.mockDAO.MockDAOFactory;
import identification.UuidProvider;

/**
 * Created by g.zubenko on 23.01.2017.
 */
public class Main {

    public static void main(String[] args) {
        Controller controller = new Controller(UuidProvider.getInstance(), MockDAOFactory.getInstance());

        controller.createData();

        Menu mainMenu = new Menu(new Session(), controller);
        mainMenu.open();

        while (mainMenu.isNotClosed()) {
            mainMenu.nextStep();
        }
/*
        controller.createData();
        Map<String,String> params = new HashMap<>();
        params.put("CITY","Kiev");
        params.put("PRICE","1000");
        params.put("START_DATE", "15.02.2017");
        params.put("PRICE","1000");
        System.out.println(controller.findHotelByCity("Kiev"));
        System.out.println();
        System.out.println(controller.findRoom(params));
*/
    }
}
