package swt2.frododatacruncher.Businesslogik.RobotStates;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import swt2.frododatacruncher.Businesslogik.Robot;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "state"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = RobotIdleState.class, name = "idle"),
        @JsonSubTypes.Type(value = RobotBusyState.class, name = "busy"),
        @JsonSubTypes.Type(value = RobotErrorState.class, name = "error"),
        @JsonSubTypes.Type(value = RobotLoadingState.class, name = "loading"),
        @JsonSubTypes.Type(value = RobotDoneState.class, name = "done")
})
public abstract class RobotState {
    @JsonIgnore
    protected Robot associatedRobot;
    protected String state;

    public RobotState() {
    }

    @JsonIgnore
    public abstract boolean isFree();
    public abstract void onDTOreceived();
    public String getState() {
        return state;
    }

    public Robot getAssociatedRobot(){
        return associatedRobot;
    }
    public void setAssociatedRobot(Robot associatedRobot){
        this.associatedRobot = associatedRobot;
    }

    @Override
    public String toString(){
        return state;
    }

    public static RobotState create(String state){
        switch(state){
            case "idle":
                return new RobotIdleState();
            case "busy":
                return new RobotBusyState();
            case "error":
                return new RobotErrorState();
            case "loading":
                return new RobotLoadingState();
            case "done":
                return new RobotDoneState();
            case "help":
                return new RobotHelpState();
            default:
                return null;
        }
    }
}
