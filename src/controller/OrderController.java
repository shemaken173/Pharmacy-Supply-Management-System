/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.OrderDao;
import model.Order;
import utils.Validator;
import java.util.List;
import java.time.LocalDate;
/**
 *
 * @author ken
 */
public class OrderController {
     private OrderDao orderDao;
    private Validator validator;

    public OrderController() {
        this.orderDao = new OrderDao();
        this.validator = new Validator();
    }

    // Add Order with Validations
    public String addOrder(Order order) {
        // Technical Validations
        if (order.getOrderNumber().trim().isEmpty()) {
            return "Order number cannot be empty!";
        }
        if (!validator.isPositiveDecimal(order.getTotalAmount())) {
            return "Total amount must be greater than 0!";
        }

        // Business Validations
        if (isOrderNumberExists(order.getOrderNumber())) {
            return "Order number already exists!";
        }
        
        // Validate order date is not in future
        try {
            LocalDate orderDate = LocalDate.parse(order.getOrderDate());
            if (orderDate.isAfter(LocalDate.now())) {
                return "Order date cannot be in the future!";
            }
        } catch (Exception e) {
            return "Invalid date format!";
        }

        boolean success = orderDao.addOrder(order);
        return success ? "SUCCESS" : "Failed to add order!";
    }

    // Update Order
    public String updateOrder(Order order) {
        // Validations
        if (!validator.isPositiveDecimal(order.getTotalAmount())) {
            return "Total amount must be greater than 0!";
        }

        boolean success = orderDao.updateOrder(order);
        return success ? "SUCCESS" : "Failed to update order!";
    }

    // Delete Order
    public String deleteOrder(int orderId) {
        // Business Validation: Cannot delete completed orders
        boolean success = orderDao.deleteOrder(orderId);
        return success ? "SUCCESS" : "Cannot delete order! Order may be completed or has related records.";
    }

    // Get All Orders
    public List<Order> getAllOrders() {
        return orderDao.getAllOrders();
    }

    // Get Pending Orders
    public List<Order> getPendingOrders() {
        List<Order> allOrders = orderDao.getAllOrders();
        List<Order> pending = new java.util.ArrayList<>();
        
        for (Order o : allOrders) {
            if (o.getStatus().equalsIgnoreCase("pending")) {
                pending.add(o);
            }
        }
        return pending;
    }

    // Complete Order
    public String completeOrder(int orderId) {
        // Get order and update status
        List<Order> orders = orderDao.getAllOrders();
        Order order = null;
        
        for (Order o : orders) {
            if (o.getOrderId() == orderId) {
                order = o;
                break;
            }
        }
        
        if (order == null) {
            return "Order not found!";
        }
        
        order.setStatus("completed");
        boolean success = orderDao.updateOrder(order);
        return success ? "SUCCESS" : "Failed to complete order!";
    }

    // Generate Order Number
    public String generateOrderNumber() {
        String prefix = "ORD-" + LocalDate.now().getYear() + "-";
        List<Order> orders = orderDao.getAllOrders();
        int maxNumber = 0;
        
        for (Order o : orders) {
            String orderNum = o.getOrderNumber();
            if (orderNum.startsWith(prefix)) {
                try {
                    String numPart = orderNum.substring(prefix.length());
                    int num = Integer.parseInt(numPart);
                    if (num > maxNumber) maxNumber = num;
                } catch (Exception e) {
                    // Skip invalid format
                }
            }
        }
        
        return prefix + String.format("%03d", maxNumber + 1);
    }

    // Helper: Check if order number exists
    private boolean isOrderNumberExists(String orderNumber) {
        List<Order> orders = orderDao.getAllOrders();
        for (Order o : orders) {
            if (o.getOrderNumber().equalsIgnoreCase(orderNumber)) {
                return true;
            }
        }
        return false;
    }
}

