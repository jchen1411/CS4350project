package PomonaTransitSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DisplayTripStops {
    public static void displayTripStops(int tripNumber) {
        String query = "SELECT TripNumber, StopNumber, SequenceNumber, DrivingTime " +
                       "FROM TripStopInfo WHERE TripNumber = ? ORDER BY SequenceNumber";

        try (Connection connection = DatabaseConnectivity.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, tripNumber);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                System.out.println("Stops for TripNumber: " + tripNumber);

                boolean hasResults = false;
                while (resultSet.next()) {
                    hasResults = true;
                    int stopNumber = resultSet.getInt("StopNumber");
                    int sequenceNumber = resultSet.getInt("SequenceNumber");
                    String drivingTime = resultSet.getString("DrivingTime");

                    System.out.println("StopNumber: " + stopNumber + ", SequenceNumber: " + sequenceNumber +
                            ", DrivingTime: " + drivingTime);
                }
                if (!hasResults) {
                    System.out.println("No stops found for the given trip number.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
