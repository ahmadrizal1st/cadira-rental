package main.java.com.carrental;

import main.java.com.carrental.controller.CarController;
import main.java.com.carrental.controller.CustomerController;
import main.java.com.carrental.controller.LoginController;
import main.java.com.carrental.controller.RentalController;
import main.java.com.carrental.view.LoginFrame;
import main.java.com.carrental.view.MainFrame;

import javax.swing.SwingUtilities;

public class App {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainFrame mainFrame = new MainFrame();
            LoginFrame loginFrame = new LoginFrame();
            
            // Initialize controllers
            new LoginController(loginFrame, mainFrame);
            new CarController(mainFrame.getCarPanel());
            new CustomerController(mainFrame.getCustomerPanel());
            new RentalController(mainFrame.getRentalPanel());

            loginFrame.setVisible(true); // Show login frame first
        });
    }
}


