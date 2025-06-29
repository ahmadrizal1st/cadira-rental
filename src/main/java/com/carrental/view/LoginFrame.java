
package main.java.com.carrental.view;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;

public class LoginFrame extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;



    public LoginFrame() {
        // Terapkan theme sebelum membuat komponen
        Theme.applyTheme();
        
        setTitle("Login");
        setSize(350, 200); // Sedikit lebih besar untuk tampilan yang lebih baik
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window
        setResizable(false);
        getContentPane().setBackground(Theme.SECONDARY_COLOR);

        // Panel utama dengan padding dan layout
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Theme.SECONDARY_COLOR);
        panel.setBorder(BorderFactory.createCompoundBorder(
            new MatteBorder(1, 1, 1, 1, Theme.SUPPORT_COLOR),
            new EmptyBorder(20, 20, 20, 20)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 10, 5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Label dan field username
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(Theme.BODY_FONT);
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(usernameLabel, gbc);

        usernameField = new JTextField();
        usernameField.setFont(Theme.BODY_FONT);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        panel.add(usernameField, gbc);

        // Label dan field password
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(Theme.BODY_FONT);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.0;
        panel.add(passwordLabel, gbc);

        passwordField = new JPasswordField();
        passwordField.setFont(Theme.BODY_FONT);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        panel.add(passwordField, gbc);

        // Tombol login
        loginButton = new JButton("Login");
        loginButton.setFont(Theme.BUTTON_FONT);
        loginButton.setBackground(Theme.PRIMARY_COLOR);
        loginButton.setForeground(Theme.SECONDARY_COLOR);
        loginButton.setFocusPainted(false);
        loginButton.setPreferredSize(new Dimension(100, 30));
        
        // Panel untuk tombol agar bisa di-center
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(Theme.SECONDARY_COLOR);
        buttonPanel.add(loginButton);
        
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.CENTER;
        panel.add(buttonPanel, gbc);

        add(panel, BorderLayout.CENTER);

        // Tambahkan hover effect untuk tombol
        loginButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                loginButton.setBackground(Theme.PRIMARY_COLOR.brighter());
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                loginButton.setBackground(Theme.PRIMARY_COLOR);
            }
        });
    }

    public String getUsername() {
        return usernameField.getText();
    }

    public String getPassword() {
        return new String(passwordField.getPassword());
    }

    public JButton getLoginButton() {
        return loginButton;
    }

    public void clearFields() {
        usernameField.setText("");
        passwordField.setText("");
    }
}

