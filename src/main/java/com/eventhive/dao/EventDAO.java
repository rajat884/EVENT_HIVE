package com.eventhive.dao;
import com.eventhive.model.Event;
import com.eventhive.db.DatabaseManager;
import com.eventhive.model.Event;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EventDAO {

    public Event createEvent(Event event) throws SQLException {
        String sql = "INSERT INTO events (title, description, date, time, venue, price, category, coverImagePath, organizerId) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, event.getTitle());
            pstmt.setString(2, event.getDescription());
            pstmt.setDate(3, event.getDate());
            pstmt.setTime(4, event.getTime());
            pstmt.setString(5, event.getVenue());
            pstmt.setBigDecimal(6, event.getPrice());
            pstmt.setString(7, event.getCategory());
            pstmt.setString(8, event.getCoverImagePath());
            pstmt.setInt(9, event.getOrganizerId());

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) throw new SQLException("Creating event failed, no rows affected.");

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    event.setId(generatedKeys.getInt(1));
                    return event;
                } else {
                    throw new SQLException("Creating event failed, no ID obtained.");
                }
            }
        }
    }

    public Event findById(int eventId) throws SQLException {
        String sql = "SELECT * FROM events WHERE id = ?";
        Event event = null;
        try (Connection conn = DatabaseManager.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, eventId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                event = mapRowToEvent(rs);
            }
        }
        return event;
    }

    public List<Event> getAllUpcomingEvents() throws SQLException {
        List<Event> events = new ArrayList<>();
        String sql = "SELECT * FROM events WHERE date >= CURDATE() ORDER BY date, time ASC";
        try (Connection conn = DatabaseManager.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                events.add(mapRowToEvent(rs));
            }
        }
        return events;
    }

    public void updateEvent(Event event) throws SQLException {
        String sql = "UPDATE events SET title=?, description=?, date=?, time=?, venue=?, price=?, category=? WHERE id=?";
        try (Connection conn = DatabaseManager.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, event.getTitle());
            pstmt.setString(2, event.getDescription());
            pstmt.setDate(3, event.getDate());
            pstmt.setTime(4, event.getTime());
            pstmt.setString(5, event.getVenue());
            pstmt.setBigDecimal(6, event.getPrice());
            pstmt.setString(7, event.getCategory());
            pstmt.setInt(8, event.getId());
            pstmt.executeUpdate();
        }
    }

    public List<Event> getUpcomingEventsByOrganizer(int organizerId) throws SQLException {
        List<Event> events = new ArrayList<>();
        String sql = "SELECT e.*, COUNT(r.id) as attendee_count FROM events e " +
                "LEFT JOIN registrations r ON e.id = r.eventId " +
                "WHERE e.organizerId = ? AND e.date >= CURDATE() " +
                "GROUP BY e.id " +
                "ORDER BY e.date, e.time ASC";
        try (Connection conn = DatabaseManager.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, organizerId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Event event = mapRowToEvent(rs);
                int attendeeCount = rs.getInt("attendee_count");
                // Calculate revenue
                BigDecimal revenue = event.getPrice().multiply(new BigDecimal(attendeeCount));
                event.setRevenue(revenue);
                events.add(event);
            }
        }
        return events;
    }

    public List<Event> getPastEventsByOrganizer(int organizerId) throws SQLException {
        List<Event> events = new ArrayList<>();
        String sql = "SELECT e.*, COUNT(r.id) as attendee_count FROM events e " +
                "LEFT JOIN registrations r ON e.id = r.eventId " +
                "WHERE e.organizerId = ? AND e.date < CURDATE() " +
                "GROUP BY e.id " +
                "ORDER BY e.date DESC, e.time DESC";
        try (Connection conn = DatabaseManager.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, organizerId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Event event = mapRowToEvent(rs);
                int attendeeCount = rs.getInt("attendee_count");
                BigDecimal revenue = event.getPrice().multiply(new BigDecimal(attendeeCount));
                event.setRevenue(revenue);
                events.add(event);
            }
        }
        return events;
    }


    public BigDecimal getRevenueByOrganizer(int organizerId) throws SQLException {
        String sql = "SELECT SUM(e.price) FROM events e " +
                "JOIN registrations r ON e.id = r.eventId " +
                "WHERE e.organizerId = ?";
        try (Connection conn = DatabaseManager.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, organizerId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                BigDecimal total = rs.getBigDecimal(1);
                return (total == null) ? BigDecimal.ZERO : total;
            }
        }
        return BigDecimal.ZERO;
    }

    public void deleteEvent(int eventId) throws SQLException {
        String sql = "DELETE FROM events WHERE id = ?";
        try (Connection conn = DatabaseManager.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, eventId);
            pstmt.executeUpdate();
        }
    }

    private Event mapRowToEvent(ResultSet rs) throws SQLException {
        return new Event(
                rs.getInt("id"),
                rs.getString("title"),
                rs.getString("description"),
                rs.getDate("date"),
                rs.getTime("time"),
                rs.getString("venue"),
                rs.getBigDecimal("price"),
                rs.getString("category"),
                rs.getString("coverImagePath"),
                rs.getInt("organizerId")
        );
    }
    public List<Event> findUpcomingRegisteredEvents(int attendeeId) throws SQLException {


        List<Event> events = new ArrayList<>();
        String sql = "SELECT e.* FROM events e " +
                "JOIN registrations r ON e.id = r.eventId " +
                "WHERE r.attendeeId = ? AND e.date >= CURDATE() " +
                "ORDER BY e.date, e.time ASC";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, attendeeId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                events.add(mapRowToEvent(rs));
            }
        }

        return events;
    }
    public List<Event> findPastRegisteredEvents(int attendeeId) throws SQLException {


        List<Event> events = new ArrayList<>();
        String sql = "SELECT e.* FROM events e " +
                "JOIN registrations r ON e.id = r.eventId " +
                "WHERE r.attendeeId = ? AND e.date < CURDATE() " +
                "ORDER BY e.date DESC, e.time DESC";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, attendeeId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                events.add(mapRowToEvent(rs));
            }
        }

        return events;
    }

    public List<Event> getFeaturedEvents(int limit) throws SQLException {
        List<Event> events = new ArrayList<>();
        String sql = "SELECT * FROM events WHERE date >= CURDATE() ORDER BY date, time ASC LIMIT ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, limit);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                events.add(mapRowToEvent(rs));
            }
        }
        return events;
    }
    public int getEventCount() throws SQLException {
        String sql = "SELECT COUNT(*) FROM events";
        try (Connection conn = DatabaseManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return 0;
    }
    public List<Event> getUpcomingEventsByOrganizerId(int organizerId) throws SQLException {
        List<Event> events = new ArrayList<>();
        // This query is now filtered by TWO conditions: the date AND the organizer's ID.
        String sql = "SELECT * FROM events WHERE organizerId = ? AND date >= CURDATE() ORDER BY date, time ASC";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, organizerId); // Set the organizerId parameter in the query
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                events.add(mapRowToEvent(rs));
            }
        }
        return events;
    }

    public List<Event> getPastEventsByOrganizerId(int organizerId) throws SQLException {
        List<Event> events = new ArrayList<>();
        String sql = "SELECT * FROM events WHERE organizerId = ? AND date < CURDATE() ORDER BY date DESC, time DESC";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, organizerId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                events.add(mapRowToEvent(rs));
            }
        }
        return events;
    }
}