package PomonaTransitSystem;

import java.sql.*;
import java.time.LocalDate;

public class DisplayDriverWeeklySchedule {
    public static void displayWeeklySchedule(String driverName, String startDate) {
        String query = "SELECT TripOffering.TripNumber, TripOffering.Date, TripOffering.ScheduledStartTime, TripOffering.ScheduledArrivalTime, " +
                "TripOffering.DriverName, TripOffering.BusID " +
                "FROM TripOffering " +
                "WHERE TripOffering.DriverName = ? AND TripOffering.Date BETWEEN ? AND ? " +
                "ORDER BY TripOffering.Date, TripOffering.ScheduledStartTime";

        try (Connection connection = DatabaseConnectivity.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            LocalDate start = LocalDate.parse(startDate);
            LocalDate end = start.plusDays(7);

            preparedStatement.setString(1, driverName);
            preparedStatement.setDate(2, Date.valueOf(start));
            preparedStatement.setDate(3, Date.valueOf(end));

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                System.out.println("Weekly schedule for driver: " + driverName + " from " + startDate + " to " + end + ":");

                boolean hasResults = false;
                while (resultSet.next()) {
                    hasResults = true;
                    int tripNumber = resultSet.getInt("TripNumber");
                    String date = resultSet.getString("Date");
                    String scheduledStartTime = resultSet.getString("ScheduledStartTime").split("\\.")[0];
                    String scheduledArrivalTime = resultSet.getString("ScheduledArrivalTime").split("\\.")[0];
                    int busID = resultSet.getInt("BusID");

                    System.out.println("Date: " + date + ", TripNumber: " + tripNumber + ", ScheduledStartTime: " + scheduledStartTime +
                            ", ScheduledArrivalTime: " + scheduledArrivalTime + ", BusID: " + busID);
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
