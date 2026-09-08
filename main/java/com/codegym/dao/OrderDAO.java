package com.codegym.dao;

import com.codegym.model.*;
import com.codegym.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO {

    public List<Order> findAll() {
        List<Order> list = new ArrayList<>();
        String sql = "SELECT dh.id, dh.phuong_thuc_thanh_toan, dh.khach_hang_id, dh.nhan_vien_id, " +
                "dh.ngay_dat_hang, dh.ngay_giao_hang, dh.dia_chi_giao_hang, " +
                "kh.ten_khach_hang, nv.ten_nhan_vien " +
                "FROM don_hang dh " +
                "JOIN khach_hang kh ON dh.khach_hang_id = kh.id " +
                "JOIN nhan_vien nv ON dh.nhan_vien_id = nv.id " +
                "ORDER BY dh.id";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public Order findById(int id) {
        String sql = "SELECT dh.id, dh.phuong_thuc_thanh_toan, dh.khach_hang_id, dh.nhan_vien_id, " +
                "dh.ngay_dat_hang, dh.ngay_giao_hang, dh.dia_chi_giao_hang, " +
                "kh.ten_khach_hang, nv.ten_nhan_vien " +
                "FROM don_hang dh " +
                "JOIN khach_hang kh ON dh.khach_hang_id = kh.id " +
                "JOIN nhan_vien nv ON dh.nhan_vien_id = nv.id " +
                "WHERE dh.id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public List<OrderDetail> findOrderDetails(int orderId) {
        List<OrderDetail> list = new ArrayList<>();
        String sql = "SELECT ctdh.id, ctdh.don_hang_id, ctdh.san_pham_id, ctdh.so_luong, " +
                "sp.ten_san_pham, sp.gia, sp.giam_gia, sp.ton_kho " +
                "FROM chi_tiet_don_hang ctdh " +
                "JOIN san_pham sp ON ctdh.san_pham_id = sp.id " +
                "WHERE ctdh.don_hang_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, orderId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapOrderDetailRow(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public boolean insert(Order order) {
        String sql = "INSERT INTO don_hang (phuong_thuc_thanh_toan, khach_hang_id, nhan_vien_id, ngay_dat_hang, ngay_giao_hang, dia_chi_giao_hang) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, order.getPaymentMethod());
            ps.setInt(2, order.getCustomerId());
            ps.setInt(3, order.getEmployeeId());
            ps.setDate(4, Date.valueOf(order.getOrderDate()));
            ps.setDate(5, order.getDeliveryDate() != null ? Date.valueOf(order.getDeliveryDate()) : null);
            ps.setString(6, order.getDeliveryAddress());
            
            int affectedRows = ps.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        order.setId(generatedKeys.getInt(1));
                    }
                }
                return true;
            }
            return false;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean insertOrderDetail(OrderDetail orderDetail) {
        String sql = "INSERT INTO chi_tiet_don_hang (don_hang_id, san_pham_id, so_luong) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, orderDetail.getOrderId());
            ps.setInt(2, orderDetail.getProductId());
            ps.setInt(3, orderDetail.getQuantity());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM don_hang WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Order mapRow(ResultSet rs) throws SQLException {
        Order order = new Order(
                rs.getInt("id"),
                rs.getString("phuong_thuc_thanh_toan"),
                rs.getInt("khach_hang_id"),
                rs.getInt("nhan_vien_id"),
                rs.getDate("ngay_dat_hang").toLocalDate(),
                rs.getDate("ngay_giao_hang") != null ? rs.getDate("ngay_giao_hang").toLocalDate() : null,
                rs.getString("dia_chi_giao_hang")
        );
        
        Customer customer = new Customer();
        customer.setId(rs.getInt("khach_hang_id"));
        customer.setName(rs.getString("ten_khach_hang"));
        order.setCustomer(customer);
        
        Employee employee = new Employee();
        employee.setId(rs.getInt("nhan_vien_id"));
        employee.setName(rs.getString("ten_nhan_vien"));
        order.setEmployee(employee);
        
        return order;
    }

    private OrderDetail mapOrderDetailRow(ResultSet rs) throws SQLException {
        OrderDetail detail = new OrderDetail(
                rs.getInt("id"),
                rs.getInt("don_hang_id"),
                rs.getInt("san_pham_id"),
                rs.getInt("so_luong")
        );
        
        Product product = new Product(
                rs.getInt("san_pham_id"),
                rs.getString("ten_san_pham"),
                rs.getDouble("gia"),
                rs.getInt("giam_gia"),
                rs.getInt("ton_kho")
        );
        detail.setProduct(product);
        
        return detail;
    }
}
