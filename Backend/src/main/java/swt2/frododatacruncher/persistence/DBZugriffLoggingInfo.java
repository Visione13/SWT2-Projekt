package swt2.frododatacruncher.persistence;

import jakarta.annotation.PostConstruct;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBZugriffLoggingInfo extends DBZugriff {

    public DBZugriffLoggingInfo(){
    }

    public String read() throws Exception {
        String result = "";
        connect();

        try {
            // Create a statement
            Statement statement = con.createStatement();

            // Execute a query
            String sql = "SELECT * FROM LOGS";
            ResultSet resultSet = statement.executeQuery(sql);

            // Process the result set
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String timestamp = resultSet.getString("timestamp");
                String level = resultSet.getString("log_level");
                String message = resultSet.getString("message");

                result += id + ", " + timestamp + ", " + level + ", " + message +"\n";
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
}
