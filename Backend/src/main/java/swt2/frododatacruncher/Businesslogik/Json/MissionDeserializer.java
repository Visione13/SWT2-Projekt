package swt2.frododatacruncher.Businesslogik.Json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import swt2.frododatacruncher.Businesslogik.Dispatcher;
import swt2.frododatacruncher.Businesslogik.Mission;

import java.io.IOException;
import java.time.LocalDateTime;

public class MissionDeserializer extends JsonDeserializer<Mission> {
  public Mission deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
    JsonNode node = jp.getCodec().readTree(jp);

    String missionId = node.get("missionId").asText();
    String phoneId = node.get("phoneId").asText();
    String destinationId = node.get("roomId").asText();

    return new Mission(missionId, phoneId, LocalDateTime.now(), Dispatcher.getRooms().get(destinationId));
  }
}
