/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.MedicineDao;
import dao.MedicineDao.MedicineDAO;
import model.Medicine;
import utils.Validator;
import java.util.List;
/**
 *
 * @author ken
 */
public class MedicineController {
    private MedicineDAO medicineDao;
    private Validator validator;

    public MedicineController() {
        MedicineDao parent = new MedicineDao();
        this.medicineDao = parent.new MedicineDAO();
        this.validator = new Validator();
    }

    // Add Medicine with Validations
    public String addMedicine(Medicine medicine) {
        // Technical Validations
        if (medicine.getMedicineName().trim().isEmpty()) {
            return "Medicine name cannot be empty!";
        }
        if (!validator.isPositiveDecimal(medicine.getUnitPrice())) {
            return "Unit price must be greater than 0!";
        }
        if (!validator.isNonNegativeInteger(medicine.getStockQuantity())) {
            return "Stock quantity cannot be negative!";
        }
        if (!validator.isNonNegativeInteger(medicine.getReorderLevel())) {
            return "Reorder level cannot be negative!";
        }

        // Business Validations
        if (isMedicineNameExists(medicine.getMedicineName())) {
            return "Medicine name already exists!";
        }
        if (medicine.getReorderLevel() > medicine.getStockQuantity()) {
            return "Warning: Stock is below reorder level!";
        }

        boolean success = medicineDao.addMedicine(medicine);
        return success ? "SUCCESS" : "Failed to add medicine!";
    }

    // Update Medicine
    public String updateMedicine(Medicine medicine) {
        // Technical Validations
        if (!validator.isPositiveDecimal(medicine.getUnitPrice())) {
            return "Unit price must be greater than 0!";
        }
        if (!validator.isNonNegativeInteger(medicine.getStockQuantity())) {
            return "Stock quantity cannot be negative!";
        }

        boolean success = medicineDao.updateMedicine(medicine);
        return success ? "SUCCESS" : "Failed to update medicine!";
    }

    // Delete Medicine
    public String deleteMedicine(int medicineId) {
        // Business Validation: Cannot delete if used in inventory
        boolean success = medicineDao.deleteMedicine(medicineId);
        return success ? "SUCCESS" : "Cannot delete medicine! It has related inventory records.";
    }

    // Get All Medicines
    public List<Medicine> getAllMedicines() {
        return medicineDao.getAllMedicines();
    }

    // Get Low Stock Medicines
    public List<Medicine> getLowStockMedicines() {
        List<Medicine> allMedicines = medicineDao.getAllMedicines();
        List<Medicine> lowStock = new java.util.ArrayList<>();
        
        for (Medicine m : allMedicines) {
            if (m.getStockQuantity() <= m.getReorderLevel()) {
                lowStock.add(m);
            }
        }
        return lowStock;
    }

    // Helper: Check if medicine name exists
    private boolean isMedicineNameExists(String medicineName) {
        List<Medicine> medicines = medicineDao.getAllMedicines();
        for (Medicine m : medicines) {
            if (m.getMedicineName().equalsIgnoreCase(medicineName)) {
                return true;
            }
        }
        return false;
    }

    // Update stock after order/sale
    public String updateStock(int medicineId, int quantity, String type) {
        List<Medicine> medicines = medicineDao.getAllMedicines();
        Medicine medicine = null;
        
        for (Medicine m : medicines) {
            if (m.getMedicineId() == medicineId) {
                medicine = m;
                break;
            }
        }
        
        if (medicine == null) {
            return "Medicine not found!";
        }
        
        if (type.equals("IN")) {
            medicine.setStockQuantity(medicine.getStockQuantity() + quantity);
        } else if (type.equals("OUT")) {
            if (medicine.getStockQuantity() < quantity) {
                return "Insufficient stock!";
            }
            medicine.setStockQuantity(medicine.getStockQuantity() - quantity);
        }
        
        boolean success = medicineDao.updateMedicine(medicine);
        return success ? "SUCCESS" : "Failed to update stock!";
    }
}

