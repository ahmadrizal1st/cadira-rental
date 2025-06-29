package main.java.com.carrental.view;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class MainFrame extends JFrame {

    private JTabbedPane tabbedPane;
    private CarPanel carPanel;
    private CustomerPanel customerPanel;
    private RentalPanel rentalPanel;
    
    private Icon createTabIcon(String emoji) {
        // 1. Buat JLabel dengan emoji
        JLabel emojiLabel = new JLabel(emoji);
        emojiLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 20));
        emojiLabel.setSize(emojiLabel.getPreferredSize());
        
        // 2. Konversi JLabel ke BufferedImage
        BufferedImage image = new BufferedImage(
            emojiLabel.getWidth(),
            emojiLabel.getHeight(),
            BufferedImage.TYPE_INT_ARGB
        );
        Graphics2D g2 = image.createGraphics();
        emojiLabel.paint(g2);
        g2.dispose();
        
        // 3. Kembalikan sebagai ImageIcon
        return new ImageIcon(image);
    }

    public MainFrame() {
        // Apply theme before creating components
        Theme.applyTheme();
        
        setTitle("Car Rental Management System");
        setSize(900, 650); // Slightly larger for better content display
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window
        getContentPane().setBackground(Theme.SECONDARY_COLOR);
        setVisible(false); // Set to false initially

        // Configure tabbed pane with theme
        tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        tabbedPane.setFont(Theme.SUBTITLE_FONT);
        tabbedPane.setBackground(Theme.SECONDARY_COLOR);
        tabbedPane.setForeground(Theme.TERTIARY_COLOR);
        
        // Create panels
        carPanel = new CarPanel();
        customerPanel = new CustomerPanel();
        rentalPanel = new RentalPanel();

        // Add tabs with icons and proper spacing
        tabbedPane.addTab("Cars", createTabIcon("🚗"), carPanel);
        tabbedPane.addTab("Customers", createTabIcon("👤"), customerPanel);
        tabbedPane.addTab("Rentals", createTabIcon("📝"), rentalPanel);
        
        // Style selected tab
        tabbedPane.setBackgroundAt(0, Theme.SECONDARY_COLOR);
        tabbedPane.setBackgroundAt(1, Theme.SECONDARY_COLOR);
        tabbedPane.setBackgroundAt(2, Theme.SECONDARY_COLOR);
        
        tabbedPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        // Add tabbed pane to frame with padding
        JPanel contentPane = new JPanel(new BorderLayout());
        contentPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        contentPane.setBackground(Theme.SECONDARY_COLOR);
        contentPane.add(tabbedPane, BorderLayout.CENTER);
        
        setContentPane(contentPane);

        // Add window listener to handle theming on activation
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                SwingUtilities.updateComponentTreeUI(MainFrame.this);
            }
        });
    }


    public void showFrame() {
        SwingUtilities.invokeLater(() -> {
            setVisible(true);
            toFront();
            repaint();
        });
    }

    public CarPanel getCarPanel() {
        return carPanel;
    }

    public CustomerPanel getCustomerPanel() {
        return customerPanel;
    }

    public RentalPanel getRentalPanel() {
        return rentalPanel;
    }

    // Removed main method as App.java will handle visibility after login
}


