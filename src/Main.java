import dataAccess.AbstractDB;
import dataAccess.cacheBased.CacheDB;
import dataAccess.fileBased.BinariFileAccess;
import dataAccess.fileBased.FileBasedDB;
import dataAccess.fileBased.XMLFileAccess;
import utils.IOUtil;

import java.io.*;

/**
 * Created by g.zubenko on 23.01.2017.
 */
public class Main {
    static AbstractDB DB;

    public static void main(String[] args) {
        configureDB();

        Controller controller = new Controller(DB);
        Menu mainMenu = new Menu(new Session(), controller);
        mainMenu.open();

        while (mainMenu.isNotClosed()) {
            try {
                mainMenu.nextStep();
            } catch (Exception e) {
                System.err.println(e.getMessage());
                //TODO logger
            }
        }
    }

    private static void configureDB() {
        File settingsFile = new File("settings.dat");
        int dataAccessMode;
        try (Reader reader = new FileReader(settingsFile)){
            dataAccessMode = reader.read();
            if (dataAccessMode<1 || dataAccessMode>3){
                throw new IOException();
            }
        } catch (IOException e) {
            //Ask user for dataAccessMode and write it to the settings file
            dataAccessMode = writeSettingsFile(settingsFile);
        }

        switch (dataAccessMode) {
            case 1:
                DB = new FileBasedDB(new XMLFileAccess());
                break;
            case 2:
                DB = new FileBasedDB(new BinariFileAccess());
                break;
            case 3:
                DB = new CacheDB();
                break;
        }

        if (!DB.dataIsCorrect()) {
            try {
                DB.Initialize();
                IOUtil.informUser("DB was filled with initial data");
            } catch (Exception e) {
                DB = new CacheDB();
                IOUtil.informUser("DB files are unavailable. Programme will run in test mode.");
            }
        }
    }

    private static int writeSettingsFile(File settingsFile) {
        int dataAccessMode;
        System.out.println("Program can work in 3 data access modes: \n 1 save data in XML files\n 2 save data in binary files\n 3 don't save data (test mode)");
        dataAccessMode = IOUtil.readInt("data access mode", 1, 3);

        try {
            settingsFile.createNewFile();
            try (Writer r = new FileWriter(settingsFile)) {
                r.write(dataAccessMode);
                System.out.println("Setting \"data access mode\" was saved.");
            } catch (IOException ex) {
            }
        } catch (IOException ex) {}

        return dataAccessMode;
    }
}
