package swt2.frododatacruncher.Businesslogik.RobotStates;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeName;
import swt2.frododatacruncher.Businesslogik.Mission;
import swt2.frododatacruncher.Businesslogik.Robot;

@JsonTypeName("busy")
public class RobotBusyState extends RobotState{

    public RobotBusyState(){
        super();
        this.state = "busy";
    }

    @JsonIgnore
    @Override
    public boolean isFree() {
        return false;
    }

    public void onDTOreceived(){
        Robot robot = this.associatedRobot;
        robot.getOwner().missionInProgress(robot);
    }
}
