package main.java.com.carrental.model;

import java.sql.*;

/**
 * Represents a user entity in the car rental system.
 * This class models user authentication information and provides
 * database operations for user management.
 * 
 * <p><b>Security Note:</b> In a production environment, passwords should
 * be hashed and never stored in plain text.</p>
 */
public class User {
    private int userId;
    private String username;
    private String password;

    /**
     * Constructs a new User instance.
     * 
     * @param userId The unique identifier for the user
     * @param username The username for authentication
     * @param password The password for authentication (should be hashed in production)
     */
    public User(int userId, String username, String password) {
        this.userId = userId;
        this.username = username;
        this.password = password;
    }

    // Getters
    /** @return The user's unique identifier */
    public int getUserId() { return userId; }
    
    /** @return The username for authentication */
    public String getUsername() { return username; }
    
    /** @return The password for authentication */
    public String getPassword() { return password; }

    // Setters
    /** @param username The username to set */
    public void setUsername(String username) { this.username = username; }
    
    /** @param password The password to set (should be hashed in production) */
    public void setPassword(String password) { this.password = password; }

    // Database operations
    
    /**
     * Retrieves a user from the database by username.
     * 
     * @param username The username to search for
     * @return User object if found, null otherwise
     * @throws SQLException If a database access error occurs
     */
    public static User getUserByUsername(String username) throws SQLException {
        String sql = "SELECT * FROM users WHERE username = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, username);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new User(
                        rs.getInt("user_id"),
                        rs.getString("username"),
                        rs.getString("password")
                    );
                }
            }
        }
        return null;
    }

    /**
     * Adds a new user to the database.
     * 
     * @param user The user object to be added
     * @throws SQLException If a database access error occurs
     * 
     * @implNote This method stores passwords in plain text. In production,
     *           passwords should be hashed before storage.
     */
    public static void addUser(User user) throws SQLException {
        String sql = "INSERT INTO users (username, password) VALUES (?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.executeUpdate();

            // Retrieve the auto-generated user ID
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    user.userId = generatedKeys.getInt(1);
                }
            }
        }
    }
}