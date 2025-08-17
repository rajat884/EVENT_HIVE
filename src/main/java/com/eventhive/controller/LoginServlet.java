package com.eventhive.controller;

// Assume User, UserDAO, PasswordUtil exist
import com.eventhive.dao.UserDAO;
import com.eventhive.model.User;
import com.eventhive.util.PasswordUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private UserDAO userDAO;


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/login.jsp").forward(request, response);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {


        UserDAO userDAO = new UserDAO();

        String email = request.getParameter("email");
        String password = request.getParameter("password");



        try {
            User user = userDAO.findByEmail(email);

            if (user == null) {
                System.out.println("DEBUG: User object is NULL. No user found in database with that email.");
            } else {

                boolean passwordMatch = PasswordUtil.checkPassword(password, user.getPassword());

                if (passwordMatch) {

                    HttpSession session = request.getSession();
                    session.setAttribute("user", user);
                    response.sendRedirect(request.getContextPath() + "/dashboard");
                    return;
                }
            }

            request.setAttribute("errorMessage", "Invalid email or password. Please try again.");
            request.getRequestDispatcher("/login.jsp").forward(request, response);

        } catch (SQLException e) {
            System.out.println("!!!! DATABASE ERROR !!!!");
            e.printStackTrace();
            request.setAttribute("errorMessage", "A database error occurred. Please try again later.");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        }
    }
}