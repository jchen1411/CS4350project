package PomonaTransitSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RecordActualTripData {
    public static void recordActualTripData(int tripNumber, int stopNumber, String scheduledArrivalTime, String actualStartTime, String actualArrivalTime, int numberOfPassengerIn, int numberOfPassengerOut) {
        String checkQuery = "SELECT COUNT(*) FROM ActualTripStopInfo WHERE TripNumber = ? AND StopNumber = ? AND ScheduledArrivalTime = ?";
        String insertQuery = "INSERT INTO ActualTripStopInfo (TripNumber, StopNumber, ScheduledArrivalTime, ActualStartTime, ActualArrivalTime, NumberOfPassengerIn, NumberOfPassengerOut) VALUES (?, ?, ?, ?, ?, ?, ?)";
        String updateQuery = "UPDATE ActualTripStopInfo SET ActualStartTime = ?, ActualArrivalTime = ?, NumberOfPassengerIn = ?, NumberOfPassengerOut = ? WHERE TripNumber = ? AND StopNumber = ? AND ScheduledArrivalTime = ?";

        try (Connection connection = DatabaseConnectivity.getConnection();
             PreparedStatement checkStatement = connection.prepareStatement(checkQuery);
             PreparedStatement insertStatement = connection.prepareStatement(insertQuery);
             PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {

            checkStatement.setInt(1, tripNumber);
            checkStatement.setInt(2, stopNumber);
            checkStatement.setTime(3, java.sql.Time.valueOf(scheduledArrivalTime));

            ResultSet resultSet = checkStatement.executeQuery();
            resultSet.next();
            int count = resultSet.getInt(1);

            if (count > 0) {
                // Record exists, update it
                updateStatement.setTime(1, java.sql.Time.valueOf(actualStartTime));
                updateStatement.setTime(2, java.sql.Time.valueOf(actualArrivalTime));
                updateStatement.setInt(3, numberOfPassengerIn);
                updateStatement.setInt(4, numberOfPassengerOut);
                updateStatement.setInt(5, tripNumber);
                updateStatement.setInt(6, stopNumber);
                updateStatement.setTime(7, java.sql.Time.valueOf(scheduledArrivalTime));

                int rowsAffected = updateStatement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Actual trip data updated successfully.");
                } else {
                    System.out.println("Failed to update actual trip data.");
                }
            } else {
                // Record does not exist, insert it
                insertStatement.setInt(1, tripNumber);
                insertStatement.setInt(2, stopNumber);
                insertStatement.setTime(3, java.sql.Time.valueOf(scheduledArrivalTime));
                insertStatement.setTime(4, java.sql.Time.valueOf(actualStartTime));
                insertStatement.setTime(5, java.sql.Time.valueOf(actualArrivalTime));
                insertStatement.setInt(6, numberOfPassengerIn);
                insertStatement.setInt(7, numberOfPassengerOut);

                int rowsAffected = insertStatement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Actual trip data recorded successfully.");
                } else {
                    System.out.println("Failed to record actual trip data.");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
