package view;

import controller.OrderController;
import controller.SupplierController;
import model.Order;
import model.Supplier;
import model.User;
import utils.MessageHelper;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

/**
 * Order View - Page 5
 * Order management with CRUD operations
 */
public class OrderView extends JFrame {
    private User currentUser;
    private OrderController orderController;
    private SupplierController supplierController;
    
    private JTextField txtOrderNumber, txtOrderDate, txtTotalAmount;
    private JComboBox<String> cmbSupplier, cmbStatus;
    private JTable table;
    private DefaultTableModel tableModel;
    private JButton btnAdd, btnUpdate, btnDelete, btnClear, btnRefresh, btnComplete;
    
    private int selectedOrderId = -1;

    public OrderView(User user) {
        this.currentUser = user;
        this.orderController = new OrderController();
        this.supplierController = new SupplierController();
        initComponents();
        loadTableData();
        loadSuppliers();
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        setTitle("Order Management");
        setSize(1100, 650);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBackground(Color.WHITE);

        // Header
        JLabel lblTitle = new JLabel("ORDER MANAGEMENT");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 22));
        lblTitle.setForeground(new Color(255, 193, 7));
        lblTitle.setBounds(20, 15, 400, 30);
        mainPanel.add(lblTitle);

        // Input Panel
        JPanel inputPanel = new JPanel();
        inputPanel.setBounds(20, 60, 450, 280);
        inputPanel.setLayout(null);
        inputPanel.setBorder(BorderFactory.createTitledBorder("Order Information"));
        inputPanel.setBackground(Color.WHITE);

        int yPos = 30;
        int gap = 45;

        // Order Number
        JLabel lblOrderNum = new JLabel("Order Number:");
        lblOrderNum.setBounds(15, yPos, 120, 25);
        inputPanel.add(lblOrderNum);
        
        txtOrderNumber = new JTextField();
        txtOrderNumber.setBounds(140, yPos, 200, 25);
        inputPanel.add(txtOrderNumber);

        JButton btnGenerate = new JButton("Generate");
        btnGenerate.setBounds(350, yPos, 90, 25);
        btnGenerate.setBackground(new Color(108, 117, 125));
        btnGenerate.setForeground(Color.WHITE);
        btnGenerate.setFocusPainted(false);
        btnGenerate.addActionListener(e -> generateOrderNumber());
        inputPanel.add(btnGenerate);

        yPos += gap;

        // Supplier
        JLabel lblSupplier = new JLabel("Supplier:");
        lblSupplier.setBounds(15, yPos, 120, 25);
        inputPanel.add(lblSupplier);
        
        cmbSupplier = new JComboBox<>();
        cmbSupplier.setBounds(140, yPos, 300, 25);
        inputPanel.add(cmbSupplier);

        yPos += gap;

        // Order Date
        JLabel lblDate = new JLabel("Order Date:");
        lblDate.setBounds(15, yPos, 120, 25);
        inputPanel.add(lblDate);
        
        txtOrderDate = new JTextField(LocalDate.now().toString());
        txtOrderDate.setBounds(140, yPos, 300, 25);
        inputPanel.add(txtOrderDate);

        yPos += gap;

        // Total Amount
        JLabel lblAmount = new JLabel("Total Amount:");
        lblAmount.setBounds(15, yPos, 120, 25);
        inputPanel.add(lblAmount);
        
        txtTotalAmount = new JTextField();
        txtTotalAmount.setBounds(140, yPos, 300, 25);
        inputPanel.add(txtTotalAmount);

        yPos += gap;

        // Status
        JLabel lblStatus = new JLabel("Status:");
        lblStatus.setBounds(15, yPos, 120, 25);
        inputPanel.add(lblStatus);
        
        cmbStatus = new JComboBox<>(new String[]{"pending", "completed", "cancelled"});
        cmbStatus.setBounds(140, yPos, 300, 25);
        inputPanel.add(cmbStatus);

        mainPanel.add(inputPanel);

        // Buttons Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBounds(20, 355, 450, 80);
        buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        buttonPanel.setBackground(Color.WHITE);

        btnAdd = new JButton("Add Order");
        btnAdd.setBackground(new Color(40, 167, 69));
        btnAdd.setForeground(Color.WHITE);
        btnAdd.setFocusPainted(false);
        btnAdd.addActionListener(e -> addOrder());
        buttonPanel.add(btnAdd);

        btnUpdate = new JButton("Update");
        btnUpdate.setBackground(new Color(0, 123, 255));
        btnUpdate.setForeground(Color.WHITE);
        btnUpdate.setFocusPainted(false);
        btnUpdate.addActionListener(e -> updateOrder());
        buttonPanel.add(btnUpdate);

        btnComplete = new JButton("Complete");
        btnComplete.setBackground(new Color(111, 66, 193));
        btnComplete.setForeground(Color.WHITE);
        btnComplete.setFocusPainted(false);
        btnComplete.addActionListener(e -> completeOrder());
        buttonPanel.add(btnComplete);

        btnDelete = new JButton("Delete");
        btnDelete.setBackground(new Color(220, 53, 69));
        btnDelete.setForeground(Color.WHITE);
        btnDelete.setFocusPainted(false);
        btnDelete.addActionListener(e -> deleteOrder());
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
        String[] columns = {"ID", "Order Number", "User ID", "Supplier ID", "Order Date", "Total Amount", "Status"};
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
        scrollPane.setBounds(490, 60, 580, 520);
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

    private void generateOrderNumber() {
        String orderNumber = orderController.generateOrderNumber();
        txtOrderNumber.setText(orderNumber);
    }

    private void loadTableData() {
        tableModel.setRowCount(0);
        List<Order> orders = orderController.getAllOrders();
        
        for (Order o : orders) {
            Object[] row = {
                o.getOrderId(),
                o.getOrderNumber(),
                o.getUserId(),
                o.getSupplierId(),
                o.getOrderDate(),
                String.format("%.2f", o.getTotalAmount()),
                o.getStatus()
            };
            tableModel.addRow(row);
        }
    }

    private void loadSelectedRow() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            selectedOrderId = (int) tableModel.getValueAt(selectedRow, 0);
            txtOrderNumber.setText(tableModel.getValueAt(selectedRow, 1).toString());
            txtOrderDate.setText(tableModel.getValueAt(selectedRow, 4).toString());
            txtTotalAmount.setText(tableModel.getValueAt(selectedRow, 5).toString());
            cmbStatus.setSelectedItem(tableModel.getValueAt(selectedRow, 6).toString());
            
            int supplierId = (int) tableModel.getValueAt(selectedRow, 3);
            for (int i = 0; i < cmbSupplier.getItemCount(); i++) {
                if (cmbSupplier.getItemAt(i).startsWith(supplierId + " -")) {
                    cmbSupplier.setSelectedIndex(i);
                    break;
                }
            }
        }
    }

    private void addOrder() {
        try {
            Order order = new Order();
            order.setOrderNumber(txtOrderNumber.getText());
            order.setUserId(currentUser.getUserId());
            
            String selectedSupplier = (String) cmbSupplier.getSelectedItem();
            int supplierId = Integer.parseInt(selectedSupplier.split(" - ")[0]);
            order.setSupplierId(supplierId);
            
            order.setOrderDate(txtOrderDate.getText());
            order.setTotalAmount(Double.parseDouble(txtTotalAmount.getText()));
            order.setStatus((String) cmbStatus.getSelectedItem());

            String result = orderController.addOrder(order);
            
            if (result.equals("SUCCESS")) {
                MessageHelper.showSuccessMessage("Order added successfully!");
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

    private void updateOrder() {
        if (selectedOrderId == -1) {
            MessageHelper.showWarningMessage("Please select an order to update!");
            return;
        }

        try {
            Order order = new Order();
            order.setOrderId(selectedOrderId);
            order.setOrderNumber(txtOrderNumber.getText());
            order.setUserId(currentUser.getUserId());
            
            String selectedSupplier = (String) cmbSupplier.getSelectedItem();
            int supplierId = Integer.parseInt(selectedSupplier.split(" - ")[0]);
            order.setSupplierId(supplierId);
            
            order.setOrderDate(txtOrderDate.getText());
            order.setTotalAmount(Double.parseDouble(txtTotalAmount.getText()));
            order.setStatus((String) cmbStatus.getSelectedItem());

            String result = orderController.updateOrder(order);
            
            if (result.equals("SUCCESS")) {
                MessageHelper.showSuccessMessage("Order updated successfully!");
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

    private void completeOrder() {
        if (selectedOrderId == -1) {
            MessageHelper.showWarningMessage("Please select an order to complete!");
            return;
        }

        if (MessageHelper.showConfirmDialog("Mark this order as completed?")) {
            String result = orderController.completeOrder(selectedOrderId);
            
            if (result.equals("SUCCESS")) {
                MessageHelper.showSuccessMessage("Order completed successfully!");
                loadTableData();
                clearFields();
            } else {
                MessageHelper.showErrorMessage(result);
            }
        }
    }

    private void deleteOrder() {
        if (selectedOrderId == -1) {
            MessageHelper.showWarningMessage("Please select an order to delete!");
            return;
        }

        if (MessageHelper.showConfirmDialog("Are you sure you want to delete this order?")) {
            String result = orderController.deleteOrder(selectedOrderId);
            
            if (result.equals("SUCCESS")) {
                MessageHelper.showSuccessMessage("Order deleted successfully!");
                loadTableData();
                clearFields();
            } else {
                MessageHelper.showErrorMessage(result);
            }
        }
    }

    private void clearFields() {
        selectedOrderId = -1;
        txtOrderNumber.setText("");
        txtOrderDate.setText(LocalDate.now().toString());
        txtTotalAmount.setText("");
        cmbStatus.setSelectedIndex(0);
        if (cmbSupplier.getItemCount() > 0) {
            cmbSupplier.setSelectedIndex(0);
        }
        table.clearSelection();
    }
}