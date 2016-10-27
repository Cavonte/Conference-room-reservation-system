package UnitOfWork;

/**
 * Created by Emili on 2016-10-26.
 */
import java.util.ArrayList;
import java.util.List;

import Mapper.DataMapper;

public class UnitOfWork {

    private List newObjects = new ArrayList();
    private List dirtyObjects = new ArrayList();
    private List removedObjects = new ArrayList();

    public UnitOfWork(DataMapper m){
        mapper = m;
    }

    public DataMapper getMapper(){
        return mapper;
    }

    public void fetchById(int id){
        =
    }


}
