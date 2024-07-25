package swt2.frododatacruncher.Businesslogik.Json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import swt2.frododatacruncher.Businesslogik.Mission;
import swt2.frododatacruncher.Businesslogik.Robot;
import swt2.frododatacruncher.Businesslogik.RobotStates.RobotState;

import java.io.IOException;

public class RobotDeserializer extends JsonDeserializer<Robot> {
  @Override
  public Robot deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
    JsonNode node = p.getCodec().readTree(p);
    Robot robot = new Robot();

    robot.setRobotId(node.get("robotId").asText());
    robot.setState(RobotState.create(node.get("state").asText()));
    robot.setFloor(node.get("floor").asInt());
    if (node.has("mission") && !node.get("mission").isNull()) {
      Mission mission = p.getCodec().treeToValue(node.get("mission"), Mission.class);
      robot.setAssignedMission(mission);
      mission.setAssignedRobot(robot);
    }
    return robot;
  }
}
