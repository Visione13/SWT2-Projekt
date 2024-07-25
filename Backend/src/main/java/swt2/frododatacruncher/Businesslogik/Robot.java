package swt2.frododatacruncher.Businesslogik;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import swt2.frododatacruncher.Businesslogik.Json.RobotDeserializer;
import swt2.frododatacruncher.Businesslogik.Json.RobotSerializer;
import swt2.frododatacruncher.Businesslogik.RobotStates.*;

@JsonSerialize(using = RobotSerializer.class)
@JsonDeserialize(using = RobotDeserializer.class)
public class Robot {

    @JsonIgnore
    private Dispatcher owner;

    private String robotId;

    @JsonUnwrapped
    private RobotState state;

    private int floor;

    @JsonAlias("mission")
    private Mission assignedMission;

    public Robot(){
    }
    public Robot(String robotId, RobotState state, int floor, Mission assignedMission) {
        this.robotId = robotId;
        this.floor = floor;
        this.state = state;

        //Robots don't get a destination until a mission is accepted
        this.assignedMission = assignedMission;
    }

    public void executeMission(Mission mission){
        //Update Robot Information
        this.state = new RobotBusyState();
        this.assignedMission = mission;
        mission.setAssignedRobot(this);
    }

    public void printInfo(){
        if(this.assignedMission == null)
            System.out.printf("Robot: %21s %7s %5d%n", robotId, state, floor);
        else
            System.out.printf("Robot: %21s %7s %5d %8s%n", robotId, state, floor, this.assignedMission.destinationToString());
    }

    @Override
    public String toString(){
        String result = "Robot: " + this.robotId + "  " + this.state + "     " +this.floor+ " ";
        if(this.assignedMission != null){
            result += this.assignedMission.toString();
        }
        return result;
    }

    /*
    Getters and Setters
     */

    @JsonProperty("robotId")
    public String getRobotId() {
        return robotId;
    }

    @JsonProperty("robotId")
    public void setRobotId(String robotId) {
        this.robotId = robotId;
    }

    @JsonProperty("state")
    public RobotState getState() {
        return state;
    }

    @JsonProperty("state")
    public void setState(RobotState state) {
        this.state = state;
    }

    @JsonProperty("floor")
    public int getFloor() {
        return floor;
    }

    @JsonProperty("floor")
    public void setFloor(int floor) {
        this.floor = floor;
    }

    @JsonProperty("mission")
    public Mission getAssignedMission() {
        return assignedMission;
    }

    @JsonProperty("mission")
    public void setAssignedMission(Mission mission) {
        this.assignedMission = mission;
    }

    @JsonIgnore
    public Dispatcher getOwner(){
        return owner;
    }

    @JsonIgnore
    public void setOwner(Dispatcher owner) {
        this.owner = owner;
    }
}
