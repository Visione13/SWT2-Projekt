package swt2.frododatacruncher.Mqtt;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import swt2.frododatacruncher.Businesslogik.Mission;
import swt2.frododatacruncher.Businesslogik.Robot;

public abstract class Sender {
    protected String broker = "SERVERADRESSSE";
    protected String clientId;
    protected String topic;

    public Sender(String clientId) {
        this.clientId = clientId;
    }

    public abstract void send(String json, String topic);
}
