package Core;

/**
 * Created by Emili on 2016-10-26.
 */

public class DomainObject {

    private long id;

    public DomainObject(long id){
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
