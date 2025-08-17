package com.eventhive.controller;
import com.eventhive.dao.RegistrationDAO;
import com.eventhive.model.Event;
import com.eventhive.dao.EventDAO;
import com.eventhive.dao.UserDAO;
import com.eventhive.model.User;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {

    private UserDAO userDAO;
    private EventDAO eventDAO;

    private RegistrationDAO registrationDAO;

    @Override
    public void init() {
        userDAO = new UserDAO();
        eventDAO = new EventDAO();
        registrationDAO = new RegistrationDAO();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);

        if (session == null || session.getAttribute("user") == null) {
            resp.sendRedirect(req.getContextPath() + "/login.jsp");
            return;
        }

        User user = (User) session.getAttribute("user");
        String role = user.getRole();

        try {
            switch (role) {
                case "Admin":
                    loadAdminDashboard(req, resp);
                    break;
                case "Organizer":
                    loadOrganizerDashboard(req, resp, user.getId());
                    break;
                case "Attendee":
                    loadAttendeeDashboard(req, resp, user.getId());
                    break;
                default:
                    session.invalidate();
                    resp.sendRedirect(req.getContextPath() + "/login.jsp");
                    break;
            }
        } catch (SQLException e) {
            throw new ServletException("Database error loading dashboard.", e);
        }
    }

    private void loadAdminDashboard(HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        // These are for the default view
        List<User> organizers = userDAO.getAllUsersByRole("Organizer");
        List<User> attendees = userDAO.getAllUsersByRole("Attendee");
        List<Event> allEvents = eventDAO.getAllUpcomingEvents();
        BigDecimal totalPlatformRevenue = registrationDAO.getTotalPlatformRevenue();

        req.setAttribute("organizerList", organizers);
        req.setAttribute("attendeeList", attendees);
        req.setAttribute("eventList", allEvents); // This is for the stats card
        req.setAttribute("totalPlatformRevenue", totalPlatformRevenue);

        req.setAttribute("allPlatformEvents", allEvents);

        req.getRequestDispatcher("/admin/dashboard.jsp").forward(req, resp);
    }
    private void loadOrganizerDashboard(HttpServletRequest req, HttpServletResponse resp, int organizerId) throws SQLException, ServletException, IOException {

        // Check if the user is asking for the special 'revenue' view
        String view = req.getParameter("view");

        // ================== THE FIX IS HERE ==================
        // Instead of getting ALL events, we now call our new methods to get events
        // ONLY for the currently logged-in organizer.
        List<Event> upcomingEvents = eventDAO.getUpcomingEventsByOrganizerId(organizerId);
        List<Event> pastEvents = eventDAO.getPastEventsByOrganizerId(organizerId);
        // ======================================================

        // Create a combined list for the revenue page calculation
        List<Event> allOrganizerEvents = new ArrayList<>(upcomingEvents);
        allOrganizerEvents.addAll(pastEvents);

        // ----------------- HANDLE REVENUE PAGE REQUEST -----------------
        if ("revenue".equals(view)) {

            Map<Integer, BigDecimal> eventRevenueMap = new HashMap<>();
            BigDecimal totalOrganizerRevenue = BigDecimal.ZERO;

            for (Event event : allOrganizerEvents) {
                int registrationCount = registrationDAO.getRegistrationCountForEvent(event.getId());
                BigDecimal revenueForEvent = event.getPrice().multiply(new BigDecimal(registrationCount));
                eventRevenueMap.put(event.getId(), revenueForEvent);
                totalOrganizerRevenue = totalOrganizerRevenue.add(revenueForEvent);
            }

            req.setAttribute("organizerEvents", allOrganizerEvents); // Send the list of events
            req.setAttribute("eventRevenueMap", eventRevenueMap); // Send the revenue breakdown
            req.setAttribute("totalOrganizerRevenue", totalOrganizerRevenue); // Send the grand total

            req.getRequestDispatcher("/organizer/revenue.jsp").forward(req, resp);

        } else {
            // This is the default view, now with the correctly filtered lists.
            req.setAttribute("upcomingEvents", upcomingEvents);
            req.setAttribute("pastEvents", pastEvents);

            req.getRequestDispatcher("/organizer/dashboard.jsp").forward(req, resp);
        }
    }


    private void loadAttendeeDashboard(HttpServletRequest req, HttpServletResponse resp, int attendeeId) throws SQLException, ServletException, IOException {


        List<Event> myUpcomingEvents = eventDAO.findUpcomingRegisteredEvents(attendeeId);
        List<Event> myPastEvents = eventDAO.findPastRegisteredEvents(attendeeId);


        req.setAttribute("myUpcomingEvents", myUpcomingEvents);
        req.setAttribute("myPastEvents", myPastEvents);

        req.getRequestDispatcher("/attendee/dashboard.jsp").forward(req, resp);
    }
}