package swt2.frododatacruncher;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import swt2.frododatacruncher.Businesslogik.*;
import swt2.frododatacruncher.Mqtt.MissionReceiver;
import swt2.frododatacruncher.Mqtt.RobotReceiver;

@SpringBootApplication
public class FrodoDataCruncherApplication {

    public static void main(String[] args) {
      SpringApplication.run(FrodoDataCruncherApplication.class, args);
      Dispatcher dispatcher = new Dispatcher();

      RobotReceiver robotReceiver = new RobotReceiver(dispatcher, "robotDataCruncherReceiver");
      MissionReceiver missionReceiver = new MissionReceiver(dispatcher, "missionDataCruncherReceiver");

      robotReceiver.receive();
      missionReceiver.receive();

      Thread robotPrinter = new Thread(() -> {
        while(true){
          try {
            Thread.sleep(10000);
            dispatcher.printAllRobots();
          } catch (InterruptedException e) {
            System.out.println("Sleep interrupted");
          }
        }
      });
      robotPrinter.setDaemon(true);
      robotPrinter.start();
    }
}
