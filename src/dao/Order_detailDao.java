/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Order_detail;


/**
 *
 * @author ken
 */
public class Order_detailDao {



    // CREATE
    public boolean addOrderDetail(Order_detail d) {
        String sql = "INSERT INTO order_details (order_id, medicine_id, quantity, unit_price_at_order) "
                   + "VALUES (?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, d.getOrderId());
            pst.setInt(2, d.getMedicineId());
            pst.setInt(3, d.getQuantity());
            pst.setDouble(4, d.getUnitPriceAtOrder());

            return pst.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace(); 
            return false;
        }
    }

    // READ 
    public List<Order_detail> getOrderDetailsByOrder(int orderId) {
        List<Order_detail> list = new ArrayList<>();

        String sql = "SELECT * FROM order_details WHERE order_id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, orderId);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                Order_detail d = new Order_detail(
                    rs.getInt("order_detail_id"),
                    rs.getInt("order_id"),
                    rs.getInt("medicine_id"),
                    rs.getInt("quantity"),
                    rs.getDouble("unit_price_at_order")
                );
                list.add(d);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    // UPDATE
    public boolean updateOrderDetail(Order_detail d) {
        String sql = "UPDATE order_details SET quantity=?, unit_price_at_order=? "
                   + "WHERE order_detail_id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, d.getQuantity());
            pst.setDouble(2, d.getUnitPriceAtOrder());
            pst.setInt(3, d.getOrderDetailId());

            return pst.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // DELETE
    public boolean deleteOrderDetail(int id) {
        String sql = "DELETE FROM order_details WHERE order_detail_id=?";

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
