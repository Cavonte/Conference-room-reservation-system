package UnitOfWork;

/**
 * Created by Emili on 2016-10-26.
 */

import org.junit.Assert;

import java.util.ArrayList;
import java.util.Iterator;

import Core.DomainObject;
import Core.Reservation;
import Core.Room;
import Core.Student;

public class UnitOfWork {

    private static ArrayList<Object> newObjects     =     new ArrayList();
    private static ArrayList<Object> dirtyObjects   =     new ArrayList();
    private static ArrayList<Object> removedObjects =     new ArrayList();


    public static void registerNew(DomainObject client){

        Assert.assertNotNull(client.getId());
        Assert.assertTrue(!dirtyObjects.contains(client));
        Assert.assertTrue(removedObjects.contains(client));
        Assert.assertTrue(!newObjects.contains(client));
        newObjects.add(client);
    }

    public static void registerDirty(DomainObject obj){

        Assert.assertNotNull(obj.getId());
        Assert.assertTrue(!removedObjects.contains(obj));

        if(!dirtyObjects.contains(obj) && !newObjects.contains(obj)){
            dirtyObjects.add(obj);
        }

    }

    public static void registerDelete(DomainObject obj){
        Assert.assertNotNull(obj.getId());

        if(newObjects.remove(obj))
            return;
        dirtyObjects.remove(obj);

        if(!removedObjects.contains(obj)){
            removedObjects.add(obj);
        }
    }

    //Register clean missing
    //rollback missing


    /*
    public static void commit(){
        save();
        updateDirty();
        deleteRemoved();
    }

    public static void save(){
        for(Iterator<Object> objects = newObjects.iterator(); objects.hasNext();){
            Object obj = (Object) objects.next();

            if(obj.getClass() == Room.class){
                //RoomMapper.saveToMap(obj);
            }
            else if(obj.getClass() == Reservation.class){
                //ReservationMapper.saveToMap(obj);
            }
            else if(obj.getClass() == Student.class){
                //StudentMapper.saveToMap(obj);
            }

        }
    }

    public static void updateDirty(){

        for(Iterator<Object> objects = dirtyObjects.iterator(); objects.hasNext();){
            Object obj = (Object) objects.next();

            if(obj.getClass() == Room.class){
                //RoomMapper.saveToMap(obj);
            }
            else if(obj.getClass() == Reservation.class){
                //ReservationMapper.saveToMap(obj);
            }
            else if(obj.getClass() == Student.class){
                //StudentMapper.saveToMap(obj);
            }

        }
    }

    public static void deleteRemoved(){
        for(Iterator<Object>objects = removedObjects.iterator(); objects.hasNext();){
            Object obj = (Object) objects.next();
            if(obj.getClass() == Room.class){
                //RoomMapper.deleteToMap(obj);
            }
            else if(obj.getClass() == Reservation.class){
                //ReservationMapper.deleteToMap(obj);
            }
            else if(obj.getClass() == Student.class){
                //StudentMapper.deleteToMap(obj);
            }
        }
    }

*/
}
