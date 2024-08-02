package PomonaTransitSystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnectivity {
    private static final String DATABASE_URL = "jdbc:ucanaccess://./resources/pomonaTransitSystem.accdb";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DATABASE_URL);
    }

    public static boolean checkConnection() {
        try (Connection connection = getConnection()) {
            return connection != null;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
