package main.java.com.carrental.view;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * The MainFrame class represents the main application window of the Car Rental Management System.
 * It contains tabbed panels for managing cars, customers, and rentals with emoji-based icons.
 */
public class MainFrame extends JFrame {
    private JTabbedPane tabbedPane;
    private CarPanel carPanel;
    private CustomerPanel customerPanel;
    private RentalPanel rentalPanel;
    
    /**
     * Creates an icon from an emoji character for use in tab headers.
     * 
     * @param emoji The emoji character to convert to an icon
     * @return ImageIcon containing the rendered emoji
     */
    private Icon createTabIcon(String emoji) {
        // Create a label with the emoji character
        JLabel emojiLabel = new JLabel(emoji);
        emojiLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 20));
        emojiLabel.setSize(emojiLabel.getPreferredSize());
        
        // Convert the label to a BufferedImage
        BufferedImage image = new BufferedImage(
            emojiLabel.getWidth(),
            emojiLabel.getHeight(),
            BufferedImage.TYPE_INT_ARGB
        );
        Graphics2D g2 = image.createGraphics();
        emojiLabel.paint(g2);
        g2.dispose();
        
        // Return as ImageIcon
        return new ImageIcon(image);
    }

    /**
     * Constructs the main application frame with all UI components.
     */
    public MainFrame() {
        // Apply theme before creating components
        Theme.applyTheme();
        
        // Configure frame properties
        setTitle("Car Rental Management System");
        setSize(900, 650); // Optimized size for content display
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window
        getContentPane().setBackground(Theme.SECONDARY_COLOR);
        setVisible(false); // Hidden initially until after login

        initializeTabbedPane();
        initializeContentPane();
        addWindowActivationListener();
    }

    /**
     * Initializes and configures the tabbed pane with all panels.
     */
    private void initializeTabbedPane() {
        tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        tabbedPane.setFont(Theme.SUBTITLE_FONT);
        tabbedPane.setBackground(Theme.SECONDARY_COLOR);
        tabbedPane.setForeground(Theme.TERTIARY_COLOR);
        
        // Initialize panels
        carPanel = new CarPanel();
        customerPanel = new CustomerPanel();
        rentalPanel = new RentalPanel();

        // Add tabs with emoji icons
        tabbedPane.addTab("Cars", createTabIcon("🚗"), carPanel);
        tabbedPane.addTab("Customers", createTabIcon("👤"), customerPanel);
        tabbedPane.addTab("Rentals", createTabIcon("📝"), rentalPanel);
        
        // Style all tabs
        for (int i = 0; i < tabbedPane.getTabCount(); i++) {
            tabbedPane.setBackgroundAt(i, Theme.SECONDARY_COLOR);
        }
        
        tabbedPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    }

    /**
     * Initializes the main content pane with proper padding and layout.
     */
    private void initializeContentPane() {
        JPanel contentPane = new JPanel(new BorderLayout());
        contentPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        contentPane.setBackground(Theme.SECONDARY_COLOR);
        contentPane.add(tabbedPane, BorderLayout.CENTER);
        setContentPane(contentPane);
    }

    /**
     * Adds window listener to handle theme updates when window is activated.
     */
    private void addWindowActivationListener() {
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowActivated(java.awt.event.WindowEvent evt) {
                SwingUtilities.updateComponentTreeUI(MainFrame.this);
            }
        });
    }

    /**
     * Displays the frame in a thread-safe manner.
     */
    public void showFrame() {
        SwingUtilities.invokeLater(() -> {
            setVisible(true);
            toFront();
            repaint();
        });
    }

    // Panel accessor methods
    
    /**
     * @return The CarPanel instance
     */
    public CarPanel getCarPanel() {
        return carPanel;
    }

    /**
     * @return The CustomerPanel instance
     */
    public CustomerPanel getCustomerPanel() {
        return customerPanel;
    }

    /**
     * @return The RentalPanel instance
     */
    public RentalPanel getRentalPanel() {
        return rentalPanel;
    }
}