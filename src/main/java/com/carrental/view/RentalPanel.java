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
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.border.TitledBorder;

public class RentalPanel extends JPanel {
    private JTable rentalTable;
    private DefaultTableModel tableModel;
    private JButton addButton, updateButton, deleteButton, calculateCostButton;
    private JTextField carIdField, customerIdField;
    private JXDatePicker rentalDatePicker, returnDatePicker;  // JXDatePicker digunakan di sini
    private JSpinner rentalTimeSpinner, returnTimeSpinner;
    private JLabel totalCostLabel;

    

    public RentalPanel() {
        // Apply theme before creating components
        Theme.applyTheme();
        
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setBackground(Theme.SECONDARY_COLOR);

        // Table setup
        tableModel = new DefaultTableModel(new Object[]{"ID", "Car", "Customer", "Rental Date", "Return Date", "Total Cost"}, 0);
        rentalTable = new JTable(tableModel);
        rentalTable.setRowHeight(25);
        rentalTable.setFillsViewportHeight(true);
        rentalTable.setSelectionBackground(Theme.LIGHT_RED);
        rentalTable.setSelectionForeground(Theme.TERTIARY_COLOR);
        
        JScrollPane tableScroll = new JScrollPane(rentalTable);
        tableScroll.setBorder(Theme.PANEL_BORDER);
        add(tableScroll, BorderLayout.CENTER);

        // Form panel
        JPanel formPanel = new JPanel(new GridLayout(6, 2, 10, 10));
        formPanel.setBackground(Theme.SECONDARY_COLOR);
        formPanel.setBorder(
            BorderFactory.createTitledBorder(
                BorderFactory.createCompoundBorder(
                    new MatteBorder(1, 0, 0, 0, Theme.SUPPORT_COLOR),
                    new EmptyBorder(5, 5, 5, 5)
                ),
                "Rental Details",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                Theme.SUBTITLE_FONT,
                Theme.PRIMARY_COLOR
            )
        );

        addFormField(formPanel, "Car ID:", carIdField = new JTextField());
        addFormField(formPanel, "Customer ID:", customerIdField = new JTextField());

        // Rental Date/Time
        JLabel rentalLabel = new JLabel("Rental Datetime:");
        rentalLabel.setFont(Theme.BODY_FONT);
        formPanel.add(rentalLabel);
        
        JPanel rentalDateTimePanel = new JPanel(new BorderLayout(5, 0));
        rentalDateTimePanel.setBackground(Theme.SECONDARY_COLOR);
        
        rentalDatePicker = new JXDatePicker();
        styleDatePicker(rentalDatePicker);
        rentalDateTimePanel.add(rentalDatePicker, BorderLayout.CENTER);
        
        rentalTimeSpinner = new JSpinner(new SpinnerDateModel(new Date(), null, null, java.util.Calendar.HOUR_OF_DAY));
        JSpinner.DateEditor rentalEditor = new JSpinner.DateEditor(rentalTimeSpinner, "HH:mm");
        styleTimeSpinner(rentalTimeSpinner, rentalEditor);
        rentalDateTimePanel.add(rentalTimeSpinner, BorderLayout.EAST);
        formPanel.add(rentalDateTimePanel);

        // Return Date/Time
        JLabel returnLabel = new JLabel("Return Datetime:");
        returnLabel.setFont(Theme.BODY_FONT);
        formPanel.add(returnLabel);
        
        JPanel returnDateTimePanel = new JPanel(new BorderLayout(5, 0));
        returnDateTimePanel.setBackground(Theme.SECONDARY_COLOR);
        
        returnDatePicker = new JXDatePicker();
        styleDatePicker(returnDatePicker);
        returnDateTimePanel.add(returnDatePicker, BorderLayout.CENTER);
        
        returnTimeSpinner = new JSpinner(new SpinnerDateModel(new Date(), null, null, java.util.Calendar.HOUR_OF_DAY));
        JSpinner.DateEditor returnEditor = new JSpinner.DateEditor(returnTimeSpinner, "HH:mm");
        styleTimeSpinner(returnTimeSpinner, returnEditor);
        returnDateTimePanel.add(returnTimeSpinner, BorderLayout.EAST);
        formPanel.add(returnDateTimePanel);

        // Total Cost
        JLabel costLabel = new JLabel("Total Cost:");
        costLabel.setFont(Theme.BODY_FONT);
        formPanel.add(costLabel);
        
        totalCostLabel = new JLabel("0.00");
        totalCostLabel.setFont(Theme.BODY_FONT);
        totalCostLabel.setBorder(BorderFactory.createCompoundBorder(
            new MatteBorder(1, 1, 1, 1, Theme.SUPPORT_COLOR),
            new EmptyBorder(5, 10, 5, 10)
        ));
        formPanel.add(totalCostLabel);

        // Button panel
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

        JPanel southPanel = new JPanel(new BorderLayout(10, 10));
        southPanel.setBorder(Theme.PANEL_BORDER);
        southPanel.setBackground(Theme.SECONDARY_COLOR);
        southPanel.add(formPanel, BorderLayout.CENTER);
        southPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(southPanel, BorderLayout.SOUTH);
    }

    private void addFormField(JPanel panel, String labelText, JTextField field) {
        JLabel label = new JLabel(labelText);
        label.setFont(Theme.BODY_FONT);
        panel.add(label);
        
        field.setFont(Theme.BODY_FONT);
        field.setBorder(Theme.INPUT_BORDER);
        field.setPreferredSize(Theme.DEFAULT_FIELD_SIZE);
        panel.add(field);
    }

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

    private void styleDatePicker(JXDatePicker picker) {
        picker.setFormats("dd/MM/yyyy");
        picker.getEditor().setFont(Theme.BODY_FONT);
        picker.getEditor().setBorder(Theme.INPUT_BORDER);
        picker.getEditor().setBackground(Theme.SECONDARY_COLOR);
        picker.getEditor().setForeground(Theme.TERTIARY_COLOR);
        picker.setBorder(BorderFactory.createEmptyBorder());
    }

    private void styleTimeSpinner(JSpinner spinner, JSpinner.DateEditor editor) {
        spinner.setFont(Theme.BODY_FONT);
        spinner.setBorder(Theme.INPUT_BORDER);
        spinner.setBackground(Theme.SECONDARY_COLOR);
        spinner.setForeground(Theme.TERTIARY_COLOR);
        editor.getTextField().setBorder(BorderFactory.createEmptyBorder());
        editor.getTextField().setBackground(Theme.SECONDARY_COLOR);
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

