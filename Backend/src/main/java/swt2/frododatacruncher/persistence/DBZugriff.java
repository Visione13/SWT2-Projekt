package swt2.frododatacruncher.persistence;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Component
public class DBZugriff {

    protected static Connection con;
    protected static String dbUrl = "jdbc:mysql://db:3306/frodo_logging_db";
    protected static String username = "root";
    protected static String password = "root";
    protected static PreparedStatement befehl;

    public static void connect() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(dbUrl, username, password);
            con.setAutoCommit(true);
        } catch (SQLException ex) {
            ex.printStackTrace();
            String errorMessage = "DB Connection failed. URL: " + dbUrl+ " PW: " + password + "USER: " +username;
            //Logger.logError(ex);
            throw new SQLException(errorMessage);
        } catch (ClassNotFoundException cnfe){
            cnfe.printStackTrace();
        }
    }

    public void close() throws SQLException {
        try {
        	/* Freiabe der Ressourcen des Statements und des ggf. zugehörenden ResultSets
        	   ResultSets werden mit dem Statement geschlossen
        	   Statement ist separat zu schließen, da beim ConnectionPooling sonst das Statement offen bleibt
        	*/
            if (befehl !=null)
                befehl.close();

            if (con != null) {
                con.close();
            }
        } catch (SQLException ex) {
            String errorMessage = "Datenbank connection gefickt. URL: " + this.dbUrl+ " PW: " + this.password + "USER: " +this.username;
            //Logger.logError(ex);
            throw new SQLException(errorMessage, ex);
        }
    }
}
