package com.eventhive.controller;

import com.eventhive.dao.UserDAO;
import com.eventhive.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/admin/manage-user")
public class UserManagementServlet extends HttpServlet {

    private UserDAO userDAO;

    @Override
    public void init() {
        userDAO = new UserDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        User currentUser = (session != null) ? (User) session.getAttribute("user") : null;
        if (currentUser == null || !"Admin".equals(currentUser.getRole())) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        String action = request.getParameter("action");

        if ("deleteUser".equals(action)) {
            try {
                int userIdToDelete = Integer.parseInt(request.getParameter("userId"));
                if (userIdToDelete == currentUser.getId()) {
                    session.setAttribute("errorMessage", "Error: You cannot delete your own account.");
                } else {
                    userDAO.deleteUser(userIdToDelete);
                    session.setAttribute("message", "User has been successfully deleted.");
                }

            } catch (NumberFormatException e) {
                session.setAttribute("errorMessage", "Error: Invalid user ID format.");
            } catch (SQLException e) {
                session.setAttribute("errorMessage", "Database error: Could not delete user.");
                e.printStackTrace();
            }
        }

        response.sendRedirect(request.getContextPath() + "/dashboard");
    }
}