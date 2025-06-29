
package main.java.com.carrental.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.border.TitledBorder;

public class CustomerPanel extends JPanel {
    private JTable customerTable;
    private DefaultTableModel tableModel;
    private JButton addButton, updateButton, deleteButton;
    private JTextField firstNameField, lastNameField, emailField, phoneField;

    public CustomerPanel() {
        // Apply theme before creating components
        Theme.applyTheme();
        
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setBackground(Theme.SECONDARY_COLOR);

        // Table setup
        tableModel = new DefaultTableModel(new Object[]{"ID", "First Name", "Last Name", "Email", "Phone"}, 0);
        customerTable = new JTable(tableModel);
        customerTable.setRowHeight(25);
        customerTable.setFillsViewportHeight(true);
        customerTable.setSelectionBackground(Theme.LIGHT_RED);
        customerTable.setSelectionForeground(Theme.TERTIARY_COLOR);
        
        JScrollPane tableScroll = new JScrollPane(customerTable);
        tableScroll.setBorder(Theme.PANEL_BORDER);
        add(tableScroll, BorderLayout.CENTER);

        // Form panel
        JPanel formPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        formPanel.setBackground(Theme.SECONDARY_COLOR);
        formPanel.setBorder(
            BorderFactory.createTitledBorder(
                BorderFactory.createCompoundBorder(
                    new MatteBorder(1, 0, 0, 0, Theme.SUPPORT_COLOR),
                    new EmptyBorder(5, 5, 5, 5)
                ),
                "Customer Details",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                Theme.SUBTITLE_FONT,
                Theme.PRIMARY_COLOR
            )
        );

        addFormField(formPanel, "First Name:", firstNameField = new JTextField());
        addFormField(formPanel, "Last Name:", lastNameField = new JTextField());
        addFormField(formPanel, "Email:", emailField = new JTextField());
        addFormField(formPanel, "Phone:", phoneField = new JTextField());

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setBackground(Theme.SECONDARY_COLOR);
        
        addButton = createStyledButton("Add Customer");
        updateButton = createStyledButton("Update Customer");
        deleteButton = createStyledButton("Delete Customer");
        
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);

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
        
        // Hover effects
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

    public JTextField getFirstNameField() {
        return firstNameField;
    }

    public JTextField getLastNameField() {
        return lastNameField;
    }

    public JTextField getEmailField() {
        return emailField;
    }

    public JTextField getPhoneField() {
        return phoneField;
    }

    public JTable getCustomerTable() {
        return customerTable;
    }

    public void clearForm() {
        firstNameField.setText("");
        lastNameField.setText("");
        emailField.setText("");
        phoneField.setText("");
    }
}


