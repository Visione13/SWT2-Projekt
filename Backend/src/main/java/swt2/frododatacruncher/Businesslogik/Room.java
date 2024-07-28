package swt2.frododatacruncher.Businesslogik;

import com.fasterxml.jackson.annotation.JsonValue;

public class Room {
    private String roomId;
    private String description;
    private int floor;

    public Room(String roomId, String description, int floor) {
        //TODO: Liste von Room IDs aus Handy App entnehmen
        this.roomId = roomId;
        this.description = description;
        this.floor = floor;
    }

    @JsonValue
    public String getRoomId() {
        return roomId;
    }

    public String getDescription() {
        return description;
    }

    public int getFloor() {
        return floor;
    }

    @Override
    public String toString() {
        return "Room: " + roomId + " " + description + " " + floor;
    }
}
