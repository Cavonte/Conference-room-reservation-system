package UnitOfWork;

/**
 * Created by Emili on 2016-10-26.
 */

import org.junit.Assert;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import Core.DomainObject;
import Core.Reservation;
import Core.Room;
import Core.Student;

import Mapper.RoomMapper;
import Mapper.ReservationMapper;
import Mapper.StudentMapper;

public class UnitOfWork {

    private static ArrayList<DomainObject> newObjects     =     new ArrayList();
    private static ArrayList<DomainObject> dirtyObjects   =     new ArrayList();
    private static ArrayList<DomainObject> removedObjects =     new ArrayList();


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



    public static void commit(){
        newSave();
        updateDirty();
        deleteRemoved();
    }

    public static void newSave() throws SQLException{
        for(Iterator<DomainObject> objects = newObjects.iterator(); objects.hasNext();){
            DomainObject obj = objects.next();

            checkInstanceSaveToMap(obj);

        }
    }

    private static void checkInstanceSaveToMap(DomainObject obj) throws SQLException{
        if(obj instanceof Room){
            RoomMapper.saveToDB((Room) obj);
        }
        else if(obj instanceof Reservation){
            ReservationMapper.saveToMap((Reservation) obj);
        }
        else if(obj instanceof Student){
            StudentMapper.saveToMap((Student) obj);
        }
    }

    public static void updateDirty() throws SQLException{


        for(Iterator<DomainObject> objects = dirtyObjects.iterator(); objects.hasNext();){
            DomainObject obj = objects.next();

            //checkInstanceSaveToMap(obj);
            if(obj instanceof Room){
                RoomMapper.updateToDB((Room) obj);
            }
            else if(obj instanceof Reservation){
                ReservationMapper.saveToMap((Reservation) obj);
            }
            else if(obj instanceof Student){
                StudentMapper.saveToMap((Student) obj);
            }

        }
    }

    public static void deleteRemoved() throws SQLException{
        for(Iterator<DomainObject>objects = removedObjects.iterator(); objects.hasNext();){
            DomainObject obj = objects.next();
            if(obj instanceof Room){
                RoomMapper.deleteToDB((Room) obj);
            }
            else if(obj instanceof Reservation){
                ReservationMapper.deleteToMap((Reservation)obj);
            }
            else if(obj instanceof Student){
                StudentMapper.deleteToMap((Student)obj);
            }
        }
    }
}
