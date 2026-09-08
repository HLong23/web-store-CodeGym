package com.codegym.dao;

import com.codegym.model.Product;
import com.codegym.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {

    /**
     * Yêu cầu 2: lấy toàn bộ danh sách sản phẩm
     */
    public List<Product> findAll() {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT id, ten_san_pham, gia, giam_gia, ton_kho FROM san_pham ORDER BY id";
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

    /**
     * Yêu cầu 3: thêm mới sản phẩm
     */
    public boolean insert(Product p) {
        String sql = "INSERT INTO san_pham (ten_san_pham, gia, giam_gia, ton_kho) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, p.getName());
            ps.setDouble(2, p.getPrice());
            ps.setInt(3, p.getDiscount());
            ps.setInt(4, p.getStock());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Yêu cầu 4: top N sản phẩm được đặt hàng nhiều nhất
     * (đếm tổng số lượng đặt trong bảng chi_tiet_don_hang)
     */
    public List<Product> findTopOrdered(int top) {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT sp.id, sp.ten_san_pham, sp.gia, sp.giam_gia, sp.ton_kho, " +
                "COALESCE(SUM(ctdh.so_luong), 0) AS so_lan_dat " +
                "FROM san_pham sp " +
                "LEFT JOIN chi_tiet_don_hang ctdh ON ctdh.san_pham_id = sp.id " +
                "GROUP BY sp.id, sp.ten_san_pham, sp.gia, sp.giam_gia, sp.ton_kho " +
                "ORDER BY so_lan_dat DESC " +
                "LIMIT ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, top);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Product p = mapRow(rs);
                    p.setOrderCount(rs.getLong("so_lan_dat"));
                    list.add(p);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    /**
     * Yêu cầu 5: danh sách sản phẩm được đặt hàng trong khoảng thời gian [from, to]
     * (dựa trên ngay_dat_hang của don_hang)
     */
    public List<Product> findOrderedBetween(Date from, Date to) {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT DISTINCT sp.id, sp.ten_san_pham, sp.gia, sp.giam_gia, sp.ton_kho " +
                "FROM san_pham sp " +
                "JOIN chi_tiet_don_hang ctdh ON ctdh.san_pham_id = sp.id " +
                "JOIN don_hang dh ON dh.id = ctdh.don_hang_id " +
                "WHERE dh.ngay_dat_hang BETWEEN ? AND ? " +
                "ORDER BY sp.id";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDate(1, from);
            ps.setDate(2, to);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapRow(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    private Product mapRow(ResultSet rs) throws SQLException {
        return new Product(
                rs.getInt("id"),
                rs.getString("ten_san_pham"),
                rs.getDouble("gia"),
                rs.getInt("giam_gia"),
                rs.getInt("ton_kho")
        );
    }
}
