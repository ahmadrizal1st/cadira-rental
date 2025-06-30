package main.java.com.carrental.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.border.TitledBorder;

/**
 * The CarPanel class represents the graphical user interface for managing cars in the car rental system.
 * It provides a table view of existing cars and a form for adding/editing car information.
 */
public class CarPanel extends JPanel {
    // UI Components
    private JTable carTable;
    private DefaultTableModel tableModel;
    private JButton addButton, updateButton, deleteButton;
    private JTextField makeField, modelField, yearField, licensePlateField, hourlyRateField;
    private JCheckBox availableCheckBox;

    /**
     * Constructs a new CarPanel with all UI components initialized.
     */
    public CarPanel() {
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
     * Initializes and configures the car table with appropriate styling.
     */
    private void initializeTable() {
        // Create table model with column headers
        tableModel = new DefaultTableModel(
            new Object[]{"ID", "Make", "Model", "Year", "License Plate", "Hourly Rate", "Available"}, 
            0) {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return columnIndex == 6 ? Boolean.class : String.class;
            }
        };
        
        // Configure table appearance
        carTable = new JTable(tableModel);
        carTable.setRowHeight(25);
        carTable.setFillsViewportHeight(true);
        carTable.setSelectionBackground(Theme.LIGHT_RED);
        carTable.setSelectionForeground(Theme.TERTIARY_COLOR);
        
        // Add table to scroll pane with themed border
        JScrollPane tableScroll = new JScrollPane(carTable);
        tableScroll.setBorder(Theme.PANEL_BORDER);
        add(tableScroll, BorderLayout.CENTER);
    }

    /**
     * Initializes the form panel with input fields for car details.
     */
    private void initializeFormPanel() {
        JPanel formPanel = new JPanel(new GridLayout(7, 2, 10, 10));
        formPanel.setBorder(createFormBorder());
        formPanel.setBackground(Theme.SECONDARY_COLOR);

        // Add all form fields
        addFormField(formPanel, "Make:", makeField = new JTextField());
        addFormField(formPanel, "Model:", modelField = new JTextField());
        addFormField(formPanel, "Year:", yearField = new JTextField());
        addFormField(formPanel, "License Plate:", licensePlateField = new JTextField());
        addFormField(formPanel, "Hourly Rate:", hourlyRateField = new JTextField());
        
        // Add availability checkbox
        JLabel availableLabel = new JLabel("Available:");
        availableLabel.setFont(Theme.BODY_FONT);
        formPanel.add(availableLabel);
        
        availableCheckBox = new JCheckBox();
        availableCheckBox.setBackground(Theme.SECONDARY_COLOR);
        formPanel.add(availableCheckBox);

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
            "Car Details", 
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
        addButton = createStyledButton("Add Car");
        updateButton = createStyledButton("Update Car");
        deleteButton = createStyledButton("Delete Car");
        
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
    public JTextField getMakeField() { return makeField; }
    public JTextField getModelField() { return modelField; }
    public JTextField getYearField() { return yearField; }
    public JTextField getLicensePlateField() { return licensePlateField; }
    public JTextField getHourlyRateField() { return hourlyRateField; }
    public JCheckBox getAvailableCheckBox() { return availableCheckBox; }
    public JTable getCarTable() { return carTable; }

    /**
     * Clears all input fields in the form.
     */
    public void clearForm() {
        makeField.setText("");
        modelField.setText("");
        yearField.setText("");
        licensePlateField.setText("");
        hourlyRateField.setText("");
        availableCheckBox.setSelected(false);
    }
}