package com.eventhive.controller;

import com.eventhive.dao.EventDAO;
import com.eventhive.dao.UserDAO;
import com.eventhive.model.Event;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("")
public class HomeServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            EventDAO eventDAO = new EventDAO();
            UserDAO userDAO = new UserDAO();

            // 1. Fetch ALL upcoming events for the grid below the header
            List<Event> allUpcomingEvents = eventDAO.getAllUpcomingEvents();
            request.setAttribute("events", allUpcomingEvents);

            List<Event> featuredEvents = eventDAO.getFeaturedEvents(5);
            request.setAttribute("featuredEvents", featuredEvents);

            int totalEvents = eventDAO.getEventCount();
            int totalUsers = userDAO.getUserCount();

            request.setAttribute("totalEvents", totalEvents);
            request.setAttribute("totalUsers", totalUsers);

        } catch (SQLException e) {
            System.err.println("Database error on homepage: " + e.getMessage());
            e.printStackTrace();
        }

        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }

}