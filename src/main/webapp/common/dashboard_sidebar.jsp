<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/dashboard.css">

<aside class="dashboard-sidebar">
    <h3>Menu</h3>
    <ul class="sidebar-nav">
        <c:if test="${sessionScope.user.role == 'Admin'}">
            <li><a href="${pageContext.request.contextPath}/dashboard?view=overview">Statistics</a></li>
            <li><a href="${pageContext.request.contextPath}/dashboard?view=users">User Management</a></li>
            <li><a href="${pageContext.request.contextPath}/dashboard?view=events">Event Management</a></li>
        </c:if>

        <c:if test="${sessionScope.user.role == 'Organizer'}">
            <li><a href="${pageContext.request.contextPath}/dashboard">My Events</a></li>
            <li><a href="${pageContext.request.contextPath}/organizer/create_event.jsp">Create New Event</a></li>
            <li><a href="${pageContext.request.contextPath}/dashboard?view=revenue">My Revenue</a></li>
        </c:if>

        <c:if test="${sessionScope.user.role == 'Attendee'}">
            <li><a href="${pageContext.request.contextPath}/dashboard">My Upcoming Events</a></li>
            <li><a href="#">My Past Events</a></li>
        </c:if>
    </ul>
</aside>