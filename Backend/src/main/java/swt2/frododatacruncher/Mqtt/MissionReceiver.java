package swt2.frododatacruncher.Mqtt;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.eclipse.paho.client.mqttv3.*;
import swt2.frododatacruncher.Businesslogik.Dispatcher;
import swt2.frododatacruncher.Businesslogik.Mission;

public class MissionReceiver extends Receiver{

    String clientId;
    String topic;

    public MissionReceiver(Dispatcher dispatcher, String clientId) {
        super(dispatcher);
        this.clientId = clientId;
        topic = "missionDataCruncher";
        try {
            client = new MqttClient(broker, clientId);
            connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);
            client.connect();
            client.subscribe(topic);
        } catch (MqttException e) {
            throw new RuntimeException(e);
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
                String missionDTO = new String(message.getPayload());
                System.out.println(missionDTO);
                //This tries to build a Mission Object from the DTO and add it to the dispatcher
                convertDTO(missionDTO);
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {
                System.out.println("Delivery complete");
            }
        });
    }

    @Override
    protected void convertDTO(String missionDTO){
        // Example DTO: {"destination": null, "floor": 0, "state": "idle", "robotId": "Robot7115360b7f522c69"}
        Mission receivedMission = null;
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        mapper.registerModule(module);
        try {
            receivedMission = mapper.readValue(missionDTO, Mission.class);
            //If Mission already exists, just return that
//            if(dispatcher.getMissions().containsKey(receivedMission.getMissionId()))
//                receivedMission = dispatcher.getMissions().get(receivedMission.getMissionId());
            Mission oldMission = dispatcher.getMissions().get(receivedMission.getMissionId());
            //Mission already exists, just make sure to set assigned Robot again and use the old Timestamp
            if(oldMission != null){
                receivedMission.setAssignedRobot(oldMission.getAssignedRobot());
                receivedMission.setTimestamp(oldMission.getTimestamp());
            } else {
                //Mission is new. Add it to the Dispatcher and try to assign it to a robot
                dispatcher.addOrUpdateMission(receivedMission);
                dispatcher.assignMissionToRobot(receivedMission);
            }
        }catch(JsonProcessingException e){
            //TODO Exception handling
            e.printStackTrace();
        }


    }


}
