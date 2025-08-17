<%-- Ensures JSP Expression Language is enabled and JSTL tags are available --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html>
<head>
    <title>Organizer Dashboard</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/dashboard.css">
</head>
<body class="dark-theme">
    <jsp:include page="../common/navbar.jsp" />
    <div class="dashboard-layout">
        <jsp:include page="../common/dashboard_sidebar.jsp" />

        <main class="dashboard-content">
            <h1>Organizer Dashboard</h1>

            <%-- This block displays a success message after an action, like creating an event. --%>
            <c:if test="${not empty sessionScope.message}">
                <p class="form-success">${sessionScope.message}</p>
                <%-- Important: Remove the attribute so it doesn't show again on the next page load --%>
                <c:remove var="message" scope="session"/>
            </c:if>

            <h2>My Upcoming Events</h2>
            <table>
                <thead>
                    <tr>
                        <th>Title</th>
                        <th>Date</th>
                        <th>Venue</th>
                        <%-- Add the "actions-column" class to the header for proper CSS styling --%>
                        <th class="actions-column">Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <c:choose>
                        <c:when test="${not empty upcomingEvents}">
                            <c:forEach var="event" items="${upcomingEvents}">
                                <tr>
                                    <td>${event.title}</td>
                                    <td><fmt:formatDate value="${event.date}" pattern="yyyy-MM-dd"/> at ${event.time}</td>
                                    <td>${event.venue}</td>

                                    <%-- Add the "actions-column" class to the data cell to apply the layout fixes --%>
                                    <td class="actions-column">
                                        <a href="${pageContext.request.contextPath}/event-manager?action=viewAttendees&eventId=${event.id}" class="btn-secondary">View Attendees</a>
                                        <a href="${pageContext.request.contextPath}/event-manager?action=showEditForm&id=${event.id}" class="btn-secondary">Edit</a>
                                        <a href="${pageContext.request.contextPath}/event-manager?action=clone&eventId=${event.id}" class="btn-secondary">Clone</a>

                                        <form action="${pageContext.request.contextPath}/event-manager" method="POST" style="display:inline;" onsubmit="return confirm('Are you sure you want to cancel this event? This cannot be undone.');">
                                            <input type="hidden" name="action" value="cancel">
                                            <input type="hidden" name="eventId" value="${event.id}">
                                            <button type="submit" class="btn-danger">Cancel</button>
                                        </form>
                                    </td>
                                </tr>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                             <%-- Make the column span 5 to account for the Actions column now taking more space --%>
                            <tr><td colspan="4">You have no upcoming events. Why not <a href="${pageContext.request.contextPath}/organizer/create_event.jsp">create one</a>?</td></tr>
                        </c:otherwise>
                    </c:choose>
                </tbody>
            </table>
        </main>
    </div>
</body>
</html>