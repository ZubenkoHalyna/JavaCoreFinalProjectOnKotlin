package dataAccess.fileBased;

import exceptions.ReadFromDBException;
import exceptions.WriteToDBException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.File;
import java.io.IOException;

/**
 * Created by g.zubenko on 27.01.2017.
 */
@XmlRootElement
public final class XMLFileAccess implements FileAccessInterface {
    public void readCacheFromFile(DAO dao){
        File file = new File(System.getProperty("user.dir")
                + File.separator + dao.getEntityClass().getSimpleName()+".xml");
        try {
            JAXBContext context = JAXBContext.newInstance(dao.getClass());
            Unmarshaller unmarshaller = context.createUnmarshaller();
            DAO buf = (DAO)unmarshaller.unmarshal(file);
            dao.setCache(buf.getCache());
            dao.setTransientValuesForEntitiesInCache();
        } catch (JAXBException ex) {
            throw new ReadFromDBException(file,ex);
        }
    }

    public void writeCacheToFile(DAO dao){
        File file = new File(System.getProperty("user.dir")
                + File.separator + dao.getEntityClass().getSimpleName()+".xml");
        try {
            if (!file.exists()) {
                file.createNewFile();
                System.out.println("File '" + file.getAbsolutePath() + "' was created");
            }
            JAXBContext context = JAXBContext.newInstance(dao.getClass());
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(dao, file);
        } catch (JAXBException | IOException ex) {
            throw new WriteToDBException(file,ex);
        }
    }
}
