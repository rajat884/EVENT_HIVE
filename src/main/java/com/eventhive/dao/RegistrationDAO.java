package com.eventhive.dao;

import com.eventhive.db.DatabaseManager;
import com.eventhive.model.Registration;

import java.math.BigDecimal;
import java.sql.*;

public class RegistrationDAO {

    public Registration createRegistration(int eventId, int attendeeId) throws SQLException {
        String sql = "INSERT INTO registrations (eventId, attendeeId, registrationDate) VALUES (?, ?, NOW())";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, eventId);
            pstmt.setInt(2, attendeeId);

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating registration failed, no rows affected.");
            }

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    Registration reg = new Registration();
                    reg.setId(generatedKeys.getInt(1));
                    reg.setEventId(eventId);
                    reg.setAttendeeId(attendeeId);

                    reg.setRegistrationDate(new Timestamp(System.currentTimeMillis()));
                    return reg;
                } else {
                    throw new SQLException("Creating registration failed, no ID obtained.");
                }
            }
        }
    }

    public int getRegistrationCountForEvent(int eventId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM registrations WHERE eventId = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, eventId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1); // The first column of the result is the count
                }
            }
        }
        return 0;
    }

    public BigDecimal getTotalPlatformRevenue() throws SQLException {
        String sql = "SELECT SUM(e.price) AS total_revenue " +
                "FROM registrations r " +
                "JOIN events e ON r.eventId = e.id";

        try (Connection conn = DatabaseManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                BigDecimal total = rs.getBigDecimal("total_revenue");
                return total == null ? BigDecimal.ZERO : total;
            }
        }
        return BigDecimal.ZERO;
    }

    public void cancelRegistration(int eventId, int attendeeId) throws SQLException {
        String sql = "DELETE FROM registrations WHERE eventId = ? AND attendeeId = ?";
        try (Connection conn = DatabaseManager.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, eventId);
            pstmt.setInt(2, attendeeId);
            pstmt.executeUpdate();
        }
    }

}