package swt2.frododatacruncher.persistence;

import swt2.frododatacruncher.Businesslogik.Room;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class DBZugriffRooms extends DBZugriff{

  public static Map<String, Room> loadRoomsFromDB(){
    Map<String, Room> rooms = new HashMap<String, Room>();
    try{
      connect();
      Statement statement = con.createStatement();
      String sql = "SELECT * FROM rooms";

      ResultSet resultSet = statement.executeQuery(sql);

      while(resultSet.next()){
        String roomId = resultSet.getString("roomId");
        String description = resultSet.getString("description");
        String floor = resultSet.getString("floor");

        try{
          rooms.put(roomId, new Room(roomId, description, Integer.parseInt(floor)));
        }catch(NumberFormatException e){
          e.printStackTrace();
        }
      }

    }catch(SQLException e){
      e.printStackTrace();
    }
    return rooms;
  }
}
