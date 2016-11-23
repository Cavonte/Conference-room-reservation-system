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

public class UnitOfWork
{

    private static ArrayList<DomainObject> newObjects = new ArrayList();
    private static ArrayList<DomainObject> dirtyObjects = new ArrayList();
    private static ArrayList<DomainObject> removedObjects = new ArrayList();


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

        if(!dirtyObjects.contains(obj) && !newObjects.contains(obj))
        {
            dirtyObjects.add(obj);
        }
    }

    public static void registerDelete(DomainObject obj)
    {
        if(newObjects.remove(obj))
            return;
        dirtyObjects.remove(obj);

        if(!removedObjects.contains(obj))
        {
            removedObjects.add(obj);
        }
    }

    public static void commit() throws ClassNotFoundException, SQLException
    {
        newSave();
        updateDirty();
        deleteRemoved();
    }

    public static void newSave() throws ClassNotFoundException, SQLException
    {
        ArrayList<Room> newRooms = new ArrayList<Room>();
        ArrayList<Student> newStudents = new ArrayList<Student>();
        ArrayList<Reservation> newReservations = new ArrayList<Reservation>();
        for(DomainObject object : newObjects)
        {
            if(object instanceof Room)
            {
                newRooms.add((Room) object);
            }
            else if(object instanceof Reservation)
            {
                newReservations.add((Reservation) object);
            }
            else if(object instanceof Student)
            {
                newStudents.add((Student) object);
            }
        }
        RoomMapper.saveToDB(newRooms);
        ReservationMapper.saveToDB(newReservations);
        StudentMapper.saveToDB(newStudents);

        newObjects.clear();
    }

    public static void updateDirty() throws ClassNotFoundException, SQLException
    {
        ArrayList<Room> dirtyRooms = new ArrayList<Room>();
        ArrayList<Student> dirtyStudents = new ArrayList<Student>();
        ArrayList<Reservation> dirtyReservations = new ArrayList<Reservation>();
        for(DomainObject object : dirtyObjects)
        {
            if(object instanceof Room)
            {
                dirtyRooms.add((Room) object);
            }
            else if(object instanceof Reservation)
            {
                dirtyReservations.add((Reservation) object);
            }
            else if(object instanceof Student)
            {
                dirtyStudents.add((Student) object);
            }
        }

        RoomMapper.updateToDB(dirtyRooms);
        ReservationMapper.updateToDB(dirtyReservations);
        StudentMapper.updateToDB(dirtyStudents);

        dirtyObjects.clear();
    }

    public static void deleteRemoved() throws ClassNotFoundException, SQLException
    {
        ArrayList<Room> removedRooms = new ArrayList<Room>();
        ArrayList<Student> removedStudents = new ArrayList<Student>();
        ArrayList<Reservation> removedReservations = new ArrayList<Reservation>();
        for(DomainObject object : removedObjects)
        {
            if(object instanceof Room)
            {
                removedRooms.add((Room) object);
            }
            else if(object instanceof Reservation)
            {
                removedReservations.add((Reservation) object);
            }
            else if(object instanceof Student)
            {
                removedStudents.add((Student) object);
            }
        }

        RoomMapper.deleteToDB(removedRooms);
        ReservationMapper.deleteToDB(removedReservations);
        StudentMapper.deleteToDB(removedStudents);

        removedObjects.clear();
    }

}
