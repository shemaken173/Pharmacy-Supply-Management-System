package view;

import controller.SupplierController;
import model.Supplier;
import model.User;
import utils.MessageHelper;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Supplier View - Page 4
 * Supplier management with CRUD operations
 */
public class SupplierView extends JFrame {
    private User currentUser;
    private SupplierController supplierController;
    
    private JTextField txtSupplierName, txtContactPerson, txtPhoneNumber, txtEmail, txtAddress;
    private JComboBox<String> cmbStatus;
    private JTable table;
    private DefaultTableModel tableModel;
    private JButton btnAdd, btnUpdate, btnDelete, btnClear, btnRefresh;
    
    private int selectedSupplierId = -1;

    public SupplierView(User user) {
        this.currentUser = user;
        this.supplierController = new SupplierController();
        initComponents();
        loadTableData();
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        setTitle("Supplier Management");
        setSize(1000, 650);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBackground(Color.WHITE);

        // Header
        JLabel lblTitle = new JLabel("SUPPLIER MANAGEMENT");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 22));
        lblTitle.setForeground(new Color(40, 167, 69));
        lblTitle.setBounds(20, 15, 400, 30);
        mainPanel.add(lblTitle);

        // Input Panel
        JPanel inputPanel = new JPanel();
        inputPanel.setBounds(20, 60, 450, 300);
        inputPanel.setLayout(null);
        inputPanel.setBorder(BorderFactory.createTitledBorder("Supplier Information"));
        inputPanel.setBackground(Color.WHITE);

        int yPos = 30;
        int gap = 45;

        // Supplier Name
        JLabel lblName = new JLabel("Supplier Name:");
        lblName.setBounds(15, yPos, 120, 25);
        inputPanel.add(lblName);
        
        txtSupplierName = new JTextField();
        txtSupplierName.setBounds(140, yPos, 280, 25);
        inputPanel.add(txtSupplierName);

        yPos += gap;

        // Contact Person
        JLabel lblContact = new JLabel("Contact Person:");
        lblContact.setBounds(15, yPos, 120, 25);
        inputPanel.add(lblContact);
        
        txtContactPerson = new JTextField();
        txtContactPerson.setBounds(140, yPos, 280, 25);
        inputPanel.add(txtContactPerson);

        yPos += gap;

        // Phone Number
        JLabel lblPhone = new JLabel("Phone Number:");
        lblPhone.setBounds(15, yPos, 120, 25);
        inputPanel.add(lblPhone);
        
        txtPhoneNumber = new JTextField();
        txtPhoneNumber.setBounds(140, yPos, 280, 25);
        inputPanel.add(txtPhoneNumber);

        yPos += gap;

        // Email
        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setBounds(15, yPos, 120, 25);
        inputPanel.add(lblEmail);
        
        txtEmail = new JTextField();
        txtEmail.setBounds(140, yPos, 280, 25);
        inputPanel.add(txtEmail);

        yPos += gap;

        // Address
        JLabel lblAddress = new JLabel("Address:");
        lblAddress.setBounds(15, yPos, 120, 25);
        inputPanel.add(lblAddress);
        
        txtAddress = new JTextField();
        txtAddress.setBounds(140, yPos, 280, 25);
        inputPanel.add(txtAddress);

        yPos += gap;

        // Status
        JLabel lblStatus = new JLabel("Status:");
        lblStatus.setBounds(15, yPos, 120, 25);
        inputPanel.add(lblStatus);
        
        cmbStatus = new JComboBox<>(new String[]{"active", "inactive"});
        cmbStatus.setBounds(140, yPos, 280, 25);
        inputPanel.add(cmbStatus);

        mainPanel.add(inputPanel);

        // Buttons Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBounds(20, 375, 450, 50);
        buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        buttonPanel.setBackground(Color.WHITE);

        btnAdd = new JButton("Add");
        btnAdd.setBackground(new Color(40, 167, 69));
        btnAdd.setForeground(Color.WHITE);
        btnAdd.setFocusPainted(false);
        btnAdd.addActionListener(e -> addSupplier());
        buttonPanel.add(btnAdd);

        btnUpdate = new JButton("Update");
        btnUpdate.setBackground(new Color(0, 123, 255));
        btnUpdate.setForeground(Color.WHITE);
        btnUpdate.setFocusPainted(false);
        btnUpdate.addActionListener(e -> updateSupplier());
        buttonPanel.add(btnUpdate);

        btnDelete = new JButton("Delete");
        btnDelete.setBackground(new Color(220, 53, 69));
        btnDelete.setForeground(Color.WHITE);
        btnDelete.setFocusPainted(false);
        btnDelete.addActionListener(e -> deleteSupplier());
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
        String[] columns = {"ID", "Supplier Name", "Contact Person", "Phone", "Email", "Address", "Status"};
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
        List<Supplier> suppliers = supplierController.getAllSuppliers();
        
        for (Supplier s : suppliers) {
            Object[] row = {
                s.getSupplierId(),
                s.getSupplierName(),
                s.getContactPerson(),
                s.getPhoneNumber(),
                s.getEmail(),
                s.getAddress(),
                s.getStatus()
            };
            tableModel.addRow(row);
        }
    }

    private void loadSelectedRow() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            selectedSupplierId = (int) tableModel.getValueAt(selectedRow, 0);
            txtSupplierName.setText(tableModel.getValueAt(selectedRow, 1).toString());
            txtContactPerson.setText(tableModel.getValueAt(selectedRow, 2).toString());
            txtPhoneNumber.setText(tableModel.getValueAt(selectedRow, 3).toString());
            txtEmail.setText(tableModel.getValueAt(selectedRow, 4).toString());
            txtAddress.setText(tableModel.getValueAt(selectedRow, 5).toString());
            cmbStatus.setSelectedItem(tableModel.getValueAt(selectedRow, 6).toString());
        }
    }

    private void addSupplier() {
        try {
            Supplier supplier = new Supplier();
            supplier.setSupplierName(txtSupplierName.getText());
            supplier.setContactPerson(txtContactPerson.getText());
            supplier.setPhoneNumber(txtPhoneNumber.getText());
            supplier.setEmail(txtEmail.getText());
            supplier.setAddress(txtAddress.getText());
            supplier.setStatus((String) cmbStatus.getSelectedItem());

            String result = supplierController.addSupplier(supplier);
            
            if (result.equals("SUCCESS")) {
                MessageHelper.showSuccessMessage("Supplier added successfully!");
                loadTableData();
                clearFields();
            } else {
                MessageHelper.showErrorMessage(result);
            }
        } catch (Exception ex) {
            MessageHelper.showErrorMessage("Error: " + ex.getMessage());
        }
    }

    private void updateSupplier() {
        if (selectedSupplierId == -1) {
            MessageHelper.showWarningMessage("Please select a supplier to update!");
            return;
        }

        try {
            Supplier supplier = new Supplier();
            supplier.setSupplierId(selectedSupplierId);
            supplier.setSupplierName(txtSupplierName.getText());
            supplier.setContactPerson(txtContactPerson.getText());
            supplier.setPhoneNumber(txtPhoneNumber.getText());
            supplier.setEmail(txtEmail.getText());
            supplier.setAddress(txtAddress.getText());
            supplier.setStatus((String) cmbStatus.getSelectedItem());

            String result = supplierController.updateSupplier(supplier);
            
            if (result.equals("SUCCESS")) {
                MessageHelper.showSuccessMessage("Supplier updated successfully!");
                loadTableData();
                clearFields();
            } else {
                MessageHelper.showErrorMessage(result);
            }
        } catch (Exception ex) {
            MessageHelper.showErrorMessage("Error: " + ex.getMessage());
        }
    }

    private void deleteSupplier() {
        if (selectedSupplierId == -1) {
            MessageHelper.showWarningMessage("Please select a supplier to delete!");
            return;
        }

        if (MessageHelper.showConfirmDialog("Are you sure you want to delete this supplier?")) {
            String result = supplierController.deleteSupplier(selectedSupplierId);
            
            if (result.equals("SUCCESS")) {
                MessageHelper.showSuccessMessage("Supplier deleted successfully!");
                loadTableData();
                clearFields();
            } else {
                MessageHelper.showErrorMessage(result);
            }
        }
    }

    private void clearFields() {
        selectedSupplierId = -1;
        txtSupplierName.setText("");
        txtContactPerson.setText("");
        txtPhoneNumber.setText("");
        txtEmail.setText("");
        txtAddress.setText("");
        cmbStatus.setSelectedIndex(0);
        table.clearSelection();
    }
}