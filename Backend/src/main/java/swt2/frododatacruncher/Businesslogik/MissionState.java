package swt2.frododatacruncher.Businesslogik;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MissionState {

  String missionId;
  String state;

  public MissionState(String missionId, String state) {
    this.missionId = missionId;
    this.state = state;
  }

  public String getMissionId() {
    return missionId;
  }

  public String getState() {
    return state;
  }

  public String toDTO(){
    String result = "";
    ObjectMapper mapper = new ObjectMapper();
    try {
      result = mapper.writeValueAsString(this);
    }catch(JsonProcessingException e){
      e.printStackTrace();
    }
    return result;
  }

}
