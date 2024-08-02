package PomonaTransitSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddBus {
    public static void addBus(int busID, String model, int year) {
        String insertQuery = "INSERT INTO Bus (BusID, Model, Year) VALUES (?, ?, ?)";

        try (Connection connection = DatabaseConnectivity.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {

            preparedStatement.setInt(1, busID);
            preparedStatement.setString(2, model);
            preparedStatement.setInt(3, year);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Bus added successfully.");
            } else {
                System.out.println("Failed to add bus.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
