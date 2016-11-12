package UnitOfWork;

/**
 * Created by Emili on 2016-10-26.
 */

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


    public static void registerNew(DomainObject client)
    {
        if(existsInLists(client))
            throw new IllegalArgumentException("Object already exists in one or more of the UnitOfWork lists");

        newObjects.add(client);
    }

    private static boolean existsInLists(DomainObject client)
    {
        return (dirtyObjects.contains(client) || removedObjects.contains(client) || newObjects.contains(client));
    }

    public static void registerDirty(DomainObject obj)
    {
        if(removedObjects.contains(obj))
            throw new IllegalArgumentException("Object already exists in the removed objects list");

        if(!dirtyObjects.contains(obj) && !newObjects.contains(obj)){
            dirtyObjects.add(obj);
        }
    }

    public static void registerDelete(DomainObject obj)
    {
        if(newObjects.remove(obj))
            return;
        dirtyObjects.remove(obj);

        if(!removedObjects.contains(obj)){
            removedObjects.add(obj);
        }
    }

    public static void commit() throws ClassNotFoundException,SQLException {
        newSave();
        updateDirty();
        deleteRemoved();
    }

    public static void newSave() throws ClassNotFoundException,SQLException{
        for(Iterator<DomainObject> objects = newObjects.iterator(); objects.hasNext();){
            DomainObject obj = objects.next();

            if(obj instanceof Room){
                RoomMapper.saveToDB((Room) obj);
            }
            else if(obj instanceof Reservation){
                ReservationMapper.saveToDB((Reservation) obj);
            }
            else if(obj instanceof Student){
                StudentMapper.saveToDB((Student) obj);
            }

        }
        newObjects.clear();
    }

    public static void updateDirty() throws ClassNotFoundException,SQLException
    {
        for(Iterator<DomainObject> objects = dirtyObjects.iterator(); objects.hasNext();){
            DomainObject obj = objects.next();

            if(obj instanceof Room){
                RoomMapper.updateToDB((Room) obj);
            }
            else if(obj instanceof Reservation){
                ReservationMapper.updateToDB((Reservation) obj);
            }
            else if(obj instanceof Student){
                StudentMapper.updateToDB((Student) obj);
            }

        }
        dirtyObjects.clear();
    }

    public static void deleteRemoved() throws ClassNotFoundException,SQLException{
        for(Iterator<DomainObject>objects = removedObjects.iterator(); objects.hasNext();){
            DomainObject obj = objects.next();
            if(obj instanceof Room){
                RoomMapper.deleteToDB((Room) obj);
            }
            else if(obj instanceof Reservation){
                ReservationMapper.deleteToDB((Reservation)obj);
            }
            else if(obj instanceof Student){
                StudentMapper.deleteToDB((Student)obj);
            }
        }
        removedObjects.clear();
    }

}
