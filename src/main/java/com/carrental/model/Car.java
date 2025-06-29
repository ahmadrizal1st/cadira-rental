package main.java.com.carrental.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Car {
    private int carId;
    private String make;
    private String model;
    private int year;
    private String licensePlate;
    private double hourlyRate;
    private boolean available;

    public Car(int carId, String make, String model, int year, String licensePlate, double hourlyRate, boolean available) {
        this.carId = carId;
        this.make = make;
        this.model = model;
        this.year = year;
        this.licensePlate = licensePlate;
        this.hourlyRate = hourlyRate;
        this.available = available;
    }

    // Getters
    public int getCarId() { return carId; }
    public String getMake() { return make; }
    public String getModel() { return model; }
    public int getYear() { return year; }
    public String getLicensePlate() { return licensePlate; }
    public double getHourlyRate() { return hourlyRate; }
    public boolean isAvailable() { return available; }

    // Setters
    public void setMake(String make) { this.make = make; }
    public void setModel(String model) { this.model = model; }
    public void setYear(int year) { this.year = year; }
    public void setLicensePlate(String licensePlate) { this.licensePlate = licensePlate; }
    public void setHourlyRate(double hourlyRate) { this.hourlyRate = hourlyRate; }
    public void setAvailable(boolean available) { this.available = available; }

    // CRUD operations
    public static void addCar(Car car) throws SQLException {
        String sql = "INSERT INTO cars (make, model, year, license_plate, hourly_rate, available) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, car.getMake());
            stmt.setString(2, car.getModel());
            stmt.setInt(3, car.getYear());
            stmt.setString(4, car.getLicensePlate());
            stmt.setDouble(5, car.getHourlyRate());
            stmt.setBoolean(6, car.isAvailable());
            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    car.carId = generatedKeys.getInt(1);
                }
            }
        }
    }

    public static List<Car> getAllCars() throws SQLException {
        List<Car> cars = new ArrayList<>();
        String sql = "SELECT * FROM cars";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
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

    public static void updateCar(Car car) throws SQLException {
        String sql = "UPDATE cars SET make = ?, model = ?, year = ?, license_plate = ?, hourly_rate = ?, available = ? WHERE car_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
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

    public static void deleteCar(int carId) throws SQLException {
        String sql = "DELETE FROM cars WHERE car_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, carId);
            stmt.executeUpdate();
        }
    }
}


