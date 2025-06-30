package main.java.com.carrental.controller;

import main.java.com.carrental.model.Customer;
import main.java.com.carrental.view.CustomerPanel;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;
import java.util.List;

/**
 * Controller class for managing customer-related operations.
 * Handles the interaction between the Customer model and CustomerPanel view,
 * including CRUD operations and user interface events.
 */
public class CustomerController {
    private CustomerPanel customerPanel;

    /**
     * Constructs a CustomerController with the specified CustomerPanel.
     * 
     * @param customerPanel The view component for customer operations
     */
    public CustomerController(CustomerPanel customerPanel) {
        this.customerPanel = customerPanel;
        initController();
    }

    /**
     * Initializes the controller by setting up event listeners
     * and loading initial customer data.
     */
    private void initController() {
        // Set up action listeners for CRUD buttons
        customerPanel.getAddButton().addActionListener(e -> addCustomer());
        customerPanel.getUpdateButton().addActionListener(e -> updateCustomer());
        customerPanel.getDeleteButton().addActionListener(e -> deleteCustomer());
        
        // Add selection listener to display selected customer details
        customerPanel.getCustomerTable().getSelectionModel().addListSelectionListener(
            e -> displaySelectedCustomer());
        
        // Load initial customer data
        loadCustomers();
    }

    /**
     * Loads all customers from the database and populates the table.
     * Displays an error message if the database operation fails.
     */
    private void loadCustomers() {
        try {
            List<Customer> customers = Customer.getAllCustomers();
            DefaultTableModel model = customerPanel.getTableModel();
            model.setRowCount(0); // Clear existing data
            
            // Add each customer to the table model
            for (Customer customer : customers) {
                model.addRow(new Object[]{
                    customer.getCustomerId(),
                    customer.getFirstName(),
                    customer.getLastName(),
                    customer.getEmail(),
                    customer.getPhoneNumber()
                });
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(customerPanel,
                "Error loading customers: " + ex.getMessage(),
                "Database Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Adds a new customer to the database using form data.
     * Validates input and shows appropriate error messages.
     */
    private void addCustomer() {
        try {
            // Get data from form fields
            String firstName = customerPanel.getFirstNameField().getText();
            String lastName = customerPanel.getLastNameField().getText();
            String email = customerPanel.getEmailField().getText();
            String phone = customerPanel.getPhoneField().getText();

            // Create and save new customer
            Customer customer = new Customer(0, firstName, lastName, email, phone);
            Customer.addCustomer(customer);
            
            JOptionPane.showMessageDialog(customerPanel,
                "Customer added successfully!");
            customerPanel.clearForm();
            loadCustomers(); // Refresh the table
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(customerPanel,
                "Error adding customer: " + ex.getMessage(),
                "Database Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Updates an existing customer in the database.
     * Requires a customer to be selected in the table.
     */
    private void updateCustomer() {
        int selectedRow = customerPanel.getCustomerTable().getSelectedRow();
        if (selectedRow >= 0) {
            try {
                // Get data from form fields and selected row
                int customerId = (int) customerPanel.getTableModel().getValueAt(selectedRow, 0);
                String firstName = customerPanel.getFirstNameField().getText();
                String lastName = customerPanel.getLastNameField().getText();
                String email = customerPanel.getEmailField().getText();
                String phone = customerPanel.getPhoneField().getText();

                // Update the customer
                Customer customer = new Customer(customerId, firstName, lastName, email, phone);
                Customer.updateCustomer(customer);
                
                JOptionPane.showMessageDialog(customerPanel,
                    "Customer updated successfully!");
                customerPanel.clearForm();
                loadCustomers(); // Refresh the table
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(customerPanel,
                    "Error updating customer: " + ex.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(customerPanel,
                "Please select a customer to update.",
                "No Selection",
                JOptionPane.WARNING_MESSAGE);
        }
    }

    /**
     * Deletes the selected customer from the database after confirmation.
     */
    private void deleteCustomer() {
        int selectedRow = customerPanel.getCustomerTable().getSelectedRow();
        if (selectedRow >= 0) {
            int customerId = (int) customerPanel.getTableModel().getValueAt(selectedRow, 0);
            
            // Confirm deletion with user
            int confirm = JOptionPane.showConfirmDialog(customerPanel,
                "Are you sure you want to delete this customer?",
                "Confirm Deletion",
                JOptionPane.YES_NO_OPTION);
            
            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    Customer.deleteCustomer(customerId);
                    JOptionPane.showMessageDialog(customerPanel,
                        "Customer deleted successfully!");
                    customerPanel.clearForm();
                    loadCustomers(); // Refresh the table
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(customerPanel,
                        "Error deleting customer: " + ex.getMessage(),
                        "Database Error",
                        JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(customerPanel,
                "Please select a customer to delete.",
                "No Selection",
                JOptionPane.WARNING_MESSAGE);
        }
    }

    /**
     * Displays the details of the selected customer in the form fields.
     */
    private void displaySelectedCustomer() {
        int selectedRow = customerPanel.getCustomerTable().getSelectedRow();
        if (selectedRow >= 0) {
            // Populate form fields with selected customer's data
            customerPanel.getFirstNameField().setText(
                customerPanel.getTableModel().getValueAt(selectedRow, 1).toString());
            customerPanel.getLastNameField().setText(
                customerPanel.getTableModel().getValueAt(selectedRow, 2).toString());
            customerPanel.getEmailField().setText(
                customerPanel.getTableModel().getValueAt(selectedRow, 3).toString());
            customerPanel.getPhoneField().setText(
                customerPanel.getTableModel().getValueAt(selectedRow, 4).toString());
        }
    }
}