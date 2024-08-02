package PomonaTransitSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteBus {
    public static void deleteBus(int busID) {
        String deleteQuery = "DELETE FROM Bus WHERE BusID = ?";

        try (Connection connection = DatabaseConnectivity.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {

            preparedStatement.setInt(1, busID);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Bus deleted successfully.");
            } else {
                System.out.println("No matching bus found.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
