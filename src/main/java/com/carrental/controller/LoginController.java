package main.java.com.carrental.controller;

import main.java.com.carrental.model.User;
import main.java.com.carrental.view.LoginFrame;
import main.java.com.carrental.view.MainFrame;

import javax.swing.JOptionPane;
import java.sql.SQLException;

/**
 * Controller class for handling user authentication and login operations.
 * Manages the interaction between the LoginFrame, MainFrame, and User model.
 * Validates user credentials and controls the transition from login to main application.
 */
public class LoginController {
    private LoginFrame loginFrame;
    private MainFrame mainFrame;

    /**
     * Constructs a LoginController with the specified login and main frames.
     * 
     * @param loginFrame The login view component
     * @param mainFrame The main application frame to show after successful login
     */
    public LoginController(LoginFrame loginFrame, MainFrame mainFrame) {
        this.loginFrame = loginFrame;
        this.mainFrame = mainFrame;
        initController();
    }

    /**
     * Initializes the controller by setting up the login button action listener.
     */
    private void initController() {
        loginFrame.getLoginButton().addActionListener(e -> authenticateUser());
    }

    /**
     * Authenticates the user by validating credentials against the database.
     * Handles empty fields, invalid credentials, and database errors.
     * On successful login, closes the login frame and opens the main application.
     */
    private void authenticateUser() {
        // Get credentials from the login form
        String username = loginFrame.getUsername();
        String password = loginFrame.getPassword();

        // Validate empty fields
        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(loginFrame, 
                "Username and password cannot be empty.", 
                "Login Error", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            // Retrieve user from database
            User user = User.getUserByUsername(username);
            
            // Validate credentials
            if (user != null && user.getPassword().equals(password)) {
                // Note: In production, use password hashing comparison
                
                // Successful login flow
                JOptionPane.showMessageDialog(loginFrame, "Login Successful!");
                loginFrame.dispose(); // Close login window
                mainFrame.setVisible(true); // Show main application
            } else {
                // Invalid credentials flow
                JOptionPane.showMessageDialog(loginFrame, 
                    "Invalid Username or Password.", 
                    "Login Error", 
                    JOptionPane.ERROR_MESSAGE);
                loginFrame.clearFields(); // Clear for retry
            }
        } catch (SQLException ex) {
            // Database error handling
            JOptionPane.showMessageDialog(loginFrame, 
                "Database error: " + ex.getMessage(), 
                "Login Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
}