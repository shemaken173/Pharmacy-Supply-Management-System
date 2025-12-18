package view;

import controller.UserController;
import model.User;
import utils.MessageHelper;
import javax.swing.*;
import java.awt.*;

/**
 * Login View - Page 1
 * User authentication interface
 */
public class LoginView extends JFrame {
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin, btnClear;
    private UserController userController;

    public LoginView() {
        userController = new UserController();
        initComponents();
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        setTitle("Pharmacy Supply Management System - Login");
        setSize(450, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        // Main Panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBackground(new Color(240, 248, 255));

        // Title Label
        JLabel lblTitle = new JLabel("PHARMACY SYSTEM");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitle.setForeground(new Color(0, 102, 204));
        lblTitle.setBounds(100, 30, 250, 30);
        mainPanel.add(lblTitle);

        JLabel lblSubtitle = new JLabel("Supply Management System");
        lblSubtitle.setFont(new Font("Arial", Font.PLAIN, 14));
        lblSubtitle.setForeground(Color.GRAY);
        lblSubtitle.setBounds(130, 60, 200, 20);
        mainPanel.add(lblSubtitle);

        // Username
        JLabel lblUsername = new JLabel("Username:");
        lblUsername.setFont(new Font("Arial", Font.BOLD, 14));
        lblUsername.setBounds(70, 120, 100, 25);
        mainPanel.add(lblUsername);

        txtUsername = new JTextField();
        txtUsername.setBounds(170, 120, 180, 30);
        txtUsername.setFont(new Font("Arial", Font.PLAIN, 13)); 
        mainPanel.add(txtUsername);

        // Password
        JLabel lblPassword = new JLabel("Password:");
        lblPassword.setFont(new Font("Arial", Font.BOLD, 14));
        lblPassword.setBounds(70, 170, 100, 25);
        mainPanel.add(lblPassword);

        txtPassword = new JPasswordField();
        txtPassword.setBounds(170, 170, 180, 30);
        txtPassword.setFont(new Font("Arial", Font.PLAIN, 13));
        mainPanel.add(txtPassword);

        // Buttons
        btnLogin = new JButton("Login");
        btnLogin.setBounds(170, 230, 80, 35);
        btnLogin.setBackground(new Color(0, 153, 76));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFont(new Font("Arial", Font.BOLD, 13));
        btnLogin.setFocusPainted(false);
        btnLogin.addActionListener(e -> handleLogin());
        mainPanel.add(btnLogin);

        btnClear = new JButton("Clear");
        btnClear.setBounds(270, 230, 80, 35);
        btnClear.setBackground(new Color(220, 53, 69));
        btnClear.setForeground(Color.WHITE);
        btnClear.setFont(new Font("Arial", Font.BOLD, 13));
        btnClear.setFocusPainted(false);
        btnClear.addActionListener(e -> clearFields());
        mainPanel.add(btnClear);

        add(mainPanel);
    }

    private void handleLogin() {
        String username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            MessageHelper.showWarningMessage("Please enter username and password!");
            return;
        }

        User user = userController.authenticateUser(username, password);
        
        if (user != null) {
            MessageHelper.showSuccessMessage("Login successful! Welcome " + user.getFullName());
            this.dispose();
            new DashboardView(user).setVisible(true);
        } else {
            MessageHelper.showErrorMessage("Invalid username or password!.");
            txtPassword.setText("");
        }
    }

    private void clearFields() {
        txtUsername.setText("");
        txtPassword.setText("");
        txtUsername.requestFocus();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new LoginView().setVisible(true);
        });
    }
}