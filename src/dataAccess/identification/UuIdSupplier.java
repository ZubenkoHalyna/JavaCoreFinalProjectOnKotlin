package dataAccess.identification;

import java.util.UUID;

/**
 * Created by g.zubenko on 17.01.2017.
 */
public final class UuIdSupplier implements IdSupplier {
    //UuIdSupplier realise singleton pattern
    private static UuIdSupplier instance;
    private UuIdSupplier(){}
    static public UuIdSupplier getInstance(){
        if (instance==null) instance=new UuIdSupplier();
        return  instance;
    }

    public long getNewId()
    {
        return -(UUID.randomUUID().getLeastSignificantBits());
    }
}
