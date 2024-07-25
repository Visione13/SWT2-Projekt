package swt2.frododatacruncher.Mqtt;

import org.eclipse.paho.client.mqttv3.*;
import swt2.frododatacruncher.Businesslogik.Robot;

public class PhoneSender extends Sender{
    private int qos = 1;
    private MqttClient client;
    private MqttConnectOptions connOpts;

    public PhoneSender(String clientID) {
      super(clientID);
      topic = "Phone" + clientID;
      try {
        client = new MqttClient(broker, clientId);
        connOpts = new MqttConnectOptions();
        connOpts.setCleanSession(true);
        client.connect(connOpts);
      } catch (MqttException e) {
        throw new RuntimeException(e);
      }
    }

    @Override
    public void send(String json, String topic){
      try{
        MqttMessage message = new MqttMessage(json.getBytes());
        message.setQos(qos);
        client.publish(topic, message);
      }catch(MqttException e){
        e.printStackTrace();
      }
    }
}
