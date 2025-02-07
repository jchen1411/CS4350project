package PomonaTransitSystem;

import java.sql.*;
import java.util.Scanner;

public class ScheduleEditor {
    private static final String DATABASE_URL = "jdbc:ucanaccess://./resources/pomonaTransitSystem.accdb";
    
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
                    int tripNumber = resultSet.getInt("TripNumber");
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

    public static class TripOfferingEditor {

        public void deleteTripOffering(int tripNumber, String date, String scheduledStartTime) {
            String deleteQuery = "DELETE FROM TripOffering WHERE TripNumber = ? AND Date = ? AND ScheduledStartTime = ?";

            try (Connection connection = DatabaseConnectivity.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {

                preparedStatement.setInt(1, tripNumber);
                preparedStatement.setDate(2, Date.valueOf(date));
                preparedStatement.setTime(3, Time.valueOf(scheduledStartTime));

                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Trip offering deleted successfully.");
                } else {
                    System.out.println("No matching trip offering found.");
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        public void addTripOfferings() {
            try (Scanner scanner = new Scanner(System.in)) {
                boolean moreTrips = true;

                while (moreTrips) {
                    System.out.println("Enter TripNumber:");
                    int tripNumber = scanner.nextInt();
                    scanner.nextLine(); // consume newline
                    System.out.println("Enter Date (YYYY-MM-DD):");
                    String date = scanner.nextLine();
                    System.out.println("Enter ScheduledStartTime (HH:MM:SS):");
                    String scheduledStartTime = scanner.nextLine();
                    System.out.println("Enter ScheduledArrivalTime (HH:MM:SS):");
                    String scheduledArrivalTime = scanner.nextLine();
                    System.out.println("Enter DriverName:");
                    String driverName = scanner.nextLine();
                    System.out.println("Enter BusID:");
                    int busID = scanner.nextInt();
                    scanner.nextLine(); 

                    String insertQuery = "INSERT INTO TripOffering (TripNumber, Date, ScheduledStartTime, ScheduledArrivalTime, DriverName, BusID) " +
                            "VALUES (?, ?, ?, ?, ?, ?)";

                    try (Connection connection = DatabaseConnectivity.getConnection();
                         PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {

                        preparedStatement.setInt(1, tripNumber);
                        preparedStatement.setDate(2, Date.valueOf(date));
                        preparedStatement.setTime(3, Time.valueOf(scheduledStartTime));
                        preparedStatement.setTime(4, Time.valueOf(scheduledArrivalTime));
                        preparedStatement.setString(5, driverName);
                        preparedStatement.setInt(6, busID);

                        int rowsAffected = preparedStatement.executeUpdate();
                        if (rowsAffected > 0) {
                            System.out.println("Trip offering added successfully.");
                        } else {
                            System.out.println("Failed to add trip offering.");
                        }

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                    System.out.println("Do you want to add more trips? (yes/no):");
                    String response = scanner.nextLine();
                    moreTrips = response.equalsIgnoreCase("yes");
                }
            }
        }

        public void changeDriver(int tripNumber, String date, String scheduledStartTime, String newDriverName) {
            String updateQuery = "UPDATE TripOffering SET DriverName = ? WHERE TripNumber = ? AND Date = ? AND ScheduledStartTime = ?";

            try (Connection connection = DatabaseConnectivity.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {

                preparedStatement.setString(1, newDriverName);
                preparedStatement.setInt(2, tripNumber);
                preparedStatement.setDate(3, Date.valueOf(date));
                preparedStatement.setTime(4, Time.valueOf(scheduledStartTime));

                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Driver changed successfully.");
                } else {
                    System.out.println("No matching trip offering found.");
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        public void changeBus(int tripNumber, String date, String scheduledStartTime, int newBusID) {
            String updateQuery = "UPDATE TripOffering SET BusID = ? WHERE TripNumber = ? AND Date = ? AND ScheduledStartTime = ?";

            try (Connection connection = DatabaseConnectivity.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {

                preparedStatement.setInt(1, newBusID);
                preparedStatement.setInt(2, tripNumber);
                preparedStatement.setDate(3, Date.valueOf(date));
                preparedStatement.setTime(4, Time.valueOf(scheduledStartTime));

                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Bus changed successfully.");
                } else {
                    System.out.println("No matching trip offering found.");
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
