package main.java.com.carrental.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import org.jdesktop.swingx.JXDatePicker;
import javax.swing.SpinnerDateModel;
import java.util.Date;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.border.TitledBorder;

/**
 * The RentalPanel class represents the user interface for managing rental transactions
 * in the car rental system. It includes date/time pickers, cost calculation,
 * and CRUD operations for rentals.
 */
public class RentalPanel extends JPanel {
    private JTable rentalTable;
    private DefaultTableModel tableModel;
    private JButton addButton, updateButton, deleteButton, calculateCostButton;
    private JTextField carIdField, customerIdField;
    private JXDatePicker rentalDatePicker, returnDatePicker;
    private JSpinner rentalTimeSpinner, returnTimeSpinner;
    private JLabel totalCostLabel;

    /**
     * Constructs a new RentalPanel with all UI components initialized.
     */
    public RentalPanel() {
        // Apply theme before creating components
        Theme.applyTheme();
        
        // Configure main panel properties
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setBackground(Theme.SECONDARY_COLOR);

        initializeTable();
        initializeFormPanel();
    }

    /**
     * Initializes and configures the rental table.
     */
    private void initializeTable() {
        tableModel = new DefaultTableModel(
            new Object[]{"ID", "Car", "Customer", "Rental Date", "Return Date", "Total Cost"}, 
            0
        );
        
        rentalTable = new JTable(tableModel);
        rentalTable.setRowHeight(25);
        rentalTable.setFillsViewportHeight(true);
        rentalTable.setSelectionBackground(Theme.LIGHT_RED);
        rentalTable.setSelectionForeground(Theme.TERTIARY_COLOR);
        
        JScrollPane tableScroll = new JScrollPane(rentalTable);
        tableScroll.setBorder(Theme.PANEL_BORDER);
        add(tableScroll, BorderLayout.CENTER);
    }

    /**
     * Initializes the form panel with all input fields and buttons.
     */
    private void initializeFormPanel() {
        JPanel formPanel = createFormPanel();
        JPanel buttonPanel = createButtonPanel();
        
        JPanel southPanel = new JPanel(new BorderLayout(10, 10));
        southPanel.setBorder(Theme.PANEL_BORDER);
        southPanel.setBackground(Theme.SECONDARY_COLOR);
        southPanel.add(formPanel, BorderLayout.CENTER);
        southPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(southPanel, BorderLayout.SOUTH);
    }

    /**
     * Creates the form panel with all input fields.
     */
    private JPanel createFormPanel() {
        JPanel formPanel = new JPanel(new GridLayout(6, 2, 10, 10));
        formPanel.setBackground(Theme.SECONDARY_COLOR);
        formPanel.setBorder(createFormBorder());

        // Add basic form fields
        addFormField(formPanel, "Car ID:", carIdField = new JTextField());
        addFormField(formPanel, "Customer ID:", customerIdField = new JTextField());

        // Add rental datetime components
        addDateTimeComponents(formPanel, "Rental Datetime:", 
            rentalDatePicker = new JXDatePicker(), 
            rentalTimeSpinner = createTimeSpinner());

        // Add return datetime components
        addDateTimeComponents(formPanel, "Return Datetime:", 
            returnDatePicker = new JXDatePicker(), 
            returnTimeSpinner = createTimeSpinner());

        // Add total cost display
        addTotalCostDisplay(formPanel);

        return formPanel;
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
            "Rental Details",
            TitledBorder.LEFT,
            TitledBorder.TOP,
            Theme.SUBTITLE_FONT,
            Theme.PRIMARY_COLOR
        );
    }

    /**
     * Adds date/time components to the form panel.
     */
    private void addDateTimeComponents(JPanel panel, String label, JXDatePicker datePicker, JSpinner timeSpinner) {
        JLabel dateLabel = new JLabel(label);
        dateLabel.setFont(Theme.BODY_FONT);
        panel.add(dateLabel);
        
        JPanel datetimePanel = new JPanel(new BorderLayout(5, 0));
        datetimePanel.setBackground(Theme.SECONDARY_COLOR);
        
        styleDatePicker(datePicker);
        datetimePanel.add(datePicker, BorderLayout.CENTER);
        
        JSpinner.DateEditor timeEditor = new JSpinner.DateEditor(timeSpinner, "HH:mm");
        styleTimeSpinner(timeSpinner, timeEditor);
        datetimePanel.add(timeSpinner, BorderLayout.EAST);
        
        panel.add(datetimePanel);
    }

    /**
     * Adds the total cost display to the form panel.
     */
    private void addTotalCostDisplay(JPanel panel) {
        JLabel costLabel = new JLabel("Total Cost:");
        costLabel.setFont(Theme.BODY_FONT);
        panel.add(costLabel);
        
        totalCostLabel = new JLabel("0.00");
        totalCostLabel.setFont(Theme.BODY_FONT);
        totalCostLabel.setBorder(BorderFactory.createCompoundBorder(
            new MatteBorder(1, 1, 1, 1, Theme.SUPPORT_COLOR),
            new EmptyBorder(5, 10, 5, 10)
        ));
        panel.add(totalCostLabel);
    }

    /**
     * Creates the button panel with all action buttons.
     */
    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setBackground(Theme.SECONDARY_COLOR);
        
        addButton = createStyledButton("Add Rental");
        updateButton = createStyledButton("Update Rental");
        deleteButton = createStyledButton("Delete Rental");
        calculateCostButton = createStyledButton("Calculate Cost");
        
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(calculateCostButton);

        return buttonPanel;
    }

    /**
     * Creates a new time spinner with default settings.
     */
    private JSpinner createTimeSpinner() {
        return new JSpinner(new SpinnerDateModel(new Date(), null, null, java.util.Calendar.HOUR_OF_DAY));
    }

    /**
     * Adds a form field with label to the specified panel.
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
     */
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(Theme.BUTTON_FONT);
        button.setBackground(Theme.PRIMARY_COLOR);
        button.setForeground(Theme.SECONDARY_COLOR);
        button.setFocusPainted(false);
        button.setPreferredSize(Theme.DEFAULT_BUTTON_SIZE);
        button.setBorder(Theme.ROUNDED_BORDER);
        
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

    /**
     * Styles a date picker component with consistent theme.
     */
    private void styleDatePicker(JXDatePicker picker) {
        picker.setFormats("dd/MM/yyyy");
        picker.getEditor().setFont(Theme.BODY_FONT);
        picker.getEditor().setBorder(Theme.INPUT_BORDER);
        picker.getEditor().setBackground(Theme.SECONDARY_COLOR);
        picker.getEditor().setForeground(Theme.TERTIARY_COLOR);
        picker.setBorder(BorderFactory.createEmptyBorder());
    }

    /**
     * Styles a time spinner component with consistent theme.
     */
    private void styleTimeSpinner(JSpinner spinner, JSpinner.DateEditor editor) {
        spinner.setFont(Theme.BODY_FONT);
        spinner.setBorder(Theme.INPUT_BORDER);
        spinner.setBackground(Theme.SECONDARY_COLOR);
        spinner.setForeground(Theme.TERTIARY_COLOR);
        editor.getTextField().setBorder(BorderFactory.createEmptyBorder());
        editor.getTextField().setBackground(Theme.SECONDARY_COLOR);
    }

    // Getters for UI components
    public DefaultTableModel getTableModel() { return tableModel; }
    public JButton getAddButton() { return addButton; }
    public JButton getUpdateButton() { return updateButton; }
    public JButton getDeleteButton() { return deleteButton; }
    public JButton getCalculateCostButton() { return calculateCostButton; }
    public JTextField getCarIdField() { return carIdField; }
    public JTextField getCustomerIdField() { return customerIdField; }
    public JLabel getTotalCostLabel() { return totalCostLabel; }
    public JTable getRentalTable() { return rentalTable; }

    /**
     * Gets the rental date picker value as LocalDate.
     */
    public JXDatePicker getRentalDatePicker() {
        if (rentalDatePicker.getDate() == null) return null;
        
        Date date = rentalDatePicker.getDate();
        LocalDate localDate = date.toInstant()
                                .atZone(ZoneId.systemDefault())
                                .toLocalDate();
        
        Date newDate = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        rentalDatePicker.setDate(newDate);
        
        return rentalDatePicker;
    }

    /**
     * Gets the rental time spinner value as LocalTime.
     */
    public JSpinner getRentalTimeSpinner() {
        if (rentalTimeSpinner.getValue() == null) return null;

        Date time = (Date) rentalTimeSpinner.getValue();
        LocalTime rentalTime = time.toInstant()
                                 .atZone(ZoneId.systemDefault())
                                 .toLocalTime()
                                 .truncatedTo(ChronoUnit.MINUTES);

        JSpinner newSpinner = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor timeEditor = new JSpinner.DateEditor(newSpinner, "HH:mm");
        newSpinner.setEditor(timeEditor);
        newSpinner.setValue(Date.from(rentalTime.atDate(LocalDate.now())
                              .atZone(ZoneId.systemDefault())
                              .toInstant()));

        return newSpinner;
    }

    /**
     * Combines rental date and time into LocalDateTime.
     */
    public LocalDateTime getRentalDateTime() {
        if (rentalDatePicker.getDate() == null || rentalTimeSpinner.getValue() == null) {
            return null;
        }

        Date date = rentalDatePicker.getDate();
        Date time = (Date) rentalTimeSpinner.getValue();

        return LocalDateTime.of(
            date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
            time.toInstant().atZone(ZoneId.systemDefault()).toLocalTime()
                .truncatedTo(ChronoUnit.MINUTES)
        );
    }

    /**
     * Gets the return date picker value as LocalDate.
     */
    public JXDatePicker getReturnDatePicker() {
        if (returnDatePicker.getDate() == null) return null;
        
        Date date = returnDatePicker.getDate();
        LocalDate localDate = date.toInstant()
                                .atZone(ZoneId.systemDefault())
                                .toLocalDate();
        
        Date newDate = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        returnDatePicker.setDate(newDate);
        
        return returnDatePicker;
    }

    /**
     * Gets the return time spinner value as LocalTime.
     */
    public JSpinner getReturnTimeSpinner() {
        if (returnTimeSpinner.getValue() == null) return null;

        Date time = (Date) returnTimeSpinner.getValue();
        LocalTime returnTime = time.toInstant()
                                 .atZone(ZoneId.systemDefault())
                                 .toLocalTime()
                                 .truncatedTo(ChronoUnit.MINUTES);

        JSpinner newSpinner = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor timeEditor = new JSpinner.DateEditor(newSpinner, "HH:mm");
        newSpinner.setEditor(timeEditor);
        newSpinner.setValue(Date.from(returnTime.atDate(LocalDate.now())
                              .atZone(ZoneId.systemDefault())
                              .toInstant()));

        return newSpinner;
    }

    /**
     * Combines return date and time into LocalDateTime.
     */
    public LocalDateTime getReturnDateTime() {
        if (returnDatePicker.getDate() == null || returnTimeSpinner.getValue() == null) {
            return null;
        }

        Date date = returnDatePicker.getDate();
        Date time = (Date) returnTimeSpinner.getValue();

        return LocalDateTime.of(
            date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
            time.toInstant().atZone(ZoneId.systemDefault()).toLocalTime()
                .truncatedTo(ChronoUnit.MINUTES)
        );
    }

    /**
     * Clears all input fields in the form.
     */
    public void clearForm() {
        carIdField.setText("");
        customerIdField.setText("");
        rentalDatePicker.setDate(null);
        rentalTimeSpinner.setValue(new java.sql.Time(0));
        returnDatePicker.setDate(null);
        returnTimeSpinner.setValue(new java.sql.Time(0));
        totalCostLabel.setText("0.00");
    }

    /**
     * Sets the rental date picker value.
     */
    public LocalDate setRentalDatePicker(Date date) {
        rentalDatePicker.setDate(date);
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    /**
     * Sets the rental time spinner value.
     */
    public void setRentalTimeSpinner(java.sql.Time time) {
        if (time == null) {
            rentalTimeSpinner.setValue(null);
            return;
        }

        LocalTime localTime = time.toLocalTime();
        LocalDateTime dummyDateTime = LocalDate.now().atTime(localTime);
        Date date = Date.from(dummyDateTime.atZone(ZoneId.systemDefault()).toInstant());
        rentalTimeSpinner.setValue(date);
    }

    /**
     * Sets the return date picker value.
     */
    public LocalDate setReturnDatePicker(Date date) {
        returnDatePicker.setDate(date);
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    /**
     * Sets the return time spinner value.
     */
    public void setReturnTimeSpinner(java.sql.Time time) {
        if (time == null) {
            returnTimeSpinner.setValue(null);
            return;
        }

        LocalTime localTime = time.toLocalTime();
        LocalDateTime dummyDateTime = LocalDate.now().atTime(localTime);
        Date date = Date.from(dummyDateTime.atZone(ZoneId.systemDefault()).toInstant());
        returnTimeSpinner.setValue(date);
    }
}