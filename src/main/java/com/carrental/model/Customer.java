package main.java.com.carrental.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a customer entity in the car rental system.
 * This class models customer information and provides database CRUD operations
 * for customer management.
 */
public class Customer {
    private int customerId;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;

    /**
     * Constructs a new Customer instance.
     * 
     * @param customerId The unique identifier for the customer
     * @param firstName The customer's first name
     * @param lastName The customer's last name
     * @param email The customer's email address
     * @param phoneNumber The customer's phone number
     */
    public Customer(int customerId, String firstName, String lastName, 
                   String email, String phoneNumber) {
        this.customerId = customerId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    // Getters
    /** @return The customer's unique identifier */
    public int getCustomerId() { return customerId; }
    
    /** @return The customer's first name */
    public String getFirstName() { return firstName; }
    
    /** @return The customer's last name */
    public String getLastName() { return lastName; }
    
    /** @return The customer's email address */
    public String getEmail() { return email; }
    
    /** @return The customer's phone number */
    public String getPhoneNumber() { return phoneNumber; }

    // Setters
    /** @param firstName The first name to set */
    public void setFirstName(String firstName) { this.firstName = firstName; }
    
    /** @param lastName The last name to set */
    public void setLastName(String lastName) { this.lastName = lastName; }
    
    /** @param email The email address to set */
    public void setEmail(String email) { this.email = email; }
    
    /** @param phoneNumber The phone number to set */
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    // CRUD operations
    
    /**
     * Adds a new customer to the database.
     * 
     * @param customer The customer object to be added
     * @throws SQLException If a database access error occurs
     */
    public static void addCustomer(Customer customer) throws SQLException {
        String sql = "INSERT INTO customers (first_name, last_name, email, phone_number) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            // Set parameters for the prepared statement
            stmt.setString(1, customer.getFirstName());
            stmt.setString(2, customer.getLastName());
            stmt.setString(3, customer.getEmail());
            stmt.setString(4, customer.getPhoneNumber());
            
            stmt.executeUpdate();

            // Retrieve the auto-generated customer ID
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    customer.customerId = generatedKeys.getInt(1);
                }
            }
        }
    }

    /**
     * Retrieves all customers from the database.
     * 
     * @return A list of all customers
     * @throws SQLException If a database access error occurs
     */
    public static List<Customer> getAllCustomers() throws SQLException {
        List<Customer> customers = new ArrayList<>();
        String sql = "SELECT * FROM customers";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            // Process each row in the result set
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

    /**
     * Updates an existing customer in the database.
     * 
     * @param customer The customer object with updated information
     * @throws SQLException If a database access error occurs
     */
    public static void updateCustomer(Customer customer) throws SQLException {
        String sql = "UPDATE customers SET first_name = ?, last_name = ?, email = ?, phone_number = ? WHERE customer_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            // Set parameters for the prepared statement
            stmt.setString(1, customer.getFirstName());
            stmt.setString(2, customer.getLastName());
            stmt.setString(3, customer.getEmail());
            stmt.setString(4, customer.getPhoneNumber());
            stmt.setInt(5, customer.getCustomerId());
            
            stmt.executeUpdate();
        }
    }

    /**
     * Deletes a customer from the database.
     * 
     * @param customerId The ID of the customer to be deleted
     * @throws SQLException If a database access error occurs
     */
    public static void deleteCustomer(int customerId) throws SQLException {
        String sql = "DELETE FROM customers WHERE customer_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, customerId);
            stmt.executeUpdate();
        }
    }
}