package main.java.com.carrental.controller;

import main.java.com.carrental.model.User;
import main.java.com.carrental.view.LoginFrame;
import main.java.com.carrental.view.MainFrame;

import javax.swing.JOptionPane;
import java.sql.SQLException;

public class LoginController {
    private LoginFrame loginFrame;
    private MainFrame mainFrame;

    public LoginController(LoginFrame loginFrame, MainFrame mainFrame) {
        this.loginFrame = loginFrame;
        this.mainFrame = mainFrame;
        initController();
    }

    private void initController() {
        loginFrame.getLoginButton().addActionListener(e -> authenticateUser());
    }

    private void authenticateUser() {
        String username = loginFrame.getUsername();
        String password = loginFrame.getPassword();

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(loginFrame, "Username and password cannot be empty.", "Login Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            User user = User.getUserByUsername(username);
            if (user != null && user.getPassword().equals(password)) { // In a real application, use hashed passwords
                JOptionPane.showMessageDialog(loginFrame, "Login Successful!");
                loginFrame.dispose(); // Close login frame
                mainFrame.setVisible(true); // Show main application frame
            } else {
                JOptionPane.showMessageDialog(loginFrame, "Invalid Username or Password.", "Login Error", JOptionPane.ERROR_MESSAGE);
                loginFrame.clearFields();
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(loginFrame, "Database error: " + ex.getMessage(), "Login Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}


