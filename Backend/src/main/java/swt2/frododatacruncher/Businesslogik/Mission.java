package swt2.frododatacruncher.Businesslogik;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import swt2.frododatacruncher.Businesslogik.Json.MissionDeserializer;

import java.time.LocalDateTime;

@JsonDeserialize(using = MissionDeserializer.class)
public class Mission {
    private String missionId;
    @JsonIgnore
    private LocalDateTime timestamp;
    @JsonProperty("roomId")
    private Room destination;
    @JsonIgnore
    private Robot assignedRobot;
    @JsonIgnore
    private boolean isCompleted;
    private String phoneId;

    public Mission(String missionId, String phoneId, LocalDateTime timestamp, Room destination) {
        this.missionId = missionId;
        this.phoneId = phoneId;
        this.timestamp = timestamp;
        this.destination = destination;
        this.assignedRobot = null;
        this.isCompleted = false;
    }

    public String getMissionId() {
        return missionId;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public Room getDestination() {
        return destination;
    }

    public Robot getAssignedRobot() {
        return assignedRobot;
    }

    public String getPhoneId() {
        return phoneId;
    }

    public void setAssignedRobot(Robot assignedRobot) {
        this.assignedRobot = assignedRobot;
    }

    @JsonIgnore
    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public String missionDTO(){
        String result = "";
        ObjectMapper mapper = new ObjectMapper();
        try{
            result = mapper.writeValueAsString(this);
        }catch(JsonProcessingException e){
            //TODO: Exceptionhandling
            e.printStackTrace();
        }
        return result;
    }

    public String destinationToString(){
        return "Raum "+this.destination.getRoomId();
    }
    @Override
    public String toString() {
        String result = "Mission: " + this.missionId + " " + this.timestamp + " " + this.destination + " ";
        if(this.assignedRobot != null){
            result += this.assignedRobot.getRobotId() + " \n";
        }
        return result;
    }
}
