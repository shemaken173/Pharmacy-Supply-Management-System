/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Inventory;

/**
 *
 * @author ken
 */
public class InventoryDao {
    
    // CREATE
    public boolean addInventory(Inventory inv) {
        String sql = "INSERT INTO inventory (medicine_id, transaction_type, quantity, transaction_date, "
                   + "user_id, order_id, batch_number, remarks) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, inv.getMedicineId());
            pst.setString(2, inv.getTransactionType());
            pst.setInt(3, inv.getQuantity());
            pst.setTimestamp(4, java.sql.Timestamp.valueOf(inv.getTransactionDate()));
            pst.setInt(5, inv.getUserId());

            if (inv.getOrderId() == null)
                pst.setNull(6, Types.INTEGER);
            else
                pst.setInt(6, inv.getOrderId());

            pst.setString(7, inv.getBatchNumber());
            pst.setString(8, inv.getRemarks());

            return pst.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // READ
    public List<Inventory> getAllInventory() {
        List<Inventory> list = new ArrayList<>();
        String sql = "SELECT * FROM inventory ORDER BY inventory_id DESC";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                Inventory i = new Inventory(
                    rs.getInt("inventory_id"),
                    rs.getInt("medicine_id"),
                    rs.getString("transaction_type"),
                    rs.getInt("quantity"),
                    rs.getString("transaction_date"),
                    rs.getInt("user_id"),
                    rs.getObject("order_id", Integer.class),
                    rs.getString("batch_number"),
                    rs.getString("remarks")
                );
                list.add(i);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    // UPDATE
    public boolean updateInventory(Inventory i) {
        String sql = "UPDATE inventory SET medicine_id=?, transaction_type=?, quantity=?, "
                   + "transaction_date=?, user_id=?, order_id=?, batch_number=?, remarks=? "
                   + "WHERE inventory_id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, i.getMedicineId());
            pst.setString(2, i.getTransactionType());
            pst.setInt(3, i.getQuantity());
            pst.setTimestamp(4, java.sql.Timestamp.valueOf(i.getTransactionDate()));
            pst.setInt(5, i.getUserId());

            if (i.getOrderId() == null)
                pst.setNull(6, Types.INTEGER);
            else
                pst.setInt(6, i.getOrderId());

            pst.setString(7, i.getBatchNumber());
            pst.setString(8, i.getRemarks());
            pst.setInt(9, i.getInventoryId());

            return pst.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // DELETE
    public boolean deleteInventory(int id) {
        String sql = "DELETE FROM inventory WHERE inventory_id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, id);
            return pst.executeUpdate() > 0;

        } catch (SQLException e) {
            // order_id uses ON DELETE SET NULL → no FK issues
            e.printStackTrace();
            return false;
        }
    }
}

