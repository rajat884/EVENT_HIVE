<%-- THIS LINE IS THE FIX: isELIgnored="false" tells the page to process the data --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>

<%-- These lines ensure the c:forEach and fmt:formatDate tags work --%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<!DOCTYPE html>
<html>
<head>
    <title>Attendee Dashboard</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/dashboard.css">
</head>
<body class="dark-theme">
    <jsp:include page="../common/navbar.jsp" />
    <div class="dashboard-layout">
        <jsp:include page="../common/dashboard_sidebar.jsp" />

        <main class="dashboard-content">
            <h1>Attendee Dashboard</h1>

            <%-- Show any message from the session (e.g., registered successfully) --%>
            <c:if test="${not empty sessionScope.message}">
                <p class="form-success">${sessionScope.message}</p>
                <c:remove var="message" scope="session"/>
            </c:if>

            <h2>My Upcoming Events</h2>
            <table>
                <thead><tr><th>Event</th><th>Date</th><th>Venue</th><th>Action</th></tr></thead>
                <tbody>
                    <c:choose>
                        <c:when test="${not empty myUpcomingEvents}">
                            <c:forEach var="event" items="${myUpcomingEvents}">
                                <tr>
                                    <td>${event.title}</td>
                                    <td><fmt:formatDate value="${event.date}" pattern="E, MMM dd"/></td>
                                    <td>${event.venue}</td>
                                    <td>
                                        <form action="${pageContext.request.contextPath}/registration-manager" method="POST" onsubmit="return confirm('Are you sure you want to cancel this ticket?');">
                                            <input type="hidden" name="action" value="cancel">
                                            <input type="hidden" name="eventId" value="${event.id}">
                                            <button type="submit" class="btn-danger">Cancel Ticket</button>
                                        </form>
                                    </td>
                                </tr>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                           <tr><td colspan="4">You aren't registered for any upcoming events. <a href="${pageContext.request.contextPath}/">Find an event!</a></td></tr>
                        </c:otherwise>
                    </c:choose>
                </tbody>
            </table>

             <h2>My Past Events</h2>
             <table>
                <thead><tr><th>Event</th><th>Date</th><th>Venue</th></tr></thead>
                <tbody>
                      <c:choose>
                        <c:when test="${not empty myPastEvents}">
                            <c:forEach var="event" items="${myPastEvents}">
                                <tr>
                                    <td>${event.title}</td>
                                    <td><fmt:formatDate value="${event.date}" pattern="E, MMM dd, yyyy"/></td>
                                    <td>${event.venue}</td>
                                </tr>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                           <tr><td colspan="3">You have no past events.</td></tr>
                        </c:otherwise>
                    </c:choose>
                </tbody>
             </table>

        </main>
    </div>
</body>
</html>