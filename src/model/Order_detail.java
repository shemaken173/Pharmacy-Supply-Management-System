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
public class Order_detail {

    private int orderDetailId;
    private int orderId;
    private int medicineId;
    private int quantity;
    private double unitPriceAtOrder;

    public Order_detail() {}

    public Order_detail(int orderDetailId, int orderId, int medicineId,
                        int quantity, double unitPriceAtOrder) {
        this.orderDetailId = orderDetailId;
        this.orderId = orderId;
        this.medicineId = medicineId;
        this.quantity = quantity;
        this.unitPriceAtOrder = unitPriceAtOrder;
    }

    public int getOrderDetailId() { return orderDetailId; }
    public void setOrderDetailId(int orderDetailId) { this.orderDetailId = orderDetailId; }

    public int getOrderId() { return orderId; }
    public void setOrderId(int orderId) { this.orderId = orderId; }

    public int getMedicineId() { return medicineId; }
    public void setMedicineId(int medicineId) { this.medicineId = medicineId; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public double getUnitPriceAtOrder() { return unitPriceAtOrder; }
    public void setUnitPriceAtOrder(double unitPriceAtOrder) { this.unitPriceAtOrder = unitPriceAtOrder; }
}

 
