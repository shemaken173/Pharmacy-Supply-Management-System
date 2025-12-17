package view;

import controller.InventoryController;
import controller.MedicineController;
import model.Inventory;
import model.Medicine;
import model.User;
import utils.MessageHelper;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Inventory View - Page 6
 * Inventory/Stock movement management
 */
public class InventoryView extends JFrame {
    private User currentUser;
    private InventoryController inventoryController;
    private MedicineController medicineController;
    
    private JComboBox<String> cmbMedicine, cmbTransactionType;
    private JTextField txtQuantity, txtBatchNumber, txtRemarks;
    private JTable table;
    private DefaultTableModel tableModel;
    private JButton btnAdd, btnUpdate, btnDelete, btnClear, btnRefresh;
    private JButton btnFilterIn, btnFilterOut, btnShowAll;
    
    private int selectedInventoryId = -1;

    public InventoryView(User user) {
        this.currentUser = user;
        this.inventoryController = new InventoryController();
        this.medicineController = new MedicineController();
        initComponents();
        loadTableData();
        loadMedicines();
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        setTitle("Inventory Management - Stock Movements");
        setSize(1100, 700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBackground(Color.WHITE);

        // Header
        JLabel lblTitle = new JLabel("INVENTORY MANAGEMENT");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 22));
        lblTitle.setForeground(new Color(108, 117, 125));
        lblTitle.setBounds(20, 15, 400, 30);
        mainPanel.add(lblTitle);

        JLabel lblSubtitle = new JLabel("Track Stock IN/OUT Transactions");
        lblSubtitle.setFont(new Font("Arial", Font.PLAIN, 13));
        lblSubtitle.setForeground(Color.GRAY);
        lblSubtitle.setBounds(20, 45, 300, 20);
        mainPanel.add(lblSubtitle);

        // Input Panel
        JPanel inputPanel = new JPanel();
        inputPanel.setBounds(20, 80, 450, 280);
        inputPanel.setLayout(null);
        inputPanel.setBorder(BorderFactory.createTitledBorder("Transaction Information"));
        inputPanel.setBackground(Color.WHITE);

        int yPos = 30;
        int gap = 50;

        // Medicine
        JLabel lblMedicine = new JLabel("Medicine:");
        lblMedicine.setBounds(15, yPos, 120, 25);
        inputPanel.add(lblMedicine);
        
        cmbMedicine = new JComboBox<>();
        cmbMedicine.setBounds(140, yPos, 280, 25);
        inputPanel.add(cmbMedicine);

        yPos += gap;

        // Transaction Type
        JLabel lblType = new JLabel("Transaction Type:");
        lblType.setBounds(15, yPos, 120, 25);
        inputPanel.add(lblType);
        
        cmbTransactionType = new JComboBox<>(new String[]{"IN", "OUT"});
        cmbTransactionType.setBounds(140, yPos, 280, 25);
        inputPanel.add(cmbTransactionType);

        yPos += gap;

        // Quantity
        JLabel lblQuantity = new JLabel("Quantity:");
        lblQuantity.setBounds(15, yPos, 120, 25);
        inputPanel.add(lblQuantity);
        
        txtQuantity = new JTextField();
        txtQuantity.setBounds(140, yPos, 280, 25);
        inputPanel.add(txtQuantity);

        yPos += gap;

        // Batch Number
        JLabel lblBatch = new JLabel("Batch Number:");
        lblBatch.setBounds(15, yPos, 120, 25);
        inputPanel.add(lblBatch);
        
        txtBatchNumber = new JTextField();
        txtBatchNumber.setBounds(140, yPos, 280, 25);
        inputPanel.add(txtBatchNumber);

        yPos += gap;

        // Remarks
        JLabel lblRemarks = new JLabel("Remarks:");
        lblRemarks.setBounds(15, yPos, 120, 25);
        inputPanel.add(lblRemarks);
        
        txtRemarks = new JTextField();
        txtRemarks.setBounds(140, yPos, 280, 25);
        inputPanel.add(txtRemarks);

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
        btnAdd.addActionListener(e -> addTransaction());
        buttonPanel.add(btnAdd);

        btnUpdate = new JButton("Update");
        btnUpdate.setBackground(new Color(0, 123, 255));
        btnUpdate.setForeground(Color.WHITE);
        btnUpdate.setFocusPainted(false);
        btnUpdate.addActionListener(e -> updateTransaction());
        buttonPanel.add(btnUpdate);

        btnDelete = new JButton("Delete");
        btnDelete.setBackground(new Color(220, 53, 69));
        btnDelete.setForeground(Color.WHITE);
        btnDelete.setFocusPainted(false);
        btnDelete.addActionListener(e -> deleteTransaction());
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

        // Filter Panel
        JPanel filterPanel = new JPanel();
        filterPanel.setBounds(490, 80, 580, 50);
        filterPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        filterPanel.setBackground(Color.WHITE);
        filterPanel.setBorder(BorderFactory.createTitledBorder("Filter Transactions"));

        btnShowAll = new JButton("Show All");
        btnShowAll.setBackground(new Color(108, 117, 125));
        btnShowAll.setForeground(Color.WHITE);
        btnShowAll.setFocusPainted(false);
        btnShowAll.addActionListener(e -> loadTableData());
        filterPanel.add(btnShowAll);

        btnFilterIn = new JButton("Stock IN");
        btnFilterIn.setBackground(new Color(40, 167, 69));
        btnFilterIn.setForeground(Color.WHITE);
        btnFilterIn.setFocusPainted(false);
        btnFilterIn.addActionListener(e -> filterInTransactions());
        filterPanel.add(btnFilterIn);

        btnFilterOut = new JButton("Stock OUT");
        btnFilterOut.setBackground(new Color(220, 53, 69));
        btnFilterOut.setForeground(Color.WHITE);
        btnFilterOut.setFocusPainted(false);
        btnFilterOut.addActionListener(e -> filterOutTransactions());
        filterPanel.add(btnFilterOut);

        mainPanel.add(filterPanel);

        // Table Panel
        String[] columns = {"ID", "Medicine ID", "Type", "Quantity", "Date", "User ID", "Order ID", "Batch", "Remarks"};
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
        scrollPane.setBounds(490, 140, 580, 500);
        mainPanel.add(scrollPane);

        add(mainPanel);
    }

    private void loadMedicines() {
        cmbMedicine.removeAllItems();
        List<Medicine> medicines = medicineController.getAllMedicines();
        for (Medicine m : medicines) {
            cmbMedicine.addItem(m.getMedicineId() + " - " + m.getMedicineName());
        }
    }

    private void loadTableData() {
        tableModel.setRowCount(0);
        List<Inventory> transactions = inventoryController.getAllInventoryTransactions();
        
        for (Inventory inv : transactions) {
            Object[] row = {
                inv.getInventoryId(),
                inv.getMedicineId(),
                inv.getTransactionType(),
                inv.getQuantity(),
                inv.getTransactionDate(),
                inv.getUserId(),
                inv.getOrderId() != null ? inv.getOrderId() : "N/A",
                inv.getBatchNumber(),
                inv.getRemarks()
            };
            tableModel.addRow(row);
        }
    }

    private void filterInTransactions() {
        tableModel.setRowCount(0);
        List<Inventory> transactions = inventoryController.getInTransactions();
        
        for (Inventory inv : transactions) {
            Object[] row = {
                inv.getInventoryId(),
                inv.getMedicineId(),
                inv.getTransactionType(),
                inv.getQuantity(),
                inv.getTransactionDate(),
                inv.getUserId(),
                inv.getOrderId() != null ? inv.getOrderId() : "N/A",
                inv.getBatchNumber(),
                inv.getRemarks()
            };
            tableModel.addRow(row);
        }
    }

    private void filterOutTransactions() {
        tableModel.setRowCount(0);
        List<Inventory> transactions = inventoryController.getOutTransactions();
        
        for (Inventory inv : transactions) {
            Object[] row = {
                inv.getInventoryId(),
                inv.getMedicineId(),
                inv.getTransactionType(),
                inv.getQuantity(),
                inv.getTransactionDate(),
                inv.getUserId(),
                inv.getOrderId() != null ? inv.getOrderId() : "N/A",
                inv.getBatchNumber(),
                inv.getRemarks()
            };
            tableModel.addRow(row);
        }
    }

    private void loadSelectedRow() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            selectedInventoryId = (int) tableModel.getValueAt(selectedRow, 0);
            
            int medicineId = (int) tableModel.getValueAt(selectedRow, 1);
            for (int i = 0; i < cmbMedicine.getItemCount(); i++) {
                if (cmbMedicine.getItemAt(i).startsWith(medicineId + " -")) {
                    cmbMedicine.setSelectedIndex(i);
                    break;
                }
            }
            
            cmbTransactionType.setSelectedItem(tableModel.getValueAt(selectedRow, 2).toString());
            txtQuantity.setText(tableModel.getValueAt(selectedRow, 3).toString());
            txtBatchNumber.setText(tableModel.getValueAt(selectedRow, 7).toString());
            txtRemarks.setText(tableModel.getValueAt(selectedRow, 8).toString());
        }
    }

    private void addTransaction() {
        try {
            Inventory inventory = new Inventory();
            
            String selectedMedicine = (String) cmbMedicine.getSelectedItem();
            int medicineId = Integer.parseInt(selectedMedicine.split(" - ")[0]);
            inventory.setMedicineId(medicineId);
            
            inventory.setTransactionType((String) cmbTransactionType.getSelectedItem());
            inventory.setQuantity(Integer.parseInt(txtQuantity.getText()));
            inventory.setTransactionDate(inventoryController.getCurrentTimestamp());
            inventory.setUserId(currentUser.getUserId());
            inventory.setOrderId(null); // Set to null or get from order
            inventory.setBatchNumber(txtBatchNumber.getText());
            inventory.setRemarks(txtRemarks.getText());

            String result = inventoryController.addInventoryTransaction(inventory);
            
            if (result.equals("SUCCESS")) {
                MessageHelper.showSuccessMessage("Transaction recorded successfully!");
                loadTableData();
                clearFields();
            } else {
                MessageHelper.showErrorMessage(result);
            }
        } catch (NumberFormatException ex) {
            MessageHelper.showErrorMessage("Please enter valid numeric values!");
        } catch (Exception ex) {
            MessageHelper.showErrorMessage("Error: " + ex.getMessage());
        }
    }

    private void updateTransaction() {
        if (selectedInventoryId == -1) {
            MessageHelper.showWarningMessage("Please select a transaction to update!");
            return;
        }

        try {
            Inventory inventory = new Inventory();
            inventory.setInventoryId(selectedInventoryId);
            
            String selectedMedicine = (String) cmbMedicine.getSelectedItem();
            int medicineId = Integer.parseInt(selectedMedicine.split(" - ")[0]);
            inventory.setMedicineId(medicineId);
            
            inventory.setTransactionType((String) cmbTransactionType.getSelectedItem());
            inventory.setQuantity(Integer.parseInt(txtQuantity.getText()));
            inventory.setTransactionDate(inventoryController.getCurrentTimestamp());
            inventory.setUserId(currentUser.getUserId());
            inventory.setOrderId(null);
            inventory.setBatchNumber(txtBatchNumber.getText());
            inventory.setRemarks(txtRemarks.getText());

            String result = inventoryController.updateInventoryTransaction(inventory);
            
            if (result.equals("SUCCESS")) {
                MessageHelper.showSuccessMessage("Transaction updated successfully!");
                loadTableData();
                clearFields();
            } else {
                MessageHelper.showErrorMessage(result);
            }
        } catch (NumberFormatException ex) {
            MessageHelper.showErrorMessage("Please enter valid numeric values!");
        } catch (Exception ex) {
            MessageHelper.showErrorMessage("Error: " + ex.getMessage());
        }
    }

    private void deleteTransaction() {
        if (selectedInventoryId == -1) {
            MessageHelper.showWarningMessage("Please select a transaction to delete!");
            return;
        }

        if (MessageHelper.showConfirmDialog("Are you sure you want to delete this transaction?")) {
            String result = inventoryController.deleteInventoryTransaction(selectedInventoryId);
            
            if (result.equals("SUCCESS")) {
                MessageHelper.showSuccessMessage("Transaction deleted successfully!");
                loadTableData();
                clearFields();
            } else {
                MessageHelper.showErrorMessage(result);
            }
        }
    }

    private void clearFields() {
        selectedInventoryId = -1;
        if (cmbMedicine.getItemCount() > 0) {
            cmbMedicine.setSelectedIndex(0);
        }
        cmbTransactionType.setSelectedIndex(0);
        txtQuantity.setText("");
        txtBatchNumber.setText("");
        txtRemarks.setText("");
        table.clearSelection();
    }
}