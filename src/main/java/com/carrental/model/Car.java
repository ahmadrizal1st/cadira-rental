package main.java.com.carrental.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a car entity in the car rental system.
 * This class models the car attributes and provides CRUD operations
 * for database interaction.
 */
public class Car {
    private int carId;
    private String make;
    private String model;
    private int year;
    private String licensePlate;
    private double hourlyRate;
    private boolean available;

    /**
     * Constructs a new Car instance.
     * 
     * @param carId The unique identifier for the car
     * @param make The manufacturer of the car
     * @param model The model of the car
     * @param year The manufacturing year of the car
     * @param licensePlate The license plate number
     * @param hourlyRate The rental rate per hour
     * @param available Availability status of the car
     */
    public Car(int carId, String make, String model, int year, 
               String licensePlate, double hourlyRate, boolean available) {
        this.carId = carId;
        this.make = make;
        this.model = model;
        this.year = year;
        this.licensePlate = licensePlate;
        this.hourlyRate = hourlyRate;
        this.available = available;
    }

    // Getters
    /** @return The car's unique identifier */
    public int getCarId() { return carId; }
    
    /** @return The manufacturer of the car */
    public String getMake() { return make; }
    
    /** @return The model of the car */
    public String getModel() { return model; }
    
    /** @return The manufacturing year of the car */
    public int getYear() { return year; }
    
    /** @return The license plate number */
    public String getLicensePlate() { return licensePlate; }
    
    /** @return The hourly rental rate */
    public double getHourlyRate() { return hourlyRate; }
    
    /** @return The availability status of the car */
    public boolean isAvailable() { return available; }

    // Setters
    /** @param make The manufacturer to set */
    public void setMake(String make) { this.make = make; }
    
    /** @param model The model to set */
    public void setModel(String model) { this.model = model; }
    
    /** @param year The manufacturing year to set */
    public void setYear(int year) { this.year = year; }
    
    /** @param licensePlate The license plate number to set */
    public void setLicensePlate(String licensePlate) { this.licensePlate = licensePlate; }
    
    /** @param hourlyRate The hourly rental rate to set */
    public void setHourlyRate(double hourlyRate) { this.hourlyRate = hourlyRate; }
    
    /** @param available The availability status to set */
    public void setAvailable(boolean available) { this.available = available; }

    // CRUD operations
    
    /**
     * Adds a new car to the database.
     * 
     * @param car The car object to be added
     * @throws SQLException If a database access error occurs
     */
    public static void addCar(Car car) throws SQLException {
        String sql = "INSERT INTO cars (make, model, year, license_plate, hourly_rate, available) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            // Set parameters for the prepared statement
            stmt.setString(1, car.getMake());
            stmt.setString(2, car.getModel());
            stmt.setInt(3, car.getYear());
            stmt.setString(4, car.getLicensePlate());
            stmt.setDouble(5, car.getHourlyRate());
            stmt.setBoolean(6, car.isAvailable());
            stmt.executeUpdate();

            // Retrieve the auto-generated car ID
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    car.carId = generatedKeys.getInt(1);
                }
            }
        }
    }

    /**
     * Retrieves all cars from the database.
     * 
     * @return A list of all cars
     * @throws SQLException If a database access error occurs
     */
    public static List<Car> getAllCars() throws SQLException {
        List<Car> cars = new ArrayList<>();
        String sql = "SELECT * FROM cars";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            // Process each row in the result set
            while (rs.next()) {
                cars.add(new Car(
                    rs.getInt("car_id"),
                    rs.getString("make"),
                    rs.getString("model"),
                    rs.getInt("year"),
                    rs.getString("license_plate"),
                    rs.getDouble("hourly_rate"),
                    rs.getBoolean("available")
                ));
            }
        }
        return cars;
    }

    /**
     * Updates an existing car in the database.
     * 
     * @param car The car object with updated information
     * @throws SQLException If a database access error occurs
     */
    public static void updateCar(Car car) throws SQLException {
        String sql = "UPDATE cars SET make = ?, model = ?, year = ?, license_plate = ?, hourly_rate = ?, available = ? WHERE car_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            // Set parameters for the prepared statement
            stmt.setString(1, car.getMake());
            stmt.setString(2, car.getModel());
            stmt.setInt(3, car.getYear());
            stmt.setString(4, car.getLicensePlate());
            stmt.setDouble(5, car.getHourlyRate());
            stmt.setBoolean(6, car.isAvailable());
            stmt.setInt(7, car.getCarId());
            
            stmt.executeUpdate();
        }
    }

    /**
     * Deletes a car from the database.
     * 
     * @param carId The ID of the car to be deleted
     * @throws SQLException If a database access error occurs
     */
    public static void deleteCar(int carId) throws SQLException {
        String sql = "DELETE FROM cars WHERE car_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, carId);
            stmt.executeUpdate();
        }
    }
}