package entities;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.UUID;

/**
 * Created by g.zubenko on 23.01.2017.
 */
@XmlRootElement
public abstract class BaseEntity implements Serializable {
    private long id;
    abstract String getView();

    public BaseEntity() {
        this.id = getNewId();
    }

    private long getNewId()
    {
        return -(UUID.randomUUID().getLeastSignificantBits());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BaseEntity that = (BaseEntity) o;

        return id == that.id;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    public long getId() {
        return id;
    }

    @XmlElement
    private void setId(long id){
        this.id=id;
    }
}
