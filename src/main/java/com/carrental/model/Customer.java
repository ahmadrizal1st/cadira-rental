package main.java.com.carrental.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Customer {
    private int customerId;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;

    public Customer(int customerId, String firstName, String lastName, String email, String phoneNumber) {
        this.customerId = customerId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    // Getters
    public int getCustomerId() { return customerId; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getEmail() { return email; }
    public String getPhoneNumber() { return phoneNumber; }

    // Setters
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public void setEmail(String email) { this.email = email; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    // CRUD operations
    public static void addCustomer(Customer customer) throws SQLException {
        String sql = "INSERT INTO customers (first_name, last_name, email, phone_number) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, customer.getFirstName());
            stmt.setString(2, customer.getLastName());
            stmt.setString(3, customer.getEmail());
            stmt.setString(4, customer.getPhoneNumber());
            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    customer.customerId = generatedKeys.getInt(1);
                }
            }
        }
    }

    public static List<Customer> getAllCustomers() throws SQLException {
        List<Customer> customers = new ArrayList<>();
        String sql = "SELECT * FROM customers";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                customers.add(new Customer(
                    rs.getInt("customer_id"),
                    rs.getString("first_name"),
                    rs.getString("last_name"),
                    rs.getString("email"),
                    rs.getString("phone_number")
                ));
            }
        }
        return customers;
    }

    public static void updateCustomer(Customer customer) throws SQLException {
        String sql = "UPDATE customers SET first_name = ?, last_name = ?, email = ?, phone_number = ? WHERE customer_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, customer.getFirstName());
            stmt.setString(2, customer.getLastName());
            stmt.setString(3, customer.getEmail());
            stmt.setString(4, customer.getPhoneNumber());
            stmt.setInt(5, customer.getCustomerId());
            stmt.executeUpdate();
        }
    }

    public static void deleteCustomer(int customerId) throws SQLException {
        String sql = "DELETE FROM customers WHERE customer_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, customerId);
            stmt.executeUpdate();
        }
    }
}


