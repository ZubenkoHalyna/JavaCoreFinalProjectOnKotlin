import dataAccess.mockDAO.DAO;
import entities.Hotel;
import identification.IdProvider;
import identification.UuidProvider;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by g.zubenko on 16.01.2017.
 */
public class Controller {
    private IdProvider idProvider = UuidProvider.getInstance();
    private DAOAbstractFactory DAOProvider = new MockDAOFactory();

    public boolean addHotel(String name, String city){
        DAO<Hotel> daObj = DAOProvider.getHotelDAO();

        daObj.insert(new Hotel(idProvider.getNewId(), name,city));
        return true;
    }

    public Collection<Hotel> findHotelByName(String name){
		DAO<Hotel> daObj = DAOProvider.getHotelDAO();
        Map<String, String> params = new HashMap<>();
        params.put(Hotel.Fields.NAME.toString(),name);

        return daObj.select(params);
    }

    public Collection<Hotel> findHotelByCity(String city){
		DAO<Hotel> daObj = DAOProvider.getHotelDAO();
        Map<String, String> params = new HashMap<>();
        params.put(Hotel.Fields.CITY.toString(),city);

        return daObj.select(params);
    }

    public IdProvider getIdProvider() {
        return idProvider;
    }

    public void setIdProvider(IdProvider idProvider) {
        this.idProvider = idProvider;
    }

    public DAOAbstractFactory getDAOProvider() {
        return DAOProvider;
    }

    public void setDAOProvider(DAOAbstractFactory DAOProvider) {
        this.DAOProvider = DAOProvider;
    }
}
