package swt2.frododatacruncher.Businesslogik.RobotStates;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeName;
import swt2.frododatacruncher.Businesslogik.Mission;
import swt2.frododatacruncher.Businesslogik.Robot;

@JsonTypeName("error")
public class RobotErrorState extends RobotState{

    public RobotErrorState(){
        super();
        this.state = "error";
    }

    @JsonIgnore
    @Override
    public boolean isFree() {
        return false;
    }

    public void onDTOreceived(){
        Robot robot = this.getAssociatedRobot();
        if (robot.getAssignedMission() != null){
            robot.getOwner().missionFailed(robot);
        }
    }
}
