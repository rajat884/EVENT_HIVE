package com.eventhive.controller;

import com.eventhive.dao.EventDAO;
import com.eventhive.dao.UserDAO;
import com.eventhive.model.Event;
import com.eventhive.model.User;
import com.eventhive.model.Event;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.nio.file.Paths;
import java.util.List;

@WebServlet("/event-manager")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024, // 1 MB
        maxFileSize = 1024 * 1024 * 10,      // 10 MB
        maxRequestSize = 1024 * 1024 * 50   // 50 MB
)
public class EventServlet extends HttpServlet {

    private EventDAO eventDAO;
    private static final String UPLOAD_DIR = "assets/images/event_covers";

    @Override
    public void init() {
        eventDAO = new EventDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            response.sendRedirect(request.getContextPath() + "/");
            return;
        }

        try {
            switch (action) {
                case "details":
                    showEventDetails(request, response);
                    break;
                case "showEditForm":
                    showEditForm(request, response);
                    break;

                case "viewAttendees":
                    int eventIdForAttendees = Integer.parseInt(request.getParameter("eventId"));
                    Event event = eventDAO.findById(eventIdForAttendees);

                    UserDAO userDAO = new UserDAO();
                    List<User> attendeeList = userDAO.findAttendeesByEvent(eventIdForAttendees);

                    request.setAttribute("event", event);
                    request.setAttribute("attendeeList", attendeeList);
                    request.getRequestDispatcher("/organizer/view_attendees.jsp").forward(request, response);
                    break;

                case "clone":
                    int eventIdToClone = Integer.parseInt(request.getParameter("eventId"));
                    Event eventToClone = eventDAO.findById(eventIdToClone);
                    request.setAttribute("eventToClone", eventToClone);
                    request.getRequestDispatcher("/organizer/create_event.jsp").forward(request, response);
                    break;

                default:
                    response.sendRedirect(request.getContextPath() + "/");
                    break;
            }
        } catch (SQLException e) {
            throw new ServletException("Database error in EventServlet GET", e);
        }
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            switch (action) {
                case "create":
                    createEvent(request, response);
                    break;
                case "update":
                    updateEvent(request, response);
                    break;
                case "cancel":
                    cancelEvent(request, response);
                    break;
                case "delete":

                    deleteEvent(request, response);
                    break;

            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new ServletException("Database error on POST action.", ex);
        }
    }

    private void showEventDetails(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        int eventId = Integer.parseInt(request.getParameter("id"));
        Event event = eventDAO.findById(eventId);
        if (event != null) {
            request.setAttribute("event", event);
            request.getRequestDispatcher("/event-details.jsp").forward(request, response);
        } else {
            response.sendRedirect("error.jsp");
        }
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        int eventId = Integer.parseInt(request.getParameter("id"));
        Event event = eventDAO.findById(eventId);
        request.setAttribute("event", event);
        request.getRequestDispatcher("/organizer/edit_event.jsp").forward(request, response);
    }

    private void createEvent(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, SQLException {

        User user = (User) request.getSession().getAttribute("user");
        if (user == null || !"Organizer".equals(user.getRole())) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }


        String title = request.getParameter("title");
        String description = request.getParameter("description");
        Date date = Date.valueOf(request.getParameter("date"));
        Time time = Time.valueOf(request.getParameter("time") + ":00");
        String venue = request.getParameter("venue");
        BigDecimal price = new BigDecimal(request.getParameter("price"));
        String category = request.getParameter("category");
        int organizerId = user.getId();


        Part filePart = request.getPart("coverImage");

        String rawFileName = getFileNameFromPart(filePart);
        String fileName = Paths.get(rawFileName).getFileName().toString();

        String uniqueFileName = System.currentTimeMillis() + "_" + fileName;

        String applicationPath = request.getServletContext().getRealPath("");
        String uploadFilePath = applicationPath + File.separator + UPLOAD_DIR;

        File uploadDir = new File(uploadFilePath);
        if (!uploadDir.exists()) uploadDir.mkdirs();

        filePart.write(uploadFilePath + File.separator + uniqueFileName);

        String dbImagePath = UPLOAD_DIR + File.separator + uniqueFileName;


        Event newEvent = new Event(0, title, description, date, time, venue, price, category, dbImagePath, organizerId);
        eventDAO.createEvent(newEvent);

        response.sendRedirect(request.getContextPath() + "/dashboard");
    }

    private void updateEvent(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, SQLException {
        int eventId = Integer.parseInt(request.getParameter("eventId"));
        Event event = new Event();
        event.setId(eventId);
        event.setTitle(request.getParameter("title"));
        event.setDescription(request.getParameter("description"));
        event.setDate(Date.valueOf(request.getParameter("date")));
        event.setTime(Time.valueOf(request.getParameter("time") + ":00"));
        event.setVenue(request.getParameter("venue"));
        event.setPrice(new BigDecimal(request.getParameter("price")));
        event.setCategory(request.getParameter("category"));

        eventDAO.updateEvent(event);
        response.sendRedirect(request.getContextPath() + "/dashboard");
    }

    private void cancelEvent(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int eventId = Integer.parseInt(request.getParameter("eventId"));
        eventDAO.deleteEvent(eventId);
        response.sendRedirect(request.getContextPath() + "/dashboard");
    }
    private void viewAttendees(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        int eventId = Integer.parseInt(request.getParameter("id"));


        UserDAO userDAO = new UserDAO();


        Event event = eventDAO.findById(eventId);
        List<User> attendees = userDAO.findAttendeesByEventId(eventId);

        request.setAttribute("event", event);
        request.setAttribute("attendeeList", attendees);

        request.getRequestDispatcher("/organizer/attendee_list.jsp").forward(request, response);
    }

    private void deleteEvent(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
  
        int eventId = Integer.parseInt(request.getParameter("eventId"));
        eventDAO.deleteEvent(eventId);
        response.sendRedirect(request.getContextPath() + "/dashboard");
    }

    private String getFileNameFromPart(Part part) {
        String contentDisp = part.getHeader("content-disposition");

        String[] tokens = contentDisp.split(";");
        for (String token : tokens) {
            if (token.trim().startsWith("filename")) {
                return token.substring(token.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
    }
}