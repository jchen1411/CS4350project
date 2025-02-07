package PomonaTransitSystem;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DisplaySchedule {
    public static void displaySchedule(String startLocationName, String destinationName, String date) {
        String query = "SELECT TripOffering.TripNumber, TripOffering.Date, TripOffering.ScheduledStartTime, TripOffering.ScheduledArrivalTime, " +
                "TripOffering.DriverName, TripOffering.BusID " +
                "FROM TripOffering " +
                "JOIN Trip ON TripOffering.TripNumber = Trip.TripNumber " +
                "WHERE Trip.StartLocationName = ? AND Trip.DestinationName = ? AND TripOffering.Date = ?";

        try (Connection connection = DatabaseConnectivity.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, startLocationName);
            preparedStatement.setString(2, destinationName);
            preparedStatement.setDate(3, Date.valueOf(date));

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                System.out.println("Schedule for trips from " + startLocationName + " to " + destinationName + " on " + date + ":");

                boolean hasResults = false;
                while (resultSet.next()) {
                    hasResults = true;
                    String tripNumber = resultSet.getString("TripNumber");
                    String scheduledStartTime = resultSet.getString("ScheduledStartTime").split("\\.")[0];
                    String scheduledArrivalTime = resultSet.getString("ScheduledArrivalTime").split("\\.")[0];
                    String driverName = resultSet.getString("DriverName");
                    int busID = resultSet.getInt("BusID");

                    System.out.println("TripNumber: " + tripNumber + ", ScheduledStartTime: " + scheduledStartTime +
                            ", ScheduledArrivalTime: " + scheduledArrivalTime + ", DriverName: " + driverName + ", BusID: " + busID);
                }
                if (!hasResults) {
                    System.out.println("No trips found for the given criteria.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
