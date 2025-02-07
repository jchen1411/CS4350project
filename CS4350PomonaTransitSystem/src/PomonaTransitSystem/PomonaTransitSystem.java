package PomonaTransitSystem;

import java.util.Scanner;

public class PomonaTransitSystem {
    public static void main(String[] args) {
        System.out.println("Connecting to MS Access database...");

        // Check connectivity
        if (DatabaseConnectivity.checkConnection()) {
            System.out.println("Connection successful.");
            
            ScheduleEditor.displaySchedule("LA", "NY", "2024-08-02"); // correct format "YYYY-MM-DD"
            System.out.println(); 
            
            Scanner scanner = new Scanner(System.in);
            ScheduleEditor.TripOfferingEditor editor = new ScheduleEditor.TripOfferingEditor();
            while (true) {
            	System.out.println(); 
                System.out.println("Schedule Editor - Choose an option:");
                System.out.println("1. Delete a trip offering");
                System.out.println("2. Add new trip offering");
                System.out.println("3. Change driver for a trip offering");
                System.out.println("4. Change bus for a trip offering");
                System.out.println("5. Display stops for a trip");
                System.out.println("6. Display weekly schedule for a driver");
                System.out.println("7. Add a new driver");
                System.out.println("8. Add a new bus");
                System.out.println("9. Delete a bus");
                System.out.println("10. Record actual trip data");
                System.out.println("11. Exit");
                System.out.print("Enter your choice: ");
                int choice = scanner.nextInt();
                scanner.nextLine();  
                switch (choice) {
                    case 1:
                        System.out.println("Enter TripNumber to delete:");
                        int tripNumberToDelete = scanner.nextInt();
                        scanner.nextLine(); // consume newline
                        System.out.println("Enter Date to delete (YYYY-MM-DD):");
                        String dateToDelete = scanner.nextLine();
                        System.out.println("Enter ScheduledStartTime to delete (HH:MM:SS):");
                        String startTimeToDelete = scanner.nextLine();
                        editor.deleteTripOffering(tripNumberToDelete, dateToDelete, startTimeToDelete);
                        break;
                    case 2:
                        editor.addTripOfferings();
                        break;
                    case 3:
                        System.out.println("Enter TripNumber to change driver:");
                        int tripNumberToChangeDriver = scanner.nextInt();
                        scanner.nextLine(); // consume newline
                        System.out.println("Enter Date to change driver (YYYY-MM-DD):");
                        String dateToChangeDriver = scanner.nextLine();
                        System.out.println("Enter ScheduledStartTime to change driver (HH:MM:SS):");
                        String startTimeToChangeDriver = scanner.nextLine();
                        System.out.println("Enter new DriverName:");
                        String newDriverName = scanner.nextLine();
                        editor.changeDriver(tripNumberToChangeDriver, dateToChangeDriver, startTimeToChangeDriver, newDriverName);
                        break;
                    case 4:
                        System.out.println("Enter TripNumber to change bus:");
                        int tripNumberToChangeBus = scanner.nextInt();
                        scanner.nextLine(); // consume newline
                        System.out.println("Enter Date to change bus (YYYY-MM-DD):");
                        String dateToChangeBus = scanner.nextLine();
                        System.out.println("Enter ScheduledStartTime to change bus (HH:MM:SS):");
                        String startTimeToChangeBus = scanner.nextLine();
                        System.out.println("Enter new BusID:");
                        int newBusID = scanner.nextInt();
                        scanner.nextLine();  
                        editor.changeBus(tripNumberToChangeBus, dateToChangeBus, startTimeToChangeBus, newBusID);
                        break;
                    case 5:
                        System.out.println("Enter TripNumber to display stops:");
                        int tripNumberToDisplayStops = scanner.nextInt();
                        scanner.nextLine(); // consume newline
                        DisplayTripStops.displayTripStops(tripNumberToDisplayStops);
                        break;
                    case 6:
                        System.out.println("Enter DriverName:");
                        String driverName = scanner.nextLine();
                        System.out.println("Enter Start Date (YYYY-MM-DD):");
                        String startDate = scanner.nextLine();
                        DisplayDriverWeeklySchedule.displayWeeklySchedule(driverName, startDate);
                        break;
                    case 7:
                    	System.out.println("Enter DriverName:");
                        String nDriverName = scanner.nextLine();
                        System.out.println("Enter DriverTelephone:");
                        String driverTelephone = scanner.nextLine();
                        AddDriver.addDriver(nDriverName, driverTelephone);
                        break;
                    case 8:
                        System.out.println("Enter BusID:");
                        int busID = scanner.nextInt();
                        scanner.nextLine(); // consume newline
                        System.out.println("Enter Model:");
                        String model = scanner.nextLine();
                        System.out.println("Enter Year:");
                        int year = scanner.nextInt();
                        scanner.nextLine(); // consume newline
                        AddBus.addBus(busID, model, year);
                        break;
                    case 9:
                        System.out.println("Enter BusID to delete:");
                        int busIDToDelete = scanner.nextInt();
                        scanner.nextLine(); // consume newline
                        DeleteBus.deleteBus(busIDToDelete);
                        break;
                    case 10:
                        System.out.println("Enter TripNumber:");
                        int tripNumber = scanner.nextInt();
                        scanner.nextLine(); // consume newline
                        System.out.println("Enter StopNumber:");
                        int stopNumber = scanner.nextInt();
                        scanner.nextLine(); // consume newline
                        System.out.println("Enter ScheduledArrivalTime (HH:MM:SS):");
                        String scheduledArrivalTime = scanner.nextLine();
                        System.out.println("Enter ActualStartTime (HH:MM:SS):");
                        String actualStartTime = scanner.nextLine();
                        System.out.println("Enter ActualArrivalTime (HH:MM:SS):");
                        String actualArrivalTime = scanner.nextLine();
                        System.out.println("Enter NumberOfPassengersIn:");
                        int numberOfPassengerIn = scanner.nextInt();
                        System.out.println("Enter NumberOfPassengersOut:");
                        int numberOfPassengerOut = scanner.nextInt();
                        scanner.nextLine(); // consume newline
                        RecordActualTripData.recordActualTripData(tripNumber, stopNumber, scheduledArrivalTime, actualStartTime, actualArrivalTime, numberOfPassengerIn, numberOfPassengerOut);
                        break;
                    case 11:
                        System.out.println("Exiting...");
                        scanner.close();
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }
            
        } else {
            System.out.println("Failed to connect to the database.");
        }
    }
}
