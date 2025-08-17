package com.eventhive.controller;


import com.eventhive.dao.UserDAO;
import com.eventhive.model.User;
import com.eventhive.util.PasswordUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    private UserDAO userDAO;

    @Override
    public void init() throws ServletException {
    
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/register.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (this.userDAO == null) {
            this.userDAO = new UserDAO();
        }

        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String role = request.getParameter("role");

        try {
            if (name == null || email == null || password == null || role == null ||
                    name.trim().isEmpty() || email.trim().isEmpty() || password.trim().isEmpty()) {

                request.setAttribute("errorMessage", "All fields are required. Please fill them all out.");
                request.getRequestDispatcher("/register.jsp").forward(request, response);
                return;
            }

            if (userDAO.findByEmail(email) != null) {
                request.setAttribute("errorMessage", "An account with this email address already exists. Please log in.");
                request.getRequestDispatcher("/register.jsp").forward(request, response);
                return;
            }

            User newUser = new User(name, email, password, role);


            userDAO.createUser(newUser);

            response.sendRedirect(request.getContextPath() + "/login.jsp?success=true");

        } catch (SQLException e) {
        
            e.printStackTrace();
            request.setAttribute("errorMessage", "A database error occurred during registration. Please try again later.");
            request.getRequestDispatcher("/register.jsp").forward(request, response);
        }
    }
}