package swt2.frododatacruncher.Mqtt;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.eclipse.paho.client.mqttv3.*;
import swt2.frododatacruncher.Businesslogik.Dispatcher;
import swt2.frododatacruncher.Businesslogik.Ack;

public class AckReceiver extends Receiver{

  private String clientId;
  private String robotId;
  private String topic;
  public Ack ack;

  public AckReceiver(Ack ack, Dispatcher dispatcher, String clientId, String robotId) {
    super(dispatcher);
    this.ack = ack;
    this.clientId = clientId;
    this.robotId = robotId;
    this.topic = "Ack"+robotId;
    try {
      client = new MqttClient(broker, clientId);
      connOpts = new MqttConnectOptions();
      connOpts.setCleanSession(true);
      client.connect();
      client.subscribe(topic);
    } catch (MqttException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void receive(){

    client.setCallback(new MqttCallback() {
      public void connectionLost(Throwable cause) {
      }

      public void deliveryComplete(IMqttDeliveryToken token) {
        System.out.println("Delivery complete");
      }

      public void messageArrived(String topic, MqttMessage message) throws Exception {
        String ackDTO = new String(message.getPayload());
        convertDTO(ackDTO);
        ack.notifyAll();
      }
    });
  }

  @Override
  protected void convertDTO(String ackDTO){
    Ack receivedAck = null;
    ObjectMapper mapper = new ObjectMapper();
    try {
      receivedAck = mapper.readValue(ackDTO, Ack.class);
    }catch(JsonProcessingException e){
      //TODO Exception handling
      e.printStackTrace();
    }
    this.ack = receivedAck;
  }

  public Ack getAck() {
    return ack;
  }
}
