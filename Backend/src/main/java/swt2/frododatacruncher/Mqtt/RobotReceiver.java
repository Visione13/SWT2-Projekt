package swt2.frododatacruncher.Mqtt;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.eclipse.paho.client.mqttv3.*;
import swt2.frododatacruncher.Businesslogik.Dispatcher;
import swt2.frododatacruncher.Businesslogik.Mission;
import swt2.frododatacruncher.Businesslogik.Robot;
import swt2.frododatacruncher.Businesslogik.RobotStates.*;

public class RobotReceiver extends Receiver{

    String clientId;
    String topic;

    public RobotReceiver(Dispatcher dispatcher, String clientId) {
      super(dispatcher);
      this.clientId = clientId;
      topic = "robotDataCruncher";
      try {
        client = new MqttClient(broker, clientId);
        connOpts = new MqttConnectOptions();
        connOpts.setCleanSession(true);
        client.connect();
        client.subscribe(topic);
      } catch (MqttException e) {
        //throw new RuntimeException(e);
        e.printStackTrace();
      }
    }

    @Override
    public void receive() {
      client.setCallback(new MqttCallback() {
        @Override
        public void connectionLost(Throwable cause) {
          System.out.println("Connection lost: " + cause.getMessage());
          cause.printStackTrace();
        }

        @Override
        public void messageArrived(String topic, MqttMessage message) throws Exception {
          String robotDTO = new String(message.getPayload());
//          System.out.println(robotDTO);
          //This tries to build a Robot Object from the DTO and add it to the dispatcher
          convertDTO(robotDTO);
        }

        @Override
        public void deliveryComplete(IMqttDeliveryToken token) {
          System.out.println("Delivery complete");
        }
      });
    }

    @Override
    protected void convertDTO(String robotDTO){
      Robot receivedRobot = null;

      // JSON parsing
      ObjectMapper mapper = new ObjectMapper();
      SimpleModule module = new SimpleModule();
      module.registerSubtypes(RobotIdleState.class, RobotDoneState.class, RobotLoadingState.class, RobotBusyState.class, RobotErrorState.class);
      mapper.registerModule(module);
      try {
        receivedRobot = mapper.readValue(robotDTO, Robot.class);
      }catch(JsonProcessingException e){
        e.printStackTrace();
      }

      // Logic for updating / adding Robot Object
      if(receivedRobot != null){
        //bidirectional association
        receivedRobot.setOwner(dispatcher);
        receivedRobot.getState().setAssociatedRobot(receivedRobot);

        Mission robotMission = receivedRobot.getAssignedMission();
        //We also have to handle updating the mission info, since the mission we're getting doesn't have an assignedRobot in the DTO
        if(robotMission != null) {
          Mission oldMission = dispatcher.getMissions().get(robotMission.getMissionId());
          //Mission already exists in our Dispatcher. Update the info accordingly
          if(oldMission != null) {
            robotMission.setTimestamp(oldMission.getTimestamp());
            //bidirectional association
          }

          //This check is necessary in case a Robot sends a confirmation more than once. We don't want to re-add a mission to our Collection if it's already completed!
          if(!(receivedRobot.getState() instanceof RobotDoneState))
            dispatcher.addOrUpdateMission(robotMission);
        }
        dispatcher.addOrUpdateRobot(receivedRobot);
        if(robotMission != null)
        //Logic to run based on the state the robot is in
        receivedRobot.getState().onDTOreceived();
//        dispatcher.printAllRobots();
      }

    }

}
