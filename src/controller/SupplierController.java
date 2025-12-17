/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.SupplierDao;
import dao.SupplierDao.SupplierDAO;
import model.Supplier;
import utils.Validator;
import java.util.List;
/**
 *
 * @author ken
 */
public class SupplierController {
    private SupplierDAO supplierDao;
    private Validator validator;

    public SupplierController() {
        SupplierDao parent = new SupplierDao();
        this.supplierDao = parent.new SupplierDAO();
        this.validator = new Validator();
    }

    // Add Supplier with Validations
    public String addSupplier(Supplier supplier) {
        // Technical Validations
        if (supplier.getSupplierName().trim().isEmpty()) {
            return "Supplier name cannot be empty!";
        }
        if (!validator.isValidEmail(supplier.getEmail())) {
            return "Invalid email format!";
        }
        if (!validator.isValidPhoneNumber(supplier.getPhoneNumber())) {
            return "Phone number must be 10 digits!";
        }
        if (supplier.getContactPerson().trim().isEmpty()) {
            return "Contact person cannot be empty!";
        }
        if (supplier.getAddress().trim().isEmpty()) {
            return "Address cannot be empty!";
        }

        // Business Validations
        if (isSupplierEmailExists(supplier.getEmail())) {
            return "Supplier email already exists!";
        }

        boolean success = supplierDao.addSupplier(supplier);
        return success ? "SUCCESS" : "Failed to add supplier!";
    }

    // Update Supplier
    public String updateSupplier(Supplier supplier) {
        // Technical Validations
        if (!validator.isValidEmail(supplier.getEmail())) {
            return "Invalid email format!";
        }
        if (!validator.isValidPhoneNumber(supplier.getPhoneNumber())) {
            return "Phone number must be 10 digits!";
        }

        boolean success = supplierDao.updateSupplier(supplier);
        return success ? "SUCCESS" : "Failed to update supplier!";
    }

    // Delete Supplier
    public String deleteSupplier(int supplierId) {
        // Business Validation: Cannot delete if has pending orders
        boolean success = supplierDao.deleteSupplier(supplierId);
        return success ? "SUCCESS" : "Cannot delete supplier! Supplier has related medicines or orders.";
    }

    // Get All Suppliers
    public List<Supplier> getAllSuppliers() {
        return supplierDao.getAllSuppliers();
    }

    // Get Active Suppliers Only
    public List<Supplier> getActiveSuppliers() {
        List<Supplier> allSuppliers = supplierDao.getAllSuppliers();
        List<Supplier> active = new java.util.ArrayList<>();
        
        for (Supplier s : allSuppliers) {
            if (s.getStatus().equalsIgnoreCase("active")) {
                active.add(s);
            }
        }
        return active;
    }

    // Helper: Check if supplier email exists
    private boolean isSupplierEmailExists(String email) {
        List<Supplier> suppliers = supplierDao.getAllSuppliers();
        for (Supplier s : suppliers) {
            if (s.getEmail().equalsIgnoreCase(email)) {
                return true;
            }
        }
        return false;
    }
}

