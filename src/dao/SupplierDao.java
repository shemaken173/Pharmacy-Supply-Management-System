/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Supplier;
/**
 *
 * @author ken
 */
public class SupplierDao {

public class SupplierDAO {

    // CREATE
    public boolean addSupplier(Supplier s) {
        String sql = "INSERT INTO suppliers (supplier_name, contact_person, phone_number, email, address, status) "
                   + "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, s.getSupplierName());
            pst.setString(2, s.getContactPerson());
            pst.setString(3, s.getPhoneNumber());
            pst.setString(4, s.getEmail());
            pst.setString(5, s.getAddress());
            pst.setString(6, s.getStatus());

            return pst.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // READ
    public List<Supplier> getAllSuppliers() {
        List<Supplier> list = new ArrayList<>();
        String sql = "SELECT * FROM suppliers ORDER BY supplier_id DESC";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                Supplier s = new Supplier(
                    rs.getInt("supplier_id"),
                    rs.getString("supplier_name"),
                    rs.getString("contact_person"),
                    rs.getString("phone_number"),
                    rs.getString("email"),
                    rs.getString("address"),
                    rs.getString("status")
                );
                list.add(s);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    // UPDATE
    public boolean updateSupplier(Supplier s) {
        String sql = "UPDATE suppliers SET supplier_name=?, contact_person=?, phone_number=?, email=?, address=?, status=? "
                   + "WHERE supplier_id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, s.getSupplierName());
            pst.setString(2, s.getContactPerson());
            pst.setString(3, s.getPhoneNumber());
            pst.setString(4, s.getEmail());
            pst.setString(5, s.getAddress());
            pst.setString(6, s.getStatus());
            pst.setInt(7, s.getSupplierId());

            return pst.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // DELETE
    public boolean deleteSupplier(int id) {
        String sql = "DELETE FROM suppliers WHERE supplier_id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, id);
            return pst.executeUpdate() > 0;

        } catch (SQLException e) {
            // Foreign key restriction → cannot delete supplier with medicines/orders
            e.printStackTrace();
            return false;
        }
    }
}
}


