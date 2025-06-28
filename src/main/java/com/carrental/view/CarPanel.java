package main.java.com.carrental.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class CarPanel extends JPanel {
    private JTable carTable;
    private DefaultTableModel tableModel;
    private JButton addButton, updateButton, deleteButton;
    private JTextField makeField, modelField, yearField, licensePlateField, dailyRateField;
    private JCheckBox availableCheckBox;

    public CarPanel() {
        setLayout(new BorderLayout());

        // Table setup
        tableModel = new DefaultTableModel(new Object[]{"ID", "Make", "Model", "Year", "License Plate", "Daily Rate", "Available"}, 0);
        carTable = new JTable(tableModel);
        add(new JScrollPane(carTable), BorderLayout.CENTER);

        // Form panel
        JPanel formPanel = new JPanel(new GridLayout(7, 2));
        formPanel.setBorder(BorderFactory.createTitledBorder("Car Details"));

        formPanel.add(new JLabel("Make:"));
        makeField = new JTextField();
        formPanel.add(makeField);

        formPanel.add(new JLabel("Model:"));
        modelField = new JTextField();
        formPanel.add(modelField);

        formPanel.add(new JLabel("Year:"));
        yearField = new JTextField();
        formPanel.add(yearField);

        formPanel.add(new JLabel("License Plate:"));
        licensePlateField = new JTextField();
        formPanel.add(licensePlateField);

        formPanel.add(new JLabel("Daily Rate:"));
        dailyRateField = new JTextField();
        formPanel.add(dailyRateField);

        formPanel.add(new JLabel("Available:"));
        availableCheckBox = new JCheckBox();
        formPanel.add(availableCheckBox);

        // Button panel
        JPanel buttonPanel = new JPanel();
        addButton = new JButton("Add Car");
        updateButton = new JButton("Update Car");
        deleteButton = new JButton("Delete Car");
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);

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

    public JTextField getMakeField() {
        return makeField;
    }

    public JTextField getModelField() {
        return modelField;
    }

    public JTextField getYearField() {
        return yearField;
    }

    public JTextField getLicensePlateField() {
        return licensePlateField;
    }

    public JTextField getDailyRateField() {
        return dailyRateField;
    }

    public JCheckBox getAvailableCheckBox() {
        return availableCheckBox;
    }

    public JTable getCarTable() {
        return carTable;
    }

    public void clearForm() {
        makeField.setText("");
        modelField.setText("");
        yearField.setText("");
        licensePlateField.setText("");
        dailyRateField.setText("");
        availableCheckBox.setSelected(false);
    }
}


