package swt2.frododatacruncher.Businesslogik;

import swt2.frododatacruncher.Mqtt.AckReceiver;
import swt2.frododatacruncher.Mqtt.PhoneSender;
import swt2.frododatacruncher.Mqtt.RobotSender;
import swt2.frododatacruncher.persistence.DBZugriffMissionLogging;
import swt2.frododatacruncher.persistence.DBZugriffRooms;

import java.util.*;

public class Dispatcher {
    private Map<String ,Robot> robots = new HashMap<>();
    private Map<String, Mission> missions = new HashMap<>();
    private static Map<String, Room> rooms = DBZugriffRooms.loadRoomsFromDB();
    private PhoneSender phoneSender;
    private RobotSender robotSender;

    public Dispatcher(){
        this.phoneSender = new PhoneSender("BackendToPhoneSender");
        this.robotSender = new RobotSender("BackendToRobotSender");
    }

    public void addOrUpdateRobot(Robot robot) {
        //Only add the Robot to our Collection if it doesn't exist yet.
        if(!robots.containsKey(robot.getRobotId())) {
            robots.put(robot.getRobotId(), robot);
        }
        else {
            robots.replace(robot.getRobotId(), robot);
        }

    }

    public void addOrUpdateMission(Mission mission) {
        if(mission == null)
            return;
        //Only add the Mission if it doesn't exist yet.
        if(!missions.containsKey(mission.getMissionId()))
            missions.put(mission.getMissionId(), mission);
        else
            missions.replace(mission.getMissionId(), mission);
    }

    public List<Robot> calculateBestRobots(Mission mission){
        List<Robot> liste = new ArrayList<>();
        for (Robot r : robots.values()) {
            if (r.getState().isFree()){
                liste.add(r);
            }
        }

        int destinationFloor = mission.getDestination().getFloor();
        Collections.sort(liste, (o1, o2) -> {
            int distance1 = Math.abs(o1.getFloor() - destinationFloor);
            int distance2 = Math.abs(o2.getFloor() - destinationFloor);
            return Integer.compare(distance1, distance2);
        });
        return liste;
    }

    public void assignMissionToRobot(Mission mission){
        List<Robot> viableRobots = calculateBestRobots(mission);
        System.out.println("Trying to assign mission to Robot.");

        boolean frodoAcceptedMission = false;
        for(Robot robot : viableRobots){
            //Send Mission to Robot client, which is listening to it's own topic.
            AckReceiver ackReceiver = new AckReceiver(new Ack(robot.getRobotId(), false),this, "ackReceiverRobot", robot.getRobotId());
            ackReceiver.receive();
            robotSender.send(mission.missionDTO(), robot.getRobotId());
            synchronized (ackReceiver.ack) {
                try {
                    ackReceiver.ack.wait(1000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            //Falls Frodo Auftrag angenommen hat
            if(ackReceiver.ack.isAccepted()) {
                System.out.println(robot.getRobotId()+" accepted the mission.");
                //Write mission with assigned robot into DB. Currently the mission is not completed yet.
                mission.setAssignedRobot(robot);
//                DBZugriffMissionLogging.writeMission(mission);

                //This is where the Mission is sent to the Robot Client via MQTT and our robot object updates it's status
                robot.executeMission(mission);
//                robotSender.send(mission.missionDTO(), "Robot"+robot.getRobotId());
                //Send confirmation to Phone Client
                phoneSender.send(new MissionState(mission.getMissionId(), "requestAccepted").toDTO(), "Phone" + mission.getPhoneId());
                frodoAcceptedMission = true;
                break;
            }
        }
        if(!frodoAcceptedMission){
            //Send MQTT Response to Phone app to reject the request. There is currently no Robot that can fulfill it.
            phoneSender.send(new MissionState(mission.getMissionId(), "noFrodoFound").toDTO(), mission.getPhoneId());
            missions.remove(mission.getMissionId());
            System.out.println("Mission was rejected");
        }
    }

    public void missionInProgress(Robot robot){
        String log = "Robot with Id: "+robot.getRobotId()+" is busy!";
        if(robot.getAssignedMission() != null)
            log += " Moving to Room " + robot.getAssignedMission().getDestination().getRoomId();
        System.out.println(log);
        //this sends a message to the void if mission was entered directly on robot. (since there is no phoneId)
        phoneSender.send(new MissionState(robot.getAssignedMission().getMissionId(), "robotOnTheWay").toDTO(), robot.getAssignedMission().getPhoneId());
    }

    public void missionWasCompleted(Mission mission){
        //Don't do anything if we receive a "done" state without an existing mission object on the server
        if(!missions.containsKey(mission.getMissionId())){
            return;
        }
        MissionState missionState = new MissionState(mission.getMissionId(), "frodoArrived");

        System.out.println("Sending Response to Phone");
        phoneSender.send(missionState.toDTO(), mission.getPhoneId());
        // Set mission status to completed and remove it from our Map since we won't need it anymore.
        mission.setCompleted(true);
//        DBZugriffMissionLogging.updateMissionCompletedTrue(mission);
        DBZugriffMissionLogging.writeMission(mission);

        mission.getAssignedRobot().setAssignedMission(null);
        mission.setAssignedRobot(null);

        missions.remove(mission.getMissionId());

        System.out.println("Mission success!");
    }

    public void missionFailed(Robot robot){
        //This method is called whenever a robot assigned to a mission enters an error state.
        phoneSender.send(new MissionState(robot.getAssignedMission().getMissionId(), "frodoCrashed").toDTO(), robot.getAssignedMission().getPhoneId());
        DBZugriffMissionLogging.writeMission(robot.getAssignedMission());
        missions.remove(robot.getAssignedMission().getMissionId());

        System.out.println("Mission failed! ):");
    }

    public void printAllRobots(){
        System.out.println("----------------------------------------------");
        System.out.println("All signed in robots:\n");
        System.out.println("                     RobotId   State Floor     Ziel");
        for(Robot robot : robots.values()){
            robot.printInfo();

//            if(this.assignedMission == null)
//                System.out.printf("Robot: %21s%7s%5d", robotId, state, floor);
//            else
//                System.out.printf("Robot: %21s%7s%5d Mission: %5s", robotId, state, floor, this.assignedMission.destinationToString());
        }
        System.out.println("----------------------------------------------");
    }

//    public void printAllMissions(){
//        for(Mission mission : missions.values()){
//            System.out.println(mission);
//        }
//    }

    public static Map<String, Room> getRooms() {
        return rooms;
    }

    public Map<String, Mission> getMissions() {
        return missions;
    }
}
