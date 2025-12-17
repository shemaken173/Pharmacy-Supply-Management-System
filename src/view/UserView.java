package view;

import controller.UserController;
import model.User;
import utils.MessageHelper;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * User View - Page 7
 * User management with CRUD operations (Admin only)
 */
public class UserView extends JFrame {
    private User currentUser;
    private UserController userController;
    
    private JTextField txtUsername, txtPassword, txtFullName, txtEmail, txtPhoneNumber;
    private JComboBox<String> cmbRole;
    private JTable table;
    private DefaultTableModel tableModel;
    private JButton btnAdd, btnUpdate, btnDelete, btnClear, btnRefresh;
    
    private int selectedUserId = -1;

    public UserView(User user) {
        this.currentUser = user;
        this.userController = new UserController();
        initComponents();
        loadTableData();
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        setTitle("User Management");
        setSize(1000, 650);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBackground(Color.WHITE);

        // Header
        JLabel lblTitle = new JLabel("USER MANAGEMENT");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 22));
        lblTitle.setForeground(new Color(111, 66, 193));
        lblTitle.setBounds(20, 15, 400, 30);
        mainPanel.add(lblTitle);

        JLabel lblWarning = new JLabel("(Admin Only)");
        lblWarning.setFont(new Font("Arial", Font.ITALIC, 12));
        lblWarning.setForeground(Color.RED);
        lblWarning.setBounds(280, 20, 100, 20);
        mainPanel.add(lblWarning);

        // Input Panel
        JPanel inputPanel = new JPanel();
        inputPanel.setBounds(20, 60, 450, 320);
        inputPanel.setLayout(null);
        inputPanel.setBorder(BorderFactory.createTitledBorder("User Information"));
        inputPanel.setBackground(Color.WHITE);

        int yPos = 30;
        int gap = 45;

        // Username
        JLabel lblUsername = new JLabel("Username:");
        lblUsername.setBounds(15, yPos, 120, 25);
        inputPanel.add(lblUsername);
        
        txtUsername = new JTextField();
        txtUsername.setBounds(140, yPos, 280, 25);
        inputPanel.add(txtUsername);

        yPos += gap;

        // Password
        JLabel lblPassword = new JLabel("Password:");
        lblPassword.setBounds(15, yPos, 120, 25);
        inputPanel.add(lblPassword);
        
        txtPassword = new JTextField();
        txtPassword.setBounds(140, yPos, 280, 25);
        inputPanel.add(txtPassword);

        yPos += gap;

        // Full Name
        JLabel lblFullName = new JLabel("Full Name:");
        lblFullName.setBounds(15, yPos, 120, 25);
        inputPanel.add(lblFullName);
        
        txtFullName = new JTextField();
        txtFullName.setBounds(140, yPos, 280, 25);
        inputPanel.add(txtFullName);

        yPos += gap;

        // Email
        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setBounds(15, yPos, 120, 25);
        inputPanel.add(lblEmail);
        
        txtEmail = new JTextField();
        txtEmail.setBounds(140, yPos, 280, 25);
        inputPanel.add(txtEmail);

        yPos += gap;

        // Phone Number
        JLabel lblPhone = new JLabel("Phone Number:");
        lblPhone.setBounds(15, yPos, 120, 25);
        inputPanel.add(lblPhone);
        
        txtPhoneNumber = new JTextField();
        txtPhoneNumber.setBounds(140, yPos, 280, 25);
        inputPanel.add(txtPhoneNumber);

        yPos += gap;

        // Role
        JLabel lblRole = new JLabel("Role:");
        lblRole.setBounds(15, yPos, 120, 25);
        inputPanel.add(lblRole);
        
        cmbRole = new JComboBox<>(new String[]{"admin", "pharmacist", "staff"});
        cmbRole.setBounds(140, yPos, 280, 25);
        inputPanel.add(cmbRole);

        mainPanel.add(inputPanel);

        // Buttons Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBounds(20, 395, 450, 50);
        buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        buttonPanel.setBackground(Color.WHITE);

        btnAdd = new JButton("Add User");
        btnAdd.setBackground(new Color(40, 167, 69));
        btnAdd.setForeground(Color.WHITE);
        btnAdd.setFocusPainted(false);
        btnAdd.addActionListener(e -> addUser());
        buttonPanel.add(btnAdd);

        btnUpdate = new JButton("Update");
        btnUpdate.setBackground(new Color(0, 123, 255));
        btnUpdate.setForeground(Color.WHITE);
        btnUpdate.setFocusPainted(false);
        btnUpdate.addActionListener(e -> updateUser());
        buttonPanel.add(btnUpdate);

        btnDelete = new JButton("Delete");
        btnDelete.setBackground(new Color(220, 53, 69));
        btnDelete.setForeground(Color.WHITE);
        btnDelete.setFocusPainted(false);
        btnDelete.addActionListener(e -> deleteUser());
        buttonPanel.add(btnDelete);

        btnClear = new JButton("Clear");
        btnClear.setBackground(new Color(108, 117, 125));
        btnClear.setForeground(Color.WHITE);
        btnClear.setFocusPainted(false);
        btnClear.addActionListener(e -> clearFields());
        buttonPanel.add(btnClear);

        btnRefresh = new JButton("Refresh");
        btnRefresh.setBackground(new Color(23, 162, 184));
        btnRefresh.setForeground(Color.WHITE);
        btnRefresh.setFocusPainted(false);
        btnRefresh.addActionListener(e -> loadTableData());
        buttonPanel.add(btnRefresh);

        mainPanel.add(buttonPanel);

        // Table Panel
        String[] columns = {"ID", "Username", "Password", "Full Name", "Email", "Phone", "Role"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                loadSelectedRow();
            }
        });

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(490, 60, 480, 520);
        mainPanel.add(scrollPane);

        add(mainPanel);
    }

    private void loadTableData() {
        tableModel.setRowCount(0);
        List<User> users = userController.getAllUsers();
        
        for (User u : users) {
            Object[] row = {
                u.getUserId(),
                u.getUsername(),
                u.getPassword(),
                u.getFullName(),
                u.getEmail(),
                u.getPhoneNumber(),
                u.getRole()
            };
            tableModel.addRow(row);
        }
    }

    private void loadSelectedRow() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            selectedUserId = (int) tableModel.getValueAt(selectedRow, 0);
            txtUsername.setText(tableModel.getValueAt(selectedRow, 1).toString());
            txtPassword.setText(tableModel.getValueAt(selectedRow, 2).toString());
            txtFullName.setText(tableModel.getValueAt(selectedRow, 3).toString());
            txtEmail.setText(tableModel.getValueAt(selectedRow, 4).toString());
            txtPhoneNumber.setText(tableModel.getValueAt(selectedRow, 5).toString());
            cmbRole.setSelectedItem(tableModel.getValueAt(selectedRow, 6).toString());
        }
    }

    private void addUser() {
        try {
            User user = new User();
            user.setUsername(txtUsername.getText());
            user.setPassword(txtPassword.getText());
            user.setFullName(txtFullName.getText());
            user.setEmail(txtEmail.getText());
            user.setPhoneNumber(txtPhoneNumber.getText());
            user.setRole((String) cmbRole.getSelectedItem());

            String result = userController.addUser(user);
            
            if (result.equals("SUCCESS")) {
                MessageHelper.showSuccessMessage("User added successfully!");
                loadTableData();
                clearFields();
            } else {
                MessageHelper.showErrorMessage(result);
            }
        } catch (Exception ex) {
            MessageHelper.showErrorMessage("Error: " + ex.getMessage());
        }
    }

    private void updateUser() {
        if (selectedUserId == -1) {
            MessageHelper.showWarningMessage("Please select a user to update!");
            return;
        }

        try {
            User user = new User();
            user.setUserId(selectedUserId);
            user.setUsername(txtUsername.getText());
            user.setPassword(txtPassword.getText());
            user.setFullName(txtFullName.getText());
            user.setEmail(txtEmail.getText());
            user.setPhoneNumber(txtPhoneNumber.getText());
            user.setRole((String) cmbRole.getSelectedItem());

            String result = userController.updateUser(user);
            
            if (result.equals("SUCCESS")) {
                MessageHelper.showSuccessMessage("User updated successfully!");
                loadTableData();
                clearFields();
            } else {
                MessageHelper.showErrorMessage(result);
            }
        } catch (Exception ex) {
            MessageHelper.showErrorMessage("Error: " + ex.getMessage());
        }
    }

    private void deleteUser() {
        if (selectedUserId == -1) {
            MessageHelper.showWarningMessage("Please select a user to delete!");
            return;
        }

        if (selectedUserId == currentUser.getUserId()) {
            MessageHelper.showErrorMessage("You cannot delete your own account!");
            return;
        }

        if (MessageHelper.showConfirmDialog("Are you sure you want to delete this user?")) {
            String result = userController.deleteUser(selectedUserId);
            
            if (result.equals("SUCCESS")) {
                MessageHelper.showSuccessMessage("User deleted successfully!");
                loadTableData();
                clearFields();
            } else {
                MessageHelper.showErrorMessage(result);
            }
        }
    }

    private void clearFields() {
        selectedUserId = -1;
        txtUsername.setText("");
        txtPassword.setText("");
        txtFullName.setText("");
        txtEmail.setText("");
        txtPhoneNumber.setText("");
        cmbRole.setSelectedIndex(0);
        table.clearSelection();
    }
}