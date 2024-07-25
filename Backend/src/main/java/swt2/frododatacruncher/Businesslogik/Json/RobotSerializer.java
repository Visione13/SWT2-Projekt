package swt2.frododatacruncher.Businesslogik.Json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import swt2.frododatacruncher.Businesslogik.Robot;

import java.io.IOException;

public class RobotSerializer extends JsonSerializer<Robot> {
  @Override
  public void serialize(Robot robot, JsonGenerator gen, SerializerProvider serializers) throws IOException {
    gen.writeStartObject();
    gen.writeStringField("robotId", robot.getRobotId());
    gen.writeStringField("state", robot.getState().getState());
    gen.writeNumberField("floor", robot.getFloor());
    gen.writeObjectField("mission", robot.getAssignedMission());
    gen.writeEndObject();
  }
}
