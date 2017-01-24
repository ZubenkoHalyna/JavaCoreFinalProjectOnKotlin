package entities;

/**
 * Created by g.zubenko on 23.01.2017.
 */
public class BaseEntity {
    private long id;
    private String view;

    public BaseEntity(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return view;
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

    public String getView() {
        return view;
    }

    public void setView(String view) {
        this.view = view;
    }
}
