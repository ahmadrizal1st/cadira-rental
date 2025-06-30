package main.java.com.carrental.view;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

/**
 * The Theme class provides a centralized location for all UI styling and theming
 * configurations used throughout the car rental application. It includes color
 * definitions, typography settings, border styles, and component dimensions.
 */
public class Theme {
    // ========== COLOR PALETTE ==========
    /** Primary brand color (red) */
    public static final Color PRIMARY_COLOR = new Color(211, 16, 31);   // #d3101f
    
    /** Secondary color (white) */
    public static final Color SECONDARY_COLOR = new Color(255, 255, 255); // #ffffff
    
    /** Tertiary color (dark gray) */
    public static final Color TERTIARY_COLOR = new Color(25, 20, 20);    // #191414
    
    /** Support color (light gray) */
    public static final Color SUPPORT_COLOR = new Color(217, 217, 217);  // #d9d9d9
    
    // ========== CUSTOM COLORS ==========
    /** Light red color used for selections */
    public static final Color LIGHT_RED = new Color(255, 230, 230);
    
    /** Dark gray color for disabled states */
    public static final Color DARK_GRAY = new Color(60, 60, 60);
    
    /** Color for disabled components */
    public static final Color DISABLED_COLOR = new Color(150, 150, 150);
    
    // ========== TYPOGRAPHY ==========
    private static final String FONT_FAMILY = "Segoe UI";
    
    /** Font for main titles */
    public static final Font TITLE_FONT = new Font(FONT_FAMILY, Font.BOLD, 24);
    
    /** Font for subtitles */
    public static final Font SUBTITLE_FONT = new Font(FONT_FAMILY, Font.BOLD, 18);
    
    /** Font for regular body text */
    public static final Font BODY_FONT = new Font(FONT_FAMILY, Font.PLAIN, 14);
    
    /** Font for buttons */
    public static final Font BUTTON_FONT = new Font(FONT_FAMILY, Font.BOLD, 14);
    
    /** Font for menu items */
    public static final Font MENU_FONT = new Font(FONT_FAMILY, Font.PLAIN, 14);
    
    /** Font for menu headers */
    public static final Font MENU_HEADER_FONT = new Font(FONT_FAMILY, Font.BOLD, 14);
    
    // ========== BORDERS ==========
    /** Rounded border with primary color */
    public static final Border ROUNDED_BORDER = new RoundBorder(PRIMARY_COLOR, 12, 2);
    
    /** Border for input fields */
    public static final Border INPUT_BORDER = new CompoundBorder(
            new RoundBorder(SUPPORT_COLOR, 8, 1),
            new EmptyBorder(5, 10, 5, 10)
    );
    
    /** Border for panels */
    public static final Border PANEL_BORDER = new CompoundBorder(
            new EmptyBorder(10, 10, 10, 10),
            new MatteBorder(1, 0, 0, 0, SUPPORT_COLOR)
    );
    
    /** Border for toolbars */
    public static final Border TOOLBAR_BORDER = new CompoundBorder(
            new MatteBorder(0, 0, 1, 0, SUPPORT_COLOR),
            new EmptyBorder(5, 5, 5, 5)
    );
    
    /** Border for internal frames */
    public static final Border INTERNAL_FRAME_BORDER = new CompoundBorder(
            new LineBorder(SUPPORT_COLOR, 1),
            new EmptyBorder(5, 5, 5, 5)
    );
    
    // ========== DIMENSIONS ==========
    /** Default size for input fields */
    public static final Dimension DEFAULT_FIELD_SIZE = new Dimension(200, 30);
    
    /** Default size for buttons */
    public static final Dimension DEFAULT_BUTTON_SIZE = new Dimension(100, 30);
    
    /** Default size for toolbar buttons */
    public static final Dimension DEFAULT_TOOLBAR_BUTTON_SIZE = new Dimension(40, 40);
    
    /**
     * Applies the custom theme to all Swing components in the application.
     * This method configures the look and feel and sets UI defaults for various components.
     */
    public static void applyTheme() {
        try {
            // Set cross-platform look and feel for consistency
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            
            // ===== Swing Containers =====
            // Panel
            UIManager.put("Panel.background", SECONDARY_COLOR);
            UIManager.put("Panel.font", BODY_FONT);
            
            // Split Pane
            UIManager.put("SplitPane.background", SECONDARY_COLOR);
            UIManager.put("SplitPane.dividerSize", 8);
            UIManager.put("SplitPane.dividerFocusColor", PRIMARY_COLOR);
            UIManager.put("SplitPane.dividerSize", 5);
            UIManager.put("SplitPane.border", BorderFactory.createEmptyBorder());
            
            // Tool Bar
            UIManager.put("ToolBar.background", SECONDARY_COLOR);
            UIManager.put("ToolBar.border", TOOLBAR_BORDER);
            UIManager.put("ToolBar.font", BODY_FONT);
            
            // Internal Frame
            UIManager.put("InternalFrame.background", SECONDARY_COLOR);
            UIManager.put("InternalFrame.border", INTERNAL_FRAME_BORDER);
            UIManager.put("InternalFrame.titleFont", SUBTITLE_FONT);
            UIManager.put("InternalFrame.activeTitleBackground", PRIMARY_COLOR);
            UIManager.put("InternalFrame.activeTitleForeground", SECONDARY_COLOR);
            UIManager.put("InternalFrame.inactiveTitleBackground", SUPPORT_COLOR);
            UIManager.put("InternalFrame.inactiveTitleForeground", TERTIARY_COLOR);
            
            // ===== Swing Controls =====
            // Label
            UIManager.put("Label.foreground", TERTIARY_COLOR);
            UIManager.put("Label.font", BODY_FONT);
            UIManager.put("Label.disabledForeground", DISABLED_COLOR);
            
            // Toggle Button
            UIManager.put("ToggleButton.background", SECONDARY_COLOR);
            UIManager.put("ToggleButton.foreground", TERTIARY_COLOR);
            UIManager.put("ToggleButton.font", BUTTON_FONT);
            UIManager.put("ToggleButton.border", ROUNDED_BORDER);
            UIManager.put("ToggleButton.select", PRIMARY_COLOR);
            
            // Radio Button
            UIManager.put("RadioButton.background", SECONDARY_COLOR);
            UIManager.put("RadioButton.foreground", TERTIARY_COLOR);
            UIManager.put("RadioButton.font", BODY_FONT);
            UIManager.put("RadioButton.icon", new RadioButtonIcon());
            
            // Combo Box
            UIManager.put("ComboBox.background", SECONDARY_COLOR);
            UIManager.put("ComboBox.foreground", TERTIARY_COLOR);
            UIManager.put("ComboBox.font", BODY_FONT);
            UIManager.put("ComboBox.border", INPUT_BORDER);
            UIManager.put("ComboBox.buttonBackground", PRIMARY_COLOR);
            UIManager.put("ComboBox.buttonHighlight", PRIMARY_COLOR.brighter());
            UIManager.put("ComboBox.selectionBackground", LIGHT_RED);
            UIManager.put("ComboBox.selectionForeground", TERTIARY_COLOR);
            
            // Text Field
            UIManager.put("TextField.background", SECONDARY_COLOR);
            UIManager.put("TextField.foreground", TERTIARY_COLOR);
            UIManager.put("TextField.font", BODY_FONT);
            UIManager.put("TextField.border", INPUT_BORDER);
            UIManager.put("TextField.caretForeground", PRIMARY_COLOR);
            UIManager.put("TextField.inactiveForeground", DISABLED_COLOR);
            
            // Scroll Bar
            UIManager.put("ScrollBar.background", SECONDARY_COLOR);
            UIManager.put("ScrollBar.thumb", SUPPORT_COLOR);
            UIManager.put("ScrollBar.track", SECONDARY_COLOR);
            UIManager.put("ScrollBar.width", 12);
            UIManager.put("ScrollBar.thumbDarkShadow", PRIMARY_COLOR);
            UIManager.put("ScrollBar.thumbHighlight", PRIMARY_COLOR.brighter());
            
            // Progress Bar
            UIManager.put("ProgressBar.background", SECONDARY_COLOR);
            UIManager.put("ProgressBar.foreground", PRIMARY_COLOR);
            UIManager.put("ProgressBar.border", BorderFactory.createEmptyBorder());
            UIManager.put("ProgressBar.font", BODY_FONT);
            UIManager.put("ProgressBar.selectionBackground", SECONDARY_COLOR);
            UIManager.put("ProgressBar.selectionForeground", TERTIARY_COLOR);
            
            // Password Field
            UIManager.put("PasswordField.background", SECONDARY_COLOR);
            UIManager.put("PasswordField.foreground", TERTIARY_COLOR);
            UIManager.put("PasswordField.font", BODY_FONT);
            UIManager.put("PasswordField.border", INPUT_BORDER);
            UIManager.put("PasswordField.caretForeground", PRIMARY_COLOR);
            
            // Separator
            UIManager.put("Separator.background", SUPPORT_COLOR);
            UIManager.put("Separator.foreground", SUPPORT_COLOR);
            
            // Editor Pane
            UIManager.put("EditorPane.background", SECONDARY_COLOR);
            UIManager.put("EditorPane.foreground", TERTIARY_COLOR);
            UIManager.put("EditorPane.font", BODY_FONT);
            UIManager.put("EditorPane.border", INPUT_BORDER);
            UIManager.put("EditorPane.caretForeground", PRIMARY_COLOR);
            UIManager.put("EditorPane.inactiveForeground", DISABLED_COLOR);
            UIManager.put("EditorPane.selectionBackground", LIGHT_RED);
            UIManager.put("EditorPane.selectionForeground", TERTIARY_COLOR);
            
            // Table
            UIManager.put("Table.background", SECONDARY_COLOR);
            UIManager.put("Table.foreground", TERTIARY_COLOR);
            UIManager.put("Table.gridColor", SUPPORT_COLOR);
            UIManager.put("Table.selectionBackground", LIGHT_RED);
            UIManager.put("Table.selectionForeground", TERTIARY_COLOR);
            UIManager.put("Table.font", BODY_FONT);
            UIManager.put("TableHeader.background", PRIMARY_COLOR);
            UIManager.put("TableHeader.foreground", SECONDARY_COLOR);
            UIManager.put("TableHeader.font", BUTTON_FONT);
            UIManager.put("TableHeader.cellBorder", BorderFactory.createEmptyBorder(5, 5, 5, 5));
            
            // ===== Swing Menus =====
            // Menu Bar
            UIManager.put("MenuBar.background", SECONDARY_COLOR);
            UIManager.put("MenuBar.border", new MatteBorder(0, 0, 1, 0, SUPPORT_COLOR));
            UIManager.put("MenuBar.font", MENU_HEADER_FONT);
            
            // Menu
            UIManager.put("Menu.background", SECONDARY_COLOR);
            UIManager.put("Menu.foreground", TERTIARY_COLOR);
            UIManager.put("Menu.font", MENU_FONT);
            UIManager.put("Menu.border", BorderFactory.createEmptyBorder(5, 5, 5, 5));
            UIManager.put("Menu.selectionBackground", PRIMARY_COLOR);
            UIManager.put("Menu.selectionForeground", SECONDARY_COLOR);
            
            // Menu Item
            UIManager.put("MenuItem.background", SECONDARY_COLOR);
            UIManager.put("MenuItem.foreground", TERTIARY_COLOR);
            UIManager.put("MenuItem.font", MENU_FONT);
            UIManager.put("MenuItem.border", BorderFactory.createEmptyBorder(5, 5, 5, 5));
            UIManager.put("MenuItem.selectionBackground", PRIMARY_COLOR);
            UIManager.put("MenuItem.selectionForeground", SECONDARY_COLOR);
            
            // CheckBox Menu Item
            UIManager.put("CheckBoxMenuItem.background", SECONDARY_COLOR);
            UIManager.put("CheckBoxMenuItem.foreground", TERTIARY_COLOR);
            UIManager.put("CheckBoxMenuItem.font", MENU_FONT);
            UIManager.put("CheckBoxMenuItem.border", BorderFactory.createEmptyBorder(5, 5, 5, 5));
            UIManager.put("CheckBoxMenuItem.selectionBackground", PRIMARY_COLOR);
            UIManager.put("CheckBoxMenuItem.selectionForeground", SECONDARY_COLOR);
            UIManager.put("CheckBoxMenuItem.checkIcon", new CheckBoxMenuItemIcon());
            
            // RadioButton Menu Item
            UIManager.put("RadioButtonMenuItem.background", SECONDARY_COLOR);
            UIManager.put("RadioButtonMenuItem.foreground", TERTIARY_COLOR);
            UIManager.put("RadioButtonMenuItem.font", MENU_FONT);
            UIManager.put("RadioButtonMenuItem.border", BorderFactory.createEmptyBorder(5, 5, 5, 5));
            UIManager.put("RadioButtonMenuItem.selectionBackground", PRIMARY_COLOR);
            UIManager.put("RadioButtonMenuItem.selectionForeground", SECONDARY_COLOR);
            UIManager.put("RadioButtonMenuItem.checkIcon", new RadioButtonMenuItemIcon());
            
            // Popup Menu
            UIManager.put("PopupMenu.background", SECONDARY_COLOR);
            UIManager.put("PopupMenu.border", new LineBorder(SUPPORT_COLOR, 1));
            UIManager.put("PopupMenu.font", MENU_FONT);
            
            // Menu Separator
            UIManager.put("PopupMenuSeparator.background", SUPPORT_COLOR);
            UIManager.put("PopupMenuSeparator.foreground", SUPPORT_COLOR);
            
            // ===== Swing Windows =====
            UIManager.put("Window.background", SECONDARY_COLOR);
            UIManager.put("Window.titleFont", TITLE_FONT);
            
            // Option Pane
            UIManager.put("OptionPane.background", SECONDARY_COLOR);
            UIManager.put("OptionPane.messageFont", BODY_FONT);
            UIManager.put("OptionPane.buttonFont", BUTTON_FONT);
            UIManager.put("OptionPane.border", new EmptyBorder(10, 10, 10, 10));
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Custom border implementation that creates rounded rectangles.
     * This is used for buttons, input fields, and other components that need rounded corners.
     */
    private static class RoundBorder extends AbstractBorder {
        private final Color color;
        private final int radius;
        private final int thickness;
        
        /**
         * Creates a new RoundBorder with specified parameters.
         * 
         * @param color The border color
         * @param radius The corner radius
         * @param thickness The border thickness
         */
        public RoundBorder(Color color, int radius, int thickness) {
            this.color = color;
            this.radius = radius;
            this.thickness = thickness;
        }
        
        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(color);
            g2.setStroke(new BasicStroke(thickness));
            g2.draw(new RoundRectangle2D.Double(x, y, width-1, height-1, radius, radius));
            g2.dispose();
        }
        
        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(radius/2, radius/2, radius/2, radius/2);
        }
        
        @Override
        public Insets getBorderInsets(Component c, Insets insets) {
            insets.left = insets.right = radius/2;
            insets.top = insets.bottom = radius/2;
            return insets;
        }
    }
    
    /**
     * Custom icon implementation for radio buttons.
     * This provides a consistent look for radio buttons throughout the application.
     */
    private static class RadioButtonIcon implements Icon {
        private static final int SIZE = 14;
        
        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            AbstractButton button = (AbstractButton) c;
            ButtonModel model = button.getModel();
            
            // Outer circle
            g2.setColor(model.isEnabled() ? Theme.TERTIARY_COLOR : Theme.DISABLED_COLOR);
            g2.drawOval(x, y, SIZE-1, SIZE-1);
            
            // Inner filled circle when selected
            if (model.isSelected()) {
                g2.setColor(Theme.PRIMARY_COLOR);
                g2.fillOval(x+3, y+3, SIZE-7, SIZE-7);
            }
            
            g2.dispose();
        }
        
        @Override
        public int getIconWidth() { return SIZE; }
        
        @Override
        public int getIconHeight() { return SIZE; }
    }
    
    /**
     * Custom icon implementation for checkbox menu items.
     * This provides a consistent look for checkbox items in menus.
     */
    private static class CheckBoxMenuItemIcon implements Icon {
        private static final int SIZE = 14;
        
        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            AbstractButton button = (AbstractButton) c;
            ButtonModel model = button.getModel();
            
            // Border
            g2.setColor(model.isEnabled() ? Theme.TERTIARY_COLOR : Theme.DISABLED_COLOR);
            g2.drawRect(x, y, SIZE-1, SIZE-1);
            
            // Check mark when selected
            if (model.isSelected()) {
                g2.setColor(Theme.PRIMARY_COLOR);
                g2.fillRect(x+3, y+3, SIZE-6, SIZE-6);
            }
            
            g2.dispose();
        }
        
        @Override
        public int getIconWidth() { return SIZE; }
        
        @Override
        public int getIconHeight() { return SIZE; }
    }
    
    /**
     * Custom icon implementation for radio button menu items.
     * This provides a consistent look for radio button items in menus.
     */
    private static class RadioButtonMenuItemIcon implements Icon {
        private static final int SIZE = 14;
        
        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            AbstractButton button = (AbstractButton) c;
            ButtonModel model = button.getModel();
            
            // Outer circle
            g2.setColor(model.isEnabled() ? Theme.TERTIARY_COLOR : Theme.DISABLED_COLOR);
            g2.drawOval(x, y, SIZE-1, SIZE-1);
            
            // Inner filled circle when selected
            if (model.isSelected()) {
                g2.setColor(Theme.PRIMARY_COLOR);
                g2.fillOval(x+3, y+3, SIZE-7, SIZE-7);
            }
            
            g2.dispose();
        }
        
        @Override
        public int getIconWidth() { return SIZE; }
        
        @Override
        public int getIconHeight() { return SIZE; }
    }
}