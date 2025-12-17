/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Medicine;

/**
 *
 * @author ken
 */
public class MedicineDao {

public class MedicineDAO {

    public boolean addMedicine(Medicine m) {
        String sql = "INSERT INTO medicines (medicine_name, description, unit_price, stock_quantity, reorder_level, supplier_id) "
                   + "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, m.getMedicineName());
            pst.setString(2, m.getDescription());
            pst.setDouble(3, m.getUnitPrice());
            pst.setInt(4, m.getStockQuantity());
            pst.setInt(5, m.getReorderLevel());
            pst.setInt(6, m.getSupplierId());

            return pst.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Medicine> getAllMedicines() {
        List<Medicine> list = new ArrayList<>();
        String sql = "SELECT * FROM medicines";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                Medicine m = new Medicine(
                    rs.getInt("medicine_id"),
                    rs.getString("medicine_name"),
                    rs.getString("description"),
                    rs.getDouble("unit_price"),
                    rs.getInt("stock_quantity"),
                    rs.getInt("reorder_level"),
                    rs.getInt("supplier_id")
                );
                list.add(m);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean updateMedicine(Medicine m) {
        String sql = "UPDATE medicines SET medicine_name=?, description=?, unit_price=?, stock_quantity=?, reorder_level=?, supplier_id=? "
                   + "WHERE medicine_id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, m.getMedicineName());
            pst.setString(2, m.getDescription());
            pst.setDouble(3, m.getUnitPrice());
            pst.setInt(4, m.getStockQuantity());
            pst.setInt(5, m.getReorderLevel());
            pst.setInt(6, m.getSupplierId());
            pst.setInt(7, m.getMedicineId());

            return pst.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteMedicine(int id) {
        String sql = "DELETE FROM medicines WHERE medicine_id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, id);
            return pst.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}

}
