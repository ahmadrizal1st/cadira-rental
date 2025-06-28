package main.java.com.carrental.controller;

import main.java.com.carrental.model.Customer;
import main.java.com.carrental.view.CustomerPanel;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;
import java.util.List;

public class CustomerController {
    private CustomerPanel customerPanel;

    public CustomerController(CustomerPanel customerPanel) {
        this.customerPanel = customerPanel;
        initController();
    }

    private void initController() {
        customerPanel.getAddButton().addActionListener(e -> addCustomer());
        customerPanel.getUpdateButton().addActionListener(e -> updateCustomer());
        customerPanel.getDeleteButton().addActionListener(e -> deleteCustomer());
        customerPanel.getCustomerTable().getSelectionModel().addListSelectionListener(e -> displaySelectedCustomer());
        loadCustomers();
    }

    private void loadCustomers() {
        try {
            List<Customer> customers = Customer.getAllCustomers();
            DefaultTableModel model = customerPanel.getTableModel();
            model.setRowCount(0); // Clear existing data
            for (Customer customer : customers) {
                model.addRow(new Object[]{customer.getCustomerId(), customer.getFirstName(), customer.getLastName(), customer.getEmail(), customer.getPhoneNumber()});
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(customerPanel, "Error loading customers: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addCustomer() {
        try {
            String firstName = customerPanel.getFirstNameField().getText();
            String lastName = customerPanel.getLastNameField().getText();
            String email = customerPanel.getEmailField().getText();
            String phone = customerPanel.getPhoneField().getText();

            Customer customer = new Customer(0, firstName, lastName, email, phone);
            Customer.addCustomer(customer);
            JOptionPane.showMessageDialog(customerPanel, "Customer added successfully!");
            customerPanel.clearForm();
            loadCustomers();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(customerPanel, "Error adding customer: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateCustomer() {
        int selectedRow = customerPanel.getCustomerTable().getSelectedRow();
        if (selectedRow >= 0) {
            try {
                int customerId = (int) customerPanel.getTableModel().getValueAt(selectedRow, 0);
                String firstName = customerPanel.getFirstNameField().getText();
                String lastName = customerPanel.getLastNameField().getText();
                String email = customerPanel.getEmailField().getText();
                String phone = customerPanel.getPhoneField().getText();

                Customer customer = new Customer(customerId, firstName, lastName, email, phone);
                Customer.updateCustomer(customer);
                JOptionPane.showMessageDialog(customerPanel, "Customer updated successfully!");
                customerPanel.clearForm();
                loadCustomers();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(customerPanel, "Error updating customer: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(customerPanel, "Please select a customer to update.", "No Selection", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void deleteCustomer() {
        int selectedRow = customerPanel.getCustomerTable().getSelectedRow();
        if (selectedRow >= 0) {
            int customerId = (int) customerPanel.getTableModel().getValueAt(selectedRow, 0);
            int confirm = JOptionPane.showConfirmDialog(customerPanel, "Are you sure you want to delete this customer?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    Customer.deleteCustomer(customerId);
                    JOptionPane.showMessageDialog(customerPanel, "Customer deleted successfully!");
                    customerPanel.clearForm();
                    loadCustomers();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(customerPanel, "Error deleting customer: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(customerPanel, "Please select a customer to delete.", "No Selection", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void displaySelectedCustomer() {
        int selectedRow = customerPanel.getCustomerTable().getSelectedRow();
        if (selectedRow >= 0) {
            customerPanel.getFirstNameField().setText(customerPanel.getTableModel().getValueAt(selectedRow, 1).toString());
            customerPanel.getLastNameField().setText(customerPanel.getTableModel().getValueAt(selectedRow, 2).toString());
            customerPanel.getEmailField().setText(customerPanel.getTableModel().getValueAt(selectedRow, 3).toString());
            customerPanel.getPhoneField().setText(customerPanel.getTableModel().getValueAt(selectedRow, 4).toString());
        }
    }
}


