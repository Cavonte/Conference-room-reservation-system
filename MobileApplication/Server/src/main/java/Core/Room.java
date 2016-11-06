package Core;

/**
 * Created by Emili on 2016-10-24.
 */

public class Room extends DomainObject{

    private String roomNumber;
    private String description;
    private int roomSize;

    public Room(int i, String rn, String d, int rs){
        super(i);
        roomNumber = rn;
        description = d;
        roomSize = rs;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public String getDescription() {
        return description;
    }

    public int getRoomSize() {
        return roomSize;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setRoomSize(int roomSize) {
        this.roomSize = roomSize;
    }

    public String toString(){
        return ("Room Number: " + roomNumber +
                "\nDescription: " + description +
                "\nSize " + roomSize +
                "\nRoom ID: " + super.getId());
    }
}
