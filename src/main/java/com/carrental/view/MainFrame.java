package main.java.com.carrental.view;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    private JTabbedPane tabbedPane;
    private CarPanel carPanel;
    private CustomerPanel customerPanel;
    private RentalPanel rentalPanel;

    public MainFrame() {
        setTitle("Car Rental Management System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window

        tabbedPane = new JTabbedPane();

        carPanel = new CarPanel();
        customerPanel = new CustomerPanel();
        rentalPanel = new RentalPanel();

        tabbedPane.addTab("Cars", carPanel);
        tabbedPane.addTab("Customers", customerPanel);
        tabbedPane.addTab("Rentals", rentalPanel);

        add(tabbedPane, BorderLayout.CENTER);
    }

    public CarPanel getCarPanel() {
        return carPanel;
    }

    public CustomerPanel getCustomerPanel() {
        return customerPanel;
    }

    public RentalPanel getRentalPanel() {
        return rentalPanel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MainFrame().setVisible(true);
        });
    }
}


