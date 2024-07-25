package swt2.frododatacruncher.Businesslogik.RobotStates;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("idle")
public class RobotIdleState extends RobotState{

    public RobotIdleState(){
        super();
        this.state = "idle";
    }

    @JsonIgnore
    @Override
    public boolean isFree() {
        return true;
    }

    @Override public void onDTOreceived(){
        //TODO: Muss hier was passieren?
    }
}
