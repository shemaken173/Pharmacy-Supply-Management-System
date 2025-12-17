 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.InventoryDao;
import model.Inventory;
import utils.Validator;
import java.util.List;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
/**
 *
 * @author ken
 */
public class InventoryController {
    
    private InventoryDao inventoryDao;
    private MedicineController medicineController;
    private Validator validator;

    public InventoryController() {
        this.inventoryDao = new InventoryDao();
        this.medicineController = new MedicineController();
        this.validator = new Validator();
    }

    // Add Inventory Transaction with Validations
    public String addInventoryTransaction(Inventory inventory) {
        // Technical Validations
        if (!validator.isPositiveInteger(inventory.getQuantity())) {
            return "Quantity must be greater than 0!";
        }
        if (inventory.getTransactionType() == null || 
            (!inventory.getTransactionType().equals("IN") && !inventory.getTransactionType().equals("OUT"))) {
            return "Transaction type must be IN or OUT!";
        }

        // Business Validations
        if (inventory.getTransactionType().equals("OUT")) {
            // Check if sufficient stock exists
            String stockCheck = medicineController.updateStock(
                inventory.getMedicineId(), 
                inventory.getQuantity(), 
                "OUT"
            );
            if (!stockCheck.equals("SUCCESS")) {
                return stockCheck; // Return error message
            }
        } else if (inventory.getTransactionType().equals("IN")) {
            // Update stock for IN transaction
            medicineController.updateStock(
                inventory.getMedicineId(), 
                inventory.getQuantity(), 
                "IN"
            );
        }

        // Validate transaction date
        try {
            LocalDateTime transDate = LocalDateTime.parse(
                inventory.getTransactionDate(), 
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            );
            if (transDate.isAfter(LocalDateTime.now())) {
                return "Transaction date cannot be in the future!";
            }
        } catch (Exception e) {
            return "Invalid date format! Use: yyyy-MM-dd HH:mm:ss";
        }

        boolean success = inventoryDao.addInventory(inventory);
        return success ? "SUCCESS" : "Failed to add inventory transaction!";
    }

    // Update Inventory Transaction
    public String updateInventoryTransaction(Inventory inventory) {
        boolean success = inventoryDao.updateInventory(inventory);
        return success ? "SUCCESS" : "Failed to update inventory transaction!";
    }

    // Delete Inventory Transaction
    public String deleteInventoryTransaction(int inventoryId) {
        boolean success = inventoryDao.deleteInventory(inventoryId);
        return success ? "SUCCESS" : "Failed to delete inventory transaction!";
    }

    // Get All Inventory Transactions
    public List<Inventory> getAllInventoryTransactions() {
        return inventoryDao.getAllInventory();
    }

    // Get IN Transactions Only
    public List<Inventory> getInTransactions() {
        List<Inventory> all = inventoryDao.getAllInventory();
        List<Inventory> inTransactions = new java.util.ArrayList<>();
        
        for (Inventory i : all) {
            if (i.getTransactionType().equals("IN")) {
                inTransactions.add(i);
            }
        }
        return inTransactions;
    }

    // Get OUT Transactions Only
    public List<Inventory> getOutTransactions() {
        List<Inventory> all = inventoryDao.getAllInventory();
        List<Inventory> outTransactions = new java.util.ArrayList<>();
        
        for (Inventory i : all) {
            if (i.getTransactionType().equals("OUT")) {
                outTransactions.add(i);
            }
        }
        return outTransactions;
    }

    // Generate current timestamp
    public String getCurrentTimestamp() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return now.format(formatter);
    }
}
