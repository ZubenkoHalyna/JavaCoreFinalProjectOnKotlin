package entities;

import dataAccess.identification.IdSupplier;
import dataAccess.identification.UuIdSupplier;

import java.io.Serializable;

/**
 * Created by g.zubenko on 23.01.2017.
 */
public abstract class BaseEntity implements Serializable {
    static IdSupplier idProvider = UuIdSupplier.getInstance();
    private long id;

    public BaseEntity() {
        this.id = idProvider.getNewId();
    }

    public BaseEntity(long id) {
        this.id = id;
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
}
