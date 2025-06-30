package main.java.com.carrental.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.border.TitledBorder;

/**
 * The CustomerPanel class represents the graphical user interface for managing customers
 * in the car rental system. It provides a table view of existing customers and a form
 * for adding/editing customer information.
 */
public class CustomerPanel extends JPanel {
    // UI Components
    private JTable customerTable;
    private DefaultTableModel tableModel;
    private JButton addButton, updateButton, deleteButton;
    private JTextField firstNameField, lastNameField, emailField, phoneField;

    /**
     * Constructs a new CustomerPanel with all UI components initialized.
     */
    public CustomerPanel() {
        // Apply theme before creating components
        Theme.applyTheme();
        
        // Set up main panel properties
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setBackground(Theme.SECONDARY_COLOR);

        initializeTable();
        initializeFormPanel();
        initializeButtonPanel();
    }

    /**
     * Initializes and configures the customer table with appropriate styling.
     */
    private void initializeTable() {
        // Create table model with column headers
        tableModel = new DefaultTableModel(
            new Object[]{"ID", "First Name", "Last Name", "Email", "Phone"}, 
            0);
        
        // Configure table appearance
        customerTable = new JTable(tableModel);
        customerTable.setRowHeight(25);
        customerTable.setFillsViewportHeight(true);
        customerTable.setSelectionBackground(Theme.LIGHT_RED);
        customerTable.setSelectionForeground(Theme.TERTIARY_COLOR);
        
        // Add table to scroll pane with themed border
        JScrollPane tableScroll = new JScrollPane(customerTable);
        tableScroll.setBorder(Theme.PANEL_BORDER);
        add(tableScroll, BorderLayout.CENTER);
    }

    /**
     * Initializes the form panel with input fields for customer details.
     */
    private void initializeFormPanel() {
        JPanel formPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        formPanel.setBackground(Theme.SECONDARY_COLOR);
        formPanel.setBorder(createFormBorder());

        // Add all form fields
        addFormField(formPanel, "First Name:", firstNameField = new JTextField());
        addFormField(formPanel, "Last Name:", lastNameField = new JTextField());
        addFormField(formPanel, "Email:", emailField = new JTextField());
        addFormField(formPanel, "Phone:", phoneField = new JTextField());

        // Add form panel to south panel
        JPanel southPanel = new JPanel(new BorderLayout(10, 10));
        southPanel.setBorder(Theme.PANEL_BORDER);
        southPanel.setBackground(Theme.SECONDARY_COLOR);
        southPanel.add(formPanel, BorderLayout.CENTER);
        add(southPanel, BorderLayout.SOUTH);
    }

    /**
     * Creates a styled border for the form panel.
     */
    private TitledBorder createFormBorder() {
        return BorderFactory.createTitledBorder(
            BorderFactory.createCompoundBorder(
                new MatteBorder(1, 0, 0, 0, Theme.SUPPORT_COLOR),
                new EmptyBorder(5, 5, 5, 5)
            ),
            "Customer Details",
            TitledBorder.LEFT,
            TitledBorder.TOP,
            Theme.SUBTITLE_FONT,
            Theme.PRIMARY_COLOR
        );
    }

    /**
     * Initializes the button panel with action buttons.
     */
    private void initializeButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setBackground(Theme.SECONDARY_COLOR);
        
        // Create styled buttons
        addButton = createStyledButton("Add Customer");
        updateButton = createStyledButton("Update Customer");
        deleteButton = createStyledButton("Delete Customer");
        
        // Add buttons to panel
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);

        // Add button panel to south panel
        ((BorderLayout) ((JPanel) getComponent(1)).getLayout()).addLayoutComponent(buttonPanel, BorderLayout.SOUTH);
    }

    /**
     * Adds a labeled form field to the specified panel.
     * 
     * @param panel The panel to add the field to
     * @param labelText The text for the field label
     * @param field The text field component
     */
    private void addFormField(JPanel panel, String labelText, JTextField field) {
        JLabel label = new JLabel(labelText);
        label.setFont(Theme.BODY_FONT);
        panel.add(label);
        
        field.setFont(Theme.BODY_FONT);
        field.setBorder(Theme.INPUT_BORDER);
        field.setPreferredSize(Theme.DEFAULT_FIELD_SIZE);
        panel.add(field);
    }

    /**
     * Creates a styled button with hover effects.
     * 
     * @param text The button text
     * @return The configured JButton
     */
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(Theme.BUTTON_FONT);
        button.setBackground(Theme.PRIMARY_COLOR);
        button.setForeground(Theme.SECONDARY_COLOR);
        button.setFocusPainted(false);
        button.setPreferredSize(Theme.DEFAULT_BUTTON_SIZE);
        button.setBorder(Theme.ROUNDED_BORDER);
        
        // Add hover effects
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(Theme.PRIMARY_COLOR.brighter());
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(Theme.PRIMARY_COLOR);
            }
        });
        
        return button;
    }

    // Getters for UI components
    public DefaultTableModel getTableModel() { return tableModel; }
    public JButton getAddButton() { return addButton; }
    public JButton getUpdateButton() { return updateButton; }
    public JButton getDeleteButton() { return deleteButton; }
    public JTextField getFirstNameField() { return firstNameField; }
    public JTextField getLastNameField() { return lastNameField; }
    public JTextField getEmailField() { return emailField; }
    public JTextField getPhoneField() { return phoneField; }
    public JTable getCustomerTable() { return customerTable; }

    /**
     * Clears all input fields in the form.
     */
    public void clearForm() {
        firstNameField.setText("");
        lastNameField.setText("");
        emailField.setText("");
        phoneField.setText("");
    }
}