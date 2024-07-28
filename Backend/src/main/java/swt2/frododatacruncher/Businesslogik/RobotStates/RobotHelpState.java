package swt2.frododatacruncher.Businesslogik.RobotStates;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeName;
import swt2.frododatacruncher.Businesslogik.Robot;

@JsonTypeName("help")
public class RobotHelpState extends RobotState{

  public RobotHelpState(){
    super();
    this.state = "help";
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
