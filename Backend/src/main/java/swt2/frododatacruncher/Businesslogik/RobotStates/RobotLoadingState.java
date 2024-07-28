package swt2.frododatacruncher.Businesslogik.RobotStates;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("loading")
public class RobotLoadingState extends RobotState{

    public RobotLoadingState(){
        super();
        this.state = "loading";
    }

    @JsonIgnore
    @Override
    public boolean isFree() {
        return false;
    }

    public void onDTOreceived(){
        //TODO: Any logic necessary?
    }
}
