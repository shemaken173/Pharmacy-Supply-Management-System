/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author ken
 */
public class Inventory {
    private int inventoryId;
    private int medicineId;
    private String transactionType;  // IN or OUT
    private int quantity;
    private String transactionDate;
    private int userId;
    private Integer orderId;   // nullable
    private String batchNumber;
    private String remarks;

    public Inventory() {}

    public Inventory(int inventoryId, int medicineId, String transactionType, int quantity,
                     String transactionDate, int userId, Integer orderId,
                     String batchNumber, String remarks) {
        this.inventoryId = inventoryId;
        this.medicineId = medicineId;
        this.transactionType = transactionType;
        this.quantity = quantity;
        this.transactionDate = transactionDate;
        this.userId = userId;
        this.orderId = orderId;
        this.batchNumber = batchNumber;
        this.remarks = remarks;
    }

    public int getInventoryId() { return inventoryId; }
    public void setInventoryId(int inventoryId) { this.inventoryId = inventoryId; }

    public int getMedicineId() { return medicineId; }
    public void setMedicineId(int medicineId) { this.medicineId = medicineId; }

    public String getTransactionType() { return transactionType; }
    public void setTransactionType(String transactionType) { this.transactionType = transactionType; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public String getTransactionDate() { return transactionDate; }
    public void setTransactionDate(String transactionDate) { this.transactionDate = transactionDate; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public Integer getOrderId() { return orderId; }
    public void setOrderId(Integer orderId) { this.orderId = orderId; }

    public String getBatchNumber() { return batchNumber; }
    public void setBatchNumber(String batchNumber) { this.batchNumber = batchNumber; }

    public String getRemarks() { return remarks; }
    public void setRemarks(String remarks) { this.remarks = remarks; }
}