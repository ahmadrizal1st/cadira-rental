package main.java.com.carrental.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import org.jdesktop.swingx.JXDatePicker;  // Import JXDatePicker
import javax.swing.SpinnerDateModel;
import java.util.Date;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

public class RentalPanel extends JPanel {
    private JTable rentalTable;
    private DefaultTableModel tableModel;
    private JButton addButton, updateButton, deleteButton, calculateCostButton;
    private JTextField carIdField, customerIdField;
    private JXDatePicker rentalDatePicker, returnDatePicker;  // JXDatePicker digunakan di sini
    private JSpinner rentalTimeSpinner, returnTimeSpinner;
    private JLabel totalCostLabel;

    public RentalPanel() {
        setLayout(new BorderLayout());

        // Table setup
        tableModel = new DefaultTableModel(new Object[]{"ID", "Car ID", "Customer ID", "Rental Datetime", "Return Datetime", "Total Cost"}, 0);
        rentalTable = new JTable(tableModel);
        add(new JScrollPane(rentalTable), BorderLayout.CENTER);

        // Form panel
        JPanel formPanel = new JPanel(new GridLayout(6, 2));
        formPanel.setBorder(BorderFactory.createTitledBorder("Rental Details"));

        formPanel.add(new JLabel("Car ID:"));
        carIdField = new JTextField();
        formPanel.add(carIdField);

        formPanel.add(new JLabel("Customer ID:"));
        customerIdField = new JTextField();
        formPanel.add(customerIdField);

        formPanel.add(new JLabel("Rental Datetime:"));
        JPanel rentalPanel = new JPanel(new BorderLayout());
        
        // Date Picker for Rental Date
        rentalDatePicker = new JXDatePicker();  
        rentalPanel.add(rentalDatePicker, BorderLayout.CENTER);
        
        // Time Spinner for Rental Time only
        rentalTimeSpinner = new JSpinner(new SpinnerDateModel(
                new Date(), null, null, java.util.Calendar.HOUR_OF_DAY));
        JSpinner.DateEditor rentalEditor = new JSpinner.DateEditor(rentalTimeSpinner, "HH:mm");
        rentalTimeSpinner.setEditor(rentalEditor);
        rentalPanel.add(rentalTimeSpinner, BorderLayout.EAST);
        formPanel.add(rentalPanel);

        formPanel.add(new JLabel("Return Datetime:"));
        JPanel returnPanel = new JPanel(new BorderLayout());
        
        // Date Picker for Return Date
        returnDatePicker = new JXDatePicker();
        returnPanel.add(returnDatePicker, BorderLayout.CENTER);

        // Time Spinner for Return Time only
        returnTimeSpinner = new JSpinner(new SpinnerDateModel(
                new Date(), null, null, java.util.Calendar.HOUR_OF_DAY));
        JSpinner.DateEditor returnEditor = new JSpinner.DateEditor(returnTimeSpinner, "HH:mm");
        returnTimeSpinner.setEditor(returnEditor);
        returnPanel.add(returnTimeSpinner, BorderLayout.EAST);
        formPanel.add(returnPanel);

        formPanel.add(new JLabel("Total Cost:"));
        totalCostLabel = new JLabel("0.00");
        formPanel.add(totalCostLabel);

        // Button panel
        JPanel buttonPanel = new JPanel();
        addButton = new JButton("Add Rental");
        updateButton = new JButton("Update Rental");
        deleteButton = new JButton("Delete Rental");
        calculateCostButton = new JButton("Calculate Cost");
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(calculateCostButton);

        JPanel southPanel = new JPanel(new BorderLayout());
        southPanel.add(formPanel, BorderLayout.CENTER);
        southPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(southPanel, BorderLayout.SOUTH);
    }

    public DefaultTableModel getTableModel() {
        return tableModel;
    }

    public JButton getAddButton() {
        return addButton;
    }

    public JButton getUpdateButton() {
        return updateButton;
    }

    public JButton getDeleteButton() {
        return deleteButton;
    }

    public JButton getCalculateCostButton() {
        return calculateCostButton;
    }

    public JTextField getCarIdField() {
        return carIdField;
    }

    public JTextField getCustomerIdField() {
        return customerIdField;
    }
    
public JXDatePicker getRentalDatePicker() {
    if (rentalDatePicker.getDate() == null) {
        return null;
    }
    
    // Get the date from the original picker and convert to LocalDate
    Date date = rentalDatePicker.getDate();
    LocalDate localDate = date.toInstant()
            .atZone(ZoneId.systemDefault())
            .toLocalDate();
    
    // Convert back to Date and set in the original picker
    Date newDate = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    rentalDatePicker.setDate(newDate);
    
    return rentalDatePicker;
}
    
public JSpinner getRentalTimeSpinner() {
    if (rentalTimeSpinner.getValue() == null) {
        return null;
    }

    Date time = (Date) rentalTimeSpinner.getValue();
    LocalTime rentalTime = time.toInstant()
                             .atZone(ZoneId.systemDefault())
                             .toLocalTime()
                             .truncatedTo(ChronoUnit.MINUTES);

    // Create a new spinner with the time value
    JSpinner newSpinner = new JSpinner(new SpinnerDateModel());
    JSpinner.DateEditor timeEditor = new JSpinner.DateEditor(newSpinner, "HH:mm");
    newSpinner.setEditor(timeEditor);
    newSpinner.setValue(Date.from(rentalTime.atDate(LocalDate.now())
                                          .atZone(ZoneId.systemDefault())
                                          .toInstant()));

    return newSpinner;
}
    

    // Mengambil rental datetime sebagai LocalDateTime
    public LocalDateTime getRentalDateTime() {
        if (rentalDatePicker.getDate() == null || rentalTimeSpinner.getValue() == null) {
            return null;
        }

        Date date = rentalDatePicker.getDate();
        Date time = (Date) rentalTimeSpinner.getValue();

        return LocalDateTime.of(
            date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
            time.toInstant().atZone(ZoneId.systemDefault()).toLocalTime()
                .truncatedTo(ChronoUnit.MINUTES) // This removes seconds
        );
    }
    
public JXDatePicker getReturnDatePicker() {
    if (returnDatePicker.getDate() == null) {
        return null;
    }
    
    // Get the date from the original picker and convert to LocalDate
    Date date = returnDatePicker.getDate();
    LocalDate localDate = date.toInstant()
            .atZone(ZoneId.systemDefault())
            .toLocalDate();
    
    // Convert back to Date and set in the original picker
    Date newDate = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    returnDatePicker.setDate(newDate);
    
    return rentalDatePicker;
}
    
    public JSpinner getReturnTimeSpinner() {
    if (returnTimeSpinner.getValue() == null) {
        return null;
    }

    Date time = (Date) returnTimeSpinner.getValue();
    LocalTime rentalTime = time.toInstant()
                             .atZone(ZoneId.systemDefault())
                             .toLocalTime()
                             .truncatedTo(ChronoUnit.MINUTES);

    // Create a new spinner with the time value
    JSpinner newSpinner = new JSpinner(new SpinnerDateModel());
    JSpinner.DateEditor timeEditor = new JSpinner.DateEditor(newSpinner, "HH:mm");
    newSpinner.setEditor(timeEditor);
    newSpinner.setValue(Date.from(rentalTime.atDate(LocalDate.now())
                                          .atZone(ZoneId.systemDefault())
                                          .toInstant()));

    return newSpinner;
}

    // Mengambil return datetime sebagai LocalDateTime
    public LocalDateTime getReturnDateTime() {
        if (returnDatePicker.getDate() == null || returnTimeSpinner.getValue() == null) {
            return null;
        }

        Date date = returnDatePicker.getDate();
        Date time = (Date) returnTimeSpinner.getValue();

        return LocalDateTime.of(
            date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
            time.toInstant().atZone(ZoneId.systemDefault()).toLocalTime()
                .truncatedTo(ChronoUnit.MINUTES) // This removes seconds
        );
    }

    public JLabel getTotalCostLabel() {
        return totalCostLabel;
    }

    public JTable getRentalTable() {
        return rentalTable;
    }

    public void clearForm() {
        carIdField.setText("");
        customerIdField.setText("");
        rentalDatePicker.setDate(null);
        rentalTimeSpinner.setValue(new java.sql.Time(0)); // 00:00:00
        returnDatePicker.setDate(null);
        returnTimeSpinner.setValue(new java.sql.Time(0)); // 00:00:00
        totalCostLabel.setText("0.00"); // atau "Rp0.00" tergantung kebutuhan
    }
    
    
    // Set rentalDatePicker
    public LocalDate setRentalDatePicker(java.util.Date date) {
        rentalDatePicker.setDate(date);
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }
    
    public void setRentalTimeSpinner(java.sql.Time time) {
        if (time == null) {
            rentalTimeSpinner.setValue(null);
            return;
        }

        // Convert java.sql.Time to LocalTime
        LocalTime localTime = time.toLocalTime();

        // Combine with a dummy date (e.g., today)
        LocalDateTime dummyDateTime = LocalDate.now().atTime(localTime);

        // Convert to java.util.Date
        Date date = Date.from(dummyDateTime.atZone(ZoneId.systemDefault()).toInstant());

        rentalTimeSpinner.setValue(date);
    }

    public LocalDate setReturnDatePicker(java.util.Date date) {
        returnDatePicker.setDate(date);
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }
    
    public void setReturnTimeSpinner(java.sql.Time time) {
        if (time == null) {
            returnTimeSpinner.setValue(null);
            return;
        }

        // Convert java.sql.Time to LocalTime
        LocalTime localTime = time.toLocalTime();

        // Combine with a dummy date (e.g., today)
        LocalDateTime dummyDateTime = LocalDate.now().atTime(localTime);

        // Convert to java.util.Date
        Date date = Date.from(dummyDateTime.atZone(ZoneId.systemDefault()).toInstant());

        returnTimeSpinner.setValue(date);
    }
}

