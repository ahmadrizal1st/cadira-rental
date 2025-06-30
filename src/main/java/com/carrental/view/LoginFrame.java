package main.java.com.carrental.view;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;

/**
 * The LoginFrame class represents the login window for the car rental system.
 * It provides a user interface for authentication with username and password fields.
 */
public class LoginFrame extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;

    /**
     * Constructs a new LoginFrame with all UI components initialized.
     */
    public LoginFrame() {
        // Apply theme before creating components
        Theme.applyTheme();
        
        // Configure frame properties
        setTitle("Login");
        setSize(350, 200); // Slightly larger for better appearance
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window
        setResizable(false);
        getContentPane().setBackground(Theme.SECONDARY_COLOR);

        // Initialize and configure main panel
        JPanel panel = createMainPanel();
        add(panel, BorderLayout.CENTER);
    }

    /**
     * Creates and configures the main panel with all login components.
     * 
     * @return The configured JPanel containing login form elements
     */
    private JPanel createMainPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Theme.SECONDARY_COLOR);
        panel.setBorder(createPanelBorder());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 10, 5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Add username components
        addFormField(panel, gbc, "Username:", usernameField = new JTextField(), 0);
        
        // Add password components
        addFormField(panel, gbc, "Password:", passwordField = new JPasswordField(), 1);
        
        // Add login button
        addLoginButton(panel, gbc);

        return panel;
    }

    /**
     * Creates a styled border for the main panel.
     */
    private Border createPanelBorder() {
        return BorderFactory.createCompoundBorder(
            new MatteBorder(1, 1, 1, 1, Theme.SUPPORT_COLOR),
            new EmptyBorder(20, 20, 20, 20)
        );
    }

    /**
     * Adds a form field to the panel with proper styling and layout.
     * 
     * @param panel The panel to add the field to
     * @param gbc The GridBagConstraints for layout
     * @param labelText The text for the field label
     * @param field The text field component
     * @param gridY The y-position in the grid
     */
    private void addFormField(JPanel panel, GridBagConstraints gbc, String labelText, JComponent field, int gridY) {
        JLabel label = new JLabel(labelText);
        label.setFont(Theme.BODY_FONT);
        gbc.gridx = 0;
        gbc.gridy = gridY;
        gbc.weightx = 0.0;
        panel.add(label, gbc);

        field.setFont(Theme.BODY_FONT);
        gbc.gridx = 1;
        gbc.gridy = gridY;
        gbc.weightx = 1.0;
        panel.add(field, gbc);
    }

    /**
     * Creates and adds the login button to the panel.
     * 
     * @param panel The panel to add the button to
     * @param gbc The GridBagConstraints for layout
     */
    private void addLoginButton(JPanel panel, GridBagConstraints gbc) {
        loginButton = createStyledButton("Login");
        
        // Panel for button to enable centering
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(Theme.SECONDARY_COLOR);
        buttonPanel.add(loginButton);
        
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.CENTER;
        panel.add(buttonPanel, gbc);

        // Add hover effects
        addButtonHoverEffect(loginButton);
    }

    /**
     * Creates a styled button with consistent appearance.
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
        button.setPreferredSize(new Dimension(100, 30));
        return button;
    }

    /**
     * Adds hover effects to a button.
     * 
     * @param button The button to add effects to
     */
    private void addButtonHoverEffect(JButton button) {
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(Theme.PRIMARY_COLOR.brighter());
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(Theme.PRIMARY_COLOR);
            }
        });
    }

    // Getters for form data and components
    
    /**
     * @return The entered username text
     */
    public String getUsername() {
        return usernameField.getText();
    }

    /**
     * @return The entered password text
     */
    public String getPassword() {
        return new String(passwordField.getPassword());
    }

    /**
     * @return The login button component
     */
    public JButton getLoginButton() {
        return loginButton;
    }

    /**
     * Clears all input fields in the form.
     */
    public void clearFields() {
        usernameField.setText("");
        passwordField.setText("");
    }
}