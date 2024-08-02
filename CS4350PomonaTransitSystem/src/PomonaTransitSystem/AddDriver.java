package PomonaTransitSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddDriver {
    public static void addDriver(String driverName, String driverTelephone) {
        String insertQuery = "INSERT INTO Driver (DriverName, DriverTelephone) VALUES (?, ?)";

        try (Connection connection = DatabaseConnectivity.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {

            preparedStatement.setString(1, driverName);
            preparedStatement.setString(2, driverTelephone);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Driver added successfully.");
            } else {
                System.out.println("Failed to add driver.");
            }

        } catch (SQLException e) {
            if (e.getMessage().contains("unsupported collating sort order")) {
                System.out.println("Failed to add driver due to unsupported collating sort order. Please check the database collation settings.");
            } else {
                e.printStackTrace();
            }
        }
    }
}
