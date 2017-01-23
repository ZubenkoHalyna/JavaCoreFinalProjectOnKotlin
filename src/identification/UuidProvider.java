package identification;

import java.util.UUID;

/**
 * Created by g.zubenko on 17.01.2017.
 */
public final class UuidProvider implements IdProvider {
    //UuidProvider realise singleton pattern
    private static UuidProvider instance;
    private UuidProvider(){}

    static public UuidProvider getInstance(){
        if (instance==null) instance=new UuidProvider();
        return  instance;
    }

    public long getNewId()
    {
        return -(UUID.randomUUID().getLeastSignificantBits());
    }
}
