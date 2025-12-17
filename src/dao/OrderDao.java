/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Order;
/**
 *
 * @author ken
 */
public class OrderDao {
    
     public boolean addOrder(Order order) {
        String sql = "INSERT INTO orders (order_number, user_id, supplier_id, order_date, total_amount, status) "
                   + "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, order.getOrderNumber());
            pst.setInt(2, order.getUserId());
            pst.setInt(3, order.getSupplierId());
            pst.setDate(4, java.sql.Date.valueOf(order.getOrderDate()));
            pst.setDouble(5, order.getTotalAmount());
            pst.setString(6, order.getStatus());

            return pst.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // READ
    public List<Order> getAllOrders() {
        List<Order> list = new ArrayList<>();
        String sql = "SELECT * FROM orders ORDER BY order_id DESC";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                Order o = new Order(
                    rs.getInt("order_id"),
                    rs.getString("order_number"),
                    rs.getInt("user_id"),
                    rs.getInt("supplier_id"),
                    rs.getString("order_date"),
                    rs.getDouble("total_amount"),
                    rs.getString("status")
                );
                list.add(o);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    // READ BY ID
    public Order getOrderById(int orderId) {
        String sql = "SELECT * FROM orders WHERE order_id = ?";
        Order order = null;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, orderId);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                order = new Order(
                    rs.getInt("order_id"),
                    rs.getString("order_number"),
                    rs.getInt("user_id"),
                    rs.getInt("supplier_id"),
                    rs.getString("order_date"),
                    rs.getDouble("total_amount"),
                    rs.getString("status")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return order;
    }

    // UPDATE
    public boolean updateOrder(Order order) {
        String sql = "UPDATE orders SET order_number=?, user_id=?, supplier_id=?, "
                   + "order_date=?, total_amount=?, status=? WHERE order_id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, order.getOrderNumber());
            pst.setInt(2, order.getUserId());
            pst.setInt(3, order.getSupplierId());
            pst.setDate(4, java.sql.Date.valueOf(order.getOrderDate()));
            pst.setDouble(5, order.getTotalAmount());
            pst.setString(6, order.getStatus());
            pst.setInt(7, order.getOrderId());

            return pst.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // DELETE
    public boolean deleteOrder(int orderId) {
        String sql = "DELETE FROM orders WHERE order_id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, orderId);
            return pst.executeUpdate() > 0;

        } catch (SQLException e) {
            // May fail if order has related inventory records
            e.printStackTrace();
            return false;
        }
    }

    // Get orders by status
    public List<Order> getOrdersByStatus(String status) {
        List<Order> list = new ArrayList<>();
        String sql = "SELECT * FROM orders WHERE status = ? ORDER BY order_date DESC";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, status);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                Order o = new Order(
                    rs.getInt("order_id"),
                    rs.getString("order_number"),
                    rs.getInt("user_id"),
                    rs.getInt("supplier_id"),
                    rs.getString("order_date"),
                    rs.getDouble("total_amount"),
                    rs.getString("status")
                );
                list.add(o);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    // Get orders by supplier
    public List<Order> getOrdersBySupplier(int supplierId) {
        List<Order> list = new ArrayList<>();
        String sql = "SELECT * FROM orders WHERE supplier_id = ? ORDER BY order_date DESC";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, supplierId);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                Order o = new Order(
                    rs.getInt("order_id"),
                    rs.getString("order_number"),
                    rs.getInt("user_id"),
                    rs.getInt("supplier_id"),
                    rs.getString("order_date"),
                    rs.getDouble("total_amount"),
                    rs.getString("status")
                );
                list.add(o);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }
}
