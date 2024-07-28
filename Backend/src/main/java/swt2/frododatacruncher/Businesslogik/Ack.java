package swt2.frododatacruncher.Businesslogik;

import com.fasterxml.jackson.annotation.JsonAlias;

public class Ack {

  @JsonAlias("robotId")
  private String deviceId;
  private boolean accepted;

  public Ack(){
  }

  public Ack(String deviceId, boolean isAccepted) {
    this.deviceId = deviceId;
    this.accepted = isAccepted;
  }

  public String getDeviceId() {
    return deviceId;
  }

  public boolean isAccepted() {
    return accepted;
  }

  public void setAccepted(boolean accepted) {
    this.accepted = accepted;
  }

  public void setDeviceId(String deviceId) {
    this.deviceId = deviceId;
  }

  public String toString(){
    return deviceId + " : " + accepted;
  }
}
