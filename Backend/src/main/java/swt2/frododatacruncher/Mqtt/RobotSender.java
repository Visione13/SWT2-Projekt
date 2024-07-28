package swt2.frododatacruncher.Mqtt;

import org.eclipse.paho.client.mqttv3.*;
import swt2.frododatacruncher.Businesslogik.Mission;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;

public class RobotSender extends Sender {
    private int qos = 1;
    private MqttClient client;
    private MqttConnectOptions connOpts;

    public RobotSender(String robotID) {
        super(robotID);
        topic = "Robot" + robotID;
        try {
            client = new MqttClient(broker, robotID);
            connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);
            client.connect(connOpts);
        } catch (MqttException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void send(String json, String topic) {
        try {
            MqttMessage message = new MqttMessage(json.getBytes());
            message.setQos(qos);
            client.publish(topic, message);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
}
