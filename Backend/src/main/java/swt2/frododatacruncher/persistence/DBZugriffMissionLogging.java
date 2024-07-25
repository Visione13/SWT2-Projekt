package swt2.frododatacruncher.persistence;

import swt2.frododatacruncher.Businesslogik.Mission;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBZugriffMissionLogging extends DBZugriff{

  /*
  This class handles any relevant Read, Write and Update use-cases we might run into for Mission Objects
   */

  public static String readMissions() {
    String result = "";

    try {
      connect();
      // Create a statement
      Statement statement = con.createStatement();

      // Execute a query
      String sql = "SELECT * FROM missions";
      ResultSet resultSet = statement.executeQuery(sql);

      // Process the result set
      while (resultSet.next()) {
        String missionId = resultSet.getString("missionId");
        String timestamp = resultSet.getString("timestamp");
        String destination = resultSet.getString("destination");
        String assignedRobot = resultSet.getString("assignedRobot");
        String isCompleted = resultSet.getString("isCompleted");

        result += missionId + ", " + timestamp + ", " + destination + ", " + assignedRobot + ", " + isCompleted +"\n";
      }

      // Close the result set, statement, and connection
      resultSet.close();
      statement.close();
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      try {
        if (con != null) {
          con.close();
        }
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
    return result;
  }

  public static String readMission(String missionId) {
    String result = "";

    try{
      connect();

      Statement statement = con.createStatement();
      String sql = "SELECT * FROM missions WHERE missionId = '" + missionId + "'";
      ResultSet resultSet = statement.executeQuery(sql);
      while (resultSet.next()) {
        result = resultSet.getString("missionId");
        result += resultSet.getString("timestamp");
        result += resultSet.getString("destination");
        result += resultSet.getString("assignedRobot");
        result += resultSet.getString("isCompleted");
      }
    }catch (SQLException e){
      e.printStackTrace();
    }
    return result;
  }

  public static boolean writeMission(Mission mission){
    //If a mission with that ID has already been logged, we probably got a redundant RobotDTO and can just ignore it
    if(!readMission(mission.getMissionId()).isEmpty()){
      return false;
    }
    try {
      connect();

      // Create a statement
      Statement statement = con.createStatement();

      // Execute a query
      String sql = "";
      if(mission.getAssignedRobot() != null)
        sql = "INSERT INTO missions (missionId, timestamp, destination, assignedRobot, isCompleted) VALUES ('"+mission.getMissionId()+"', '"+mission.getTimestamp()+"', '"+mission.getDestination().getRoomId()+"', '"+mission.getAssignedRobot().getRobotId()+"', "+mission.isCompleted()+");";
      else
        sql = "INSERT INTO missions (missionId, timestamp, destination, assignedRobot, isCompleted) VALUES ('"+mission.getMissionId()+"', '"+mission.getTimestamp()+"', '"+mission.getDestination().getRoomId()+"', "+null+", "+mission.isCompleted()+");";
      statement.executeUpdate(sql);
      return true;
    }catch(SQLException e){
      //TODO Exception handling
      e.printStackTrace();
      return false;
    }
  }
}
