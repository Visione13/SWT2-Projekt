package swt2.frododatacruncher.Businesslogik.RobotStates;

import com.fasterxml.jackson.annotation.JsonIgnore;
import swt2.frododatacruncher.Businesslogik.Robot;

public class RobotDoneState extends RobotState {
  public RobotDoneState() {
    super();
    this.state = "done";
  }

  @JsonIgnore
  @Override
  public boolean isFree() {
    return false;
  }

  public void onDTOreceived(){
    Robot robot = this.getAssociatedRobot();
    if (robot.getAssignedMission() != null){
      robot.getOwner().missionWasCompleted(robot.getAssignedMission());
    }
  }
}
