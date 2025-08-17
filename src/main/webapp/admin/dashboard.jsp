<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html>
<head>
    <title>Admin Dashboard</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/dashboard.css">
</head>
<body class="dark-theme">
    <jsp:include page="../common/navbar.jsp" />
    <div class="dashboard-layout">
        <jsp:include page="../common/dashboard_sidebar.jsp" />

        <main class="dashboard-content">
            <h1>Admin Dashboard</h1>

            <%-- Message display block for user/event actions --%>
            <c:if test="${not empty sessionScope.message}">
                <p class="form-success">${sessionScope.message}</p>
                <c:remove var="message" scope="session"/>
            </c:if>
            <c:if test="${not empty sessionScope.errorMessage}">
                <p class="form-error">${sessionScope.errorMessage}</p>
                <c:remove var="errorMessage" scope="session"/>
            </c:if>

            <c:choose>
            
                <c:when test="${param.view == 'events'}">
                    <h2>Event Management</h2>
                    <p>Below is a list of all upcoming events on the platform. As an administrator, you can cancel any event that violates platform policy.</p>

                    <table>
                        <thead>
                            <tr>
                                <th>Event Title</th>
                                <th>Organizer ID</th>
                                <th>Date</th>
                                <th>Venue</th>
                                <th class="actions-column">Action</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:choose>
                                <c:when test="${not empty allPlatformEvents}">
                                    <c:forEach var="event" items="${allPlatformEvents}">
                                        <tr>
                                            <td>${event.title}</td>
                                            <td>${event.organizerId}</td>
                                            <td><fmt:formatDate value="${event.date}" pattern="yyyy-MM-dd"/></td>
                                            <td>${event.venue}</td>
                                            <td class="actions-column">
                                                <form action="${pageContext.request.contextPath}/event-manager" method="POST" onsubmit="return confirm('ADMIN ACTION: Are you sure you want to permanently cancel this event?');">
                                                    <input type="hidden" name="action" value="adminCancel">
                                                    <input type="hidden" name="eventId" value="${event.id}">
                                                    <button type="submit" class="btn-danger">Cancel Event</button>
                                                </form>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </c:when>
                                <c:otherwise>
                                    <tr><td colspan="5">There are no upcoming events on the platform.</td></tr>
                                </c:otherwise>
                            </c:choose>
                        </tbody>
                    </table>
                </c:when>

              
                <c:otherwise>
                    <h2>Platform Statistics</h2>
                    <div class="stats-grid">
                        <div class="stat-card"><h3>Organizers</h3><p><c:choose><c:when test="${not empty organizerList}">${organizerList.size()}</c:when><c:otherwise>0</c:otherwise></c:choose></p></div>
                        <div class="stat-card"><h3>Attendees</h3><p><c:choose><c:when test="${not empty attendeeList}">${attendeeList.size()}</c:when><c:otherwise>0</c:otherwise></c:choose></p></div>
                        <div class="stat-card"><h3>Upcoming Events</h3><p><c:choose><c:when test="${not empty eventList}">${eventList.size()}</c:when><c:otherwise>0</c:otherwise></c:choose></p></div>
                        <div class="stat-card"><h3>Total Revenue</h3><p><c:choose><c:when test="${not empty totalPlatformRevenue and totalPlatformRevenue > 0}">$<fmt:formatNumber value="${totalPlatformRevenue}" type="number" minFractionDigits="2" maxFractionDigits="2" /></c:when><c:otherwise>$0.00</c:otherwise></c:choose></p></div>
                    </div>

                    <h2>User Management</h2>

                    <h3>Organizers</h3>
                    <table>
                        <thead><tr><th>ID</th><th>Name</th><th>Email</th><th class="actions-column">Action</th></tr></thead>
                        <tbody>
                            <c:forEach var="org" items="${organizerList}">
                                <tr>
                                    <td>${org.id}</td>
                                    <td>${org.name}</td>
                                    <td>${org.email}</td>
                                    <td class="actions-column">
                                        <form action="${pageContext.request.contextPath}/admin/manage-user" method="POST" onsubmit="return confirm('Are you sure you want to PERMANENTLY delete this user and all their associated events? This action cannot be undone.');">
                                            <input type="hidden" name="action" value="deleteUser">
                                            <input type="hidden" name="userId" value="${org.id}">
                                            <button type="submit" class="btn-danger">Delete</button>
                                        </form>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>

                    <h3>Attendees</h3>
                    <table>
                        <thead><tr><th>ID</th><th>Name</th><th>Email</th><th class="actions-column">Action</th></tr></thead>
                        <tbody>
                            <c:forEach var="att" items="${attendeeList}">
                                <tr>
                                    <td>${att.id}</td>
                                    <td>${att.name}</td>
                                    <td>${att.email}</td>
                                    <td class="actions-column">
                                        <form action="${pageContext.request.contextPath}/admin/manage-user" method="POST" onsubmit="return confirm('Are you sure you want to PERMANENTLY delete this user?');">
                                            <input type="hidden" name="action" value="deleteUser">
                                            <input type="hidden" name="userId" value="${att.id}">
                                            <button type="submit" class="btn-danger">Delete</button>
                                        </form>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </c:otherwise>
            </c:choose> <%-- THIS IS THE REQUIRED CLOSING TAG --%>
        </main>
    </div>
</body>
</html>