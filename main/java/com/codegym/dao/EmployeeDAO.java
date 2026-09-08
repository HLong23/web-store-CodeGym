package com.codegym.dao;

import com.codegym.model.Employee;
import com.codegym.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAO {

    public List<Employee> findAll() {
        List<Employee> list = new ArrayList<>();
        String sql = "SELECT id, ten_nhan_vien, ngay_sinh, dia_chi FROM nhan_vien ORDER BY id";
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

    public Employee findById(int id) {
        String sql = "SELECT id, ten_nhan_vien, ngay_sinh, dia_chi FROM nhan_vien WHERE id = ?";
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

    public boolean insert(Employee employee) {
        String sql = "INSERT INTO nhan_vien (ten_nhan_vien, ngay_sinh, dia_chi) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, employee.getName());
            ps.setDate(2, Date.valueOf(employee.getBirthDate()));
            ps.setString(3, employee.getAddress());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean update(Employee employee) {
        String sql = "UPDATE nhan_vien SET ten_nhan_vien = ?, ngay_sinh = ?, dia_chi = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, employee.getName());
            ps.setDate(2, Date.valueOf(employee.getBirthDate()));
            ps.setString(3, employee.getAddress());
            ps.setInt(4, employee.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM nhan_vien WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Employee mapRow(ResultSet rs) throws SQLException {
        return new Employee(
                rs.getInt("id"),
                rs.getString("ten_nhan_vien"),
                rs.getDate("ngay_sinh").toLocalDate(),
                rs.getString("dia_chi")
        );
    }
}
