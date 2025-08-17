package com.eventhive.controller;

import com.eventhive.dao.RegistrationDAO;
import com.eventhive.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/registration-manager")
public class RegistrationServlet extends HttpServlet {
    private RegistrationDAO registrationDAO;

    @Override
    public void init() {
        registrationDAO = new RegistrationDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        User user = (User) request.getSession().getAttribute("user");
        if (user == null || !"Attendee".equals(user.getRole())) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        int eventId = Integer.parseInt(request.getParameter("eventId"));
        int attendeeId = user.getId();

        try {
            if ("register".equals(action)) {
                registrationDAO.createRegistration(eventId, attendeeId);
                request.getSession().setAttribute("message", "Successfully registered for the event!");
            } else if ("cancel".equals(action)) {
                registrationDAO.cancelRegistration(eventId, attendeeId);
                request.getSession().setAttribute("message", "Your registration has been cancelled.");
            }
        } catch (SQLException e) {
            request.getSession().setAttribute("errorMessage", "A database error occurred. Please try again.");
            e.printStackTrace(); // Log error
        }

        response.sendRedirect(request.getContextPath() + "/dashboard");
    }
}