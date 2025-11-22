package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler {
    private static final String DB_URL = "jdbc:sqlite:firms_db.db";

    public void initDB() {
        String sql = "CREATE TABLE IF NOT EXISTS firms (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT NOT NULL, " +
                "employeeCount INTEGER, " +
                "annualRevenue REAL, " +
                "industry TEXT)";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Firm> getAllFirms() {
        List<Firm> list = new ArrayList<>();
        String sql = "SELECT * FROM firms";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Firm(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("employeeCount"),
                        rs.getDouble("annualRevenue"),
                        rs.getString("industry")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void addFirm(Firm firm) {
        String sql = "INSERT INTO firms(name, employeeCount, annualRevenue, industry) VALUES(?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, firm.getName());
            pstmt.setInt(2, firm.getEmployeeCount());
            pstmt.setDouble(3, firm.getAnnualRevenue());
            pstmt.setString(4, firm.getIndustry());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteFirm(int id) {
        String sql = "DELETE FROM firms WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void clearAll() {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {
            stmt.execute("DELETE FROM firms");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}