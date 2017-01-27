import dataAccess.AbstractDB;
import dataAccess.identification.IdSupplier;
import dataAccess.identification.UuIdSupplier;
import dataAccess.saveInFileDAO.SaveInFileDB;

/**
 * Created by g.zubenko on 23.01.2017.
 */
public class Main {
    static AbstractDB DB;
    static IdSupplier idProvider;

    public static void main(String[] args) {
        DB = new SaveInFileDB();
        idProvider = UuIdSupplier.getInstance();

        if (!DB.dataIsCorrect()) {
            DB.Initialize();
        }

        Controller controller = new Controller(idProvider, DB);
        Menu mainMenu = new Menu(new Session(), controller);
        mainMenu.open();

        while (mainMenu.isNotClosed()) {
            try{
                mainMenu.nextStep();
            }
            catch (Exception e){
                System.err.println(e.getMessage());
            }
        }
    }
}
