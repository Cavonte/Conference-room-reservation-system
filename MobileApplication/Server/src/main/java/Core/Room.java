package Core;

/**
 * Created by Emili on 2016-10-24.
 */

public class Room extends DomainObject{

    private String roomNumber;
    private String description;
    private int roomSize;

    public Room(int roomId, String roomNumber, String description, int roomSize){
        super(roomId);
        this.roomNumber = roomNumber;
        this.description = description;
        this.roomSize = roomSize;
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
