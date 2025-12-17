package view;

import controller.MedicineController;
import controller.SupplierController;
import model.Medicine;
import model.Supplier;
import model.User;
import utils.MessageHelper;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Medicine View - Page 3
 * Medicine management with CRUD operations
 */
public class MedicineView extends JFrame {
    private User currentUser;
    private MedicineController medicineController;
    private SupplierController supplierController;
    
    private JTextField txtMedicineName, txtDescription, txtUnitPrice, txtStockQuantity, txtReorderLevel;
    private JComboBox<String> cmbSupplier;
    private JTable table;
    private DefaultTableModel tableModel;
    private JButton btnAdd, btnUpdate, btnDelete, btnClear, btnRefresh;
    
    private int selectedMedicineId = -1;

    public MedicineView(User user) {
        this.currentUser = user;
        this.medicineController = new MedicineController();
        this.supplierController = new SupplierController();
        initComponents();
        loadTableData();
        loadSuppliers();
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        setTitle("Medicine Management");
        setSize(1000, 650);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBackground(Color.WHITE);

        // Header
        JLabel lblTitle = new JLabel("MEDICINE MANAGEMENT");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 22));
        lblTitle.setForeground(new Color(0, 102, 204));
        lblTitle.setBounds(20, 15, 400, 30);
        mainPanel.add(lblTitle);

        // Input Panel
        JPanel inputPanel = new JPanel();
        inputPanel.setBounds(20, 60, 450, 300);
        inputPanel.setLayout(null);
        inputPanel.setBorder(BorderFactory.createTitledBorder("Medicine Information"));
        inputPanel.setBackground(Color.WHITE);

        int yPos = 30;
        int gap = 45;

        // Medicine Name
        JLabel lblName = new JLabel("Medicine Name:");
        lblName.setBounds(15, yPos, 120, 25);
        inputPanel.add(lblName);
        
        txtMedicineName = new JTextField();
        txtMedicineName.setBounds(140, yPos, 280, 25);
        inputPanel.add(txtMedicineName);

        yPos += gap;

        // Description
        JLabel lblDesc = new JLabel("Description:");
        lblDesc.setBounds(15, yPos, 120, 25);
        inputPanel.add(lblDesc);
        
        txtDescription = new JTextField();
        txtDescription.setBounds(140, yPos, 280, 25);
        inputPanel.add(txtDescription);

        yPos += gap;

        // Unit Price
        JLabel lblPrice = new JLabel("Unit Price:");
        lblPrice.setBounds(15, yPos, 120, 25);
        inputPanel.add(lblPrice);
        
        txtUnitPrice = new JTextField();
        txtUnitPrice.setBounds(140, yPos, 280, 25);
        inputPanel.add(txtUnitPrice);

        yPos += gap;

        // Stock Quantity
        JLabel lblStock = new JLabel("Stock Quantity:");
        lblStock.setBounds(15, yPos, 120, 25);
        inputPanel.add(lblStock);
        
        txtStockQuantity = new JTextField();
        txtStockQuantity.setBounds(140, yPos, 280, 25);
        inputPanel.add(txtStockQuantity);

        yPos += gap;

        // Reorder Level
        JLabel lblReorder = new JLabel("Reorder Level:");
        lblReorder.setBounds(15, yPos, 120, 25);
        inputPanel.add(lblReorder);
        
        txtReorderLevel = new JTextField();
        txtReorderLevel.setBounds(140, yPos, 280, 25);
        inputPanel.add(txtReorderLevel);

        yPos += gap;

        // Supplier
        JLabel lblSupplier = new JLabel("Supplier:");
        lblSupplier.setBounds(15, yPos, 120, 25);
        inputPanel.add(lblSupplier);
        
        cmbSupplier = new JComboBox<>();
        cmbSupplier.setBounds(140, yPos, 280, 25);
        inputPanel.add(cmbSupplier);

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
        btnAdd.addActionListener(e -> addMedicine());
        buttonPanel.add(btnAdd);

        btnUpdate = new JButton("Update");
        btnUpdate.setBackground(new Color(0, 123, 255));
        btnUpdate.setForeground(Color.WHITE);
        btnUpdate.setFocusPainted(false);
        btnUpdate.addActionListener(e -> updateMedicine());
        buttonPanel.add(btnUpdate);

        btnDelete = new JButton("Delete");
        btnDelete.setBackground(new Color(220, 53, 69));
        btnDelete.setForeground(Color.WHITE);
        btnDelete.setFocusPainted(false);
        btnDelete.addActionListener(e -> deleteMedicine());
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
        String[] columns = {"ID", "Medicine Name", "Description", "Unit Price", "Stock", "Reorder Level", "Supplier ID"};
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

    private void loadSuppliers() {
        cmbSupplier.removeAllItems();
        List<Supplier> suppliers = supplierController.getActiveSuppliers();
        for (Supplier s : suppliers) {
            cmbSupplier.addItem(s.getSupplierId() + " - " + s.getSupplierName());
        }
    }

    private void loadTableData() {
        tableModel.setRowCount(0);
        List<Medicine> medicines = medicineController.getAllMedicines();
        
        for (Medicine m : medicines) {
            Object[] row = {
                m.getMedicineId(),
                m.getMedicineName(),
                m.getDescription(),
                m.getUnitPrice(),
                m.getStockQuantity(),
                m.getReorderLevel(),
                m.getSupplierId()
            };
            tableModel.addRow(row);
        }
    }

    private void loadSelectedRow() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            selectedMedicineId = (int) tableModel.getValueAt(selectedRow, 0);
            txtMedicineName.setText(tableModel.getValueAt(selectedRow, 1).toString());
            txtDescription.setText(tableModel.getValueAt(selectedRow, 2).toString());
            txtUnitPrice.setText(tableModel.getValueAt(selectedRow, 3).toString());
            txtStockQuantity.setText(tableModel.getValueAt(selectedRow, 4).toString());
            txtReorderLevel.setText(tableModel.getValueAt(selectedRow, 5).toString());
            
            int supplierId = (int) tableModel.getValueAt(selectedRow, 6);
            for (int i = 0; i < cmbSupplier.getItemCount(); i++) {
                if (cmbSupplier.getItemAt(i).startsWith(supplierId + " -")) {
                    cmbSupplier.setSelectedIndex(i);
                    break;
                }
            }
        }
    }

    private void addMedicine() {
        try {
            Medicine medicine = new Medicine();
            medicine.setMedicineName(txtMedicineName.getText());
            medicine.setDescription(txtDescription.getText());
            medicine.setUnitPrice(Double.parseDouble(txtUnitPrice.getText()));
            medicine.setStockQuantity(Integer.parseInt(txtStockQuantity.getText()));
            medicine.setReorderLevel(Integer.parseInt(txtReorderLevel.getText()));
            
            String selectedSupplier = (String) cmbSupplier.getSelectedItem();
            int supplierId = Integer.parseInt(selectedSupplier.split(" - ")[0]);
            medicine.setSupplierId(supplierId);

            String result = medicineController.addMedicine(medicine);
            
            if (result.equals("SUCCESS")) {
                MessageHelper.showSuccessMessage("Medicine added successfully!");
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

    private void updateMedicine() {
        if (selectedMedicineId == -1) {
            MessageHelper.showWarningMessage("Please select a medicine to update!");
            return;
        }

        try {
            Medicine medicine = new Medicine();
            medicine.setMedicineId(selectedMedicineId);
            medicine.setMedicineName(txtMedicineName.getText());
            medicine.setDescription(txtDescription.getText());
            medicine.setUnitPrice(Double.parseDouble(txtUnitPrice.getText()));
            medicine.setStockQuantity(Integer.parseInt(txtStockQuantity.getText()));
            medicine.setReorderLevel(Integer.parseInt(txtReorderLevel.getText()));
            
            String selectedSupplier = (String) cmbSupplier.getSelectedItem();
            int supplierId = Integer.parseInt(selectedSupplier.split(" - ")[0]);
            medicine.setSupplierId(supplierId);

            String result = medicineController.updateMedicine(medicine);
            
            if (result.equals("SUCCESS")) {
                MessageHelper.showSuccessMessage("Medicine updated successfully!");
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

    private void deleteMedicine() {
        if (selectedMedicineId == -1) {
            MessageHelper.showWarningMessage("Please select a medicine to delete!");
            return;
        }

        if (MessageHelper.showConfirmDialog("Are you sure you want to delete this medicine?")) {
            String result = medicineController.deleteMedicine(selectedMedicineId);
            
            if (result.equals("SUCCESS")) {
                MessageHelper.showSuccessMessage("Medicine deleted successfully!");
                loadTableData();
                clearFields();
            } else {
                MessageHelper.showErrorMessage(result);
            }
        }
    }

    private void clearFields() {
        selectedMedicineId = -1;
        txtMedicineName.setText("");
        txtDescription.setText("");
        txtUnitPrice.setText("");
        txtStockQuantity.setText("");
        txtReorderLevel.setText("");
        if (cmbSupplier.getItemCount() > 0) {
            cmbSupplier.setSelectedIndex(0);
        }
        table.clearSelection();
    }
}