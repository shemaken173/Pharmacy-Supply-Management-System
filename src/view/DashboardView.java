package view;

import controller.MedicineController;
import model.User;
import model.Medicine;
import utils.MessageHelper;
import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Dashboard View - Page 2
 * Main navigation hub for the application
 * Color Scheme: Blue and Green only
 */
public class DashboardView extends JFrame {
    private User currentUser;
    private MedicineController medicineController;
    private JLabel lblWelcome, lblTotalMedicines, lblLowStock;

    public DashboardView(User user) {
        this.currentUser = user;
        this.medicineController = new MedicineController();
        initComponents();
        loadDashboardData();
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        setTitle("Dashboard - Pharmacy Supply Management System");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        // Main Panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBackground(new Color(245, 245, 245));

        // Header Panel - Blue
        JPanel headerPanel = new JPanel();
        headerPanel.setBounds(0, 0, 900, 80);
        headerPanel.setBackground(new Color(0, 102, 204)); // Blue
        headerPanel.setLayout(null);

        JLabel lblTitle = new JLabel("PHARMACY DASHBOARD");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 26));
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setBounds(20, 15, 400, 30);
        headerPanel.add(lblTitle);

        lblWelcome = new JLabel("Welcome, " + currentUser.getFullName());
        lblWelcome.setFont(new Font("Arial", Font.PLAIN, 14));
        lblWelcome.setForeground(Color.WHITE);
        lblWelcome.setBounds(20, 45, 400, 20);
        headerPanel.add(lblWelcome);

        JButton btnLogout = new JButton("Logout");
        btnLogout.setBounds(780, 25, 90, 30);
        btnLogout.setBackground(new Color(220, 53, 69)); // Keep red for logout (safety)
        btnLogout.setForeground(Color.WHITE);
        btnLogout.setFont(new Font("Arial", Font.BOLD, 12));
        btnLogout.setFocusPainted(false);
        btnLogout.addActionListener(e -> logout());
        headerPanel.add(btnLogout);

        mainPanel.add(headerPanel);

        // Statistics Panel
        JPanel statsPanel = new JPanel();
        statsPanel.setBounds(30, 110, 840, 100);
        statsPanel.setLayout(new GridLayout(1, 3, 20, 0));
        statsPanel.setBackground(new Color(245, 245, 245));

        // Card 1: Total Medicines - Blue
        JPanel card1 = createStatCard("Total Medicines", "0", new Color(0, 123, 255)); // Bright Blue
        lblTotalMedicines = (JLabel) card1.getComponent(1);
        statsPanel.add(card1);

        // Card 2: Low Stock - Green
        JPanel card2 = createStatCard("Low Stock Alert", "0", new Color(40, 167, 69)); // Green
        lblLowStock = (JLabel) card2.getComponent(1);
        statsPanel.add(card2);

        // Card 3: Active Status - Dark Blue
        JPanel card3 = createStatCard("User Role", currentUser.getRole().toUpperCase(), new Color(0, 102, 204)); // Dark Blue
        statsPanel.add(card3);

        mainPanel.add(statsPanel);

        // Menu Buttons Panel
        JPanel menuPanel = new JPanel();
        menuPanel.setBounds(30, 240, 840, 300);
        menuPanel.setLayout(new GridLayout(3, 3, 15, 15));
        menuPanel.setBackground(new Color(245, 245, 245));

        // Create Menu Buttons - Blue and Green theme
        JButton btnMedicines = createMenuButton("Medicines", "Manage Medicines", new Color(0, 123, 255)); // Blue
        btnMedicines.addActionListener(e -> openMedicineView());
        menuPanel.add(btnMedicines);

        JButton btnSuppliers = createMenuButton("Suppliers", "Manage Suppliers", new Color(40, 167, 69)); // Green
        btnSuppliers.addActionListener(e -> openSupplierView());
        menuPanel.add(btnSuppliers);

        JButton btnOrders = createMenuButton("Orders", "Purchase Orders", new Color(25, 135, 84)); // Dark Green
        btnOrders.addActionListener(e -> openOrderView());
        menuPanel.add(btnOrders);

        JButton btnInventory = createMenuButton("Inventory", "Stock Movements", new Color(13, 110, 253)); // Bright Blue
        btnInventory.addActionListener(e -> openInventoryView());
        menuPanel.add(btnInventory);

        JButton btnUsers = createMenuButton("Users", "Manage Users", new Color(0, 86, 179)); // Navy Blue
        btnUsers.addActionListener(e -> openUserView());
        if (!currentUser.getRole().equalsIgnoreCase("admin")) {
            btnUsers.setEnabled(false);
        }
        menuPanel.add(btnUsers);

        JButton btnReports = createMenuButton("Reports", "View Reports", new Color(32, 201, 151)); // Teal Green
        btnReports.addActionListener(e -> MessageHelper.showInfoMessage("Reports feature coming soon!"));
        menuPanel.add(btnReports);

        mainPanel.add(menuPanel);

        add(mainPanel);
    }

    private JPanel createStatCard(String title, String value, Color color) {
        JPanel card = new JPanel();
        card.setLayout(new BorderLayout(10, 10));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createLineBorder(color, 3));

        JLabel lblTitle = new JLabel(title, SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 14));
        lblTitle.setForeground(Color.GRAY);

        JLabel lblValue = new JLabel(value, SwingConstants.CENTER);
        lblValue.setFont(new Font("Arial", Font.BOLD, 36));
        lblValue.setForeground(color);

        card.add(lblTitle, BorderLayout.NORTH);
        card.add(lblValue, BorderLayout.CENTER);

        return card;
    }

    private JButton createMenuButton(String title, String subtitle, Color color) {
        JButton btn = new JButton("<html><center><b>" + title + "</b><br><small>" + subtitle + "</small></center></html>");
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Arial", Font.PLAIN, 14));
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private void loadDashboardData() {
        List<Medicine> medicines = medicineController.getAllMedicines();
        lblTotalMedicines.setText(String.valueOf(medicines.size()));

        List<Medicine> lowStock = medicineController.getLowStockMedicines();
        lblLowStock.setText(String.valueOf(lowStock.size()));
    }

    private void openMedicineView() {
        new MedicineView(currentUser).setVisible(true);
    }

    private void openSupplierView() {
        new SupplierView(currentUser).setVisible(true);
    }

    private void openOrderView() {
        new OrderView(currentUser).setVisible(true);
    }

    private void openInventoryView() {
        new InventoryView(currentUser).setVisible(true);
    }

    private void openUserView() {
        new UserView(currentUser).setVisible(true);
    }

    private void logout() {
        if (MessageHelper.showConfirmDialog("Are you sure you want to logout?")) {
            this.dispose();
            new LoginView().setVisible(true);
        }
    }
}