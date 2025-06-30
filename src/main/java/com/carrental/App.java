package main.java.com.carrental;

import main.java.com.carrental.controller.CarController;
import main.java.com.carrental.controller.CustomerController;
import main.java.com.carrental.controller.LoginController;
import main.java.com.carrental.controller.RentalController;
import main.java.com.carrental.view.LoginFrame;
import main.java.com.carrental.view.MainFrame;

import javax.swing.SwingUtilities;

/**
 * The main application class for the Car Rental System.
 * This class serves as the entry point for the application and initializes
 * all the necessary components including the main frame, login frame,
 * and various controllers.
 */
public class App {
    
    /**
     * The main method that launches the application.
     * 
     * @param args Command line arguments (not used in this application)
     */
    public static void main(String[] args) {
        // Use SwingUtilities.invokeLater to ensure thread-safe GUI operations
        SwingUtilities.invokeLater(() -> {
            // Create the main application frame (initially hidden)
            MainFrame mainFrame = new MainFrame();
            
            // Create the login frame
            LoginFrame loginFrame = new LoginFrame();
            
            // Initialize all controllers with their respective views
            // LoginController handles authentication between login and main frames
            new LoginController(loginFrame, mainFrame);
            
            // CarController manages the car panel operations
            new CarController(mainFrame.getCarPanel());
            
            // CustomerController manages the customer panel operations
            new CustomerController(mainFrame.getCustomerPanel());
            
            // RentalController manages the rental panel operations
            new RentalController(mainFrame.getRentalPanel());

            // Display the login frame first (main frame remains hidden until successful login)
            loginFrame.setVisible(true);
        });
    }
}