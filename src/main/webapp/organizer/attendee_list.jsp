<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html>
<head>
    <title>Attendees for: ${event.title}</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/dashboard.css">
</head>
<body class="dark-theme">
    <jsp:include page="../common/navbar.jsp" />
    <div class="dashboard-layout">
        <jsp:include page="../common/dashboard_sidebar.jsp" />

        <main class="dashboard-content">
            <h1>Attendees for: <span style="color: white;">${event.title}</span></h1>

            <table>
                <thead>
                    <tr>
                        <th>Attendee Name</th>
                        <th>Attendee Email</th>
                    </tr>
                </thead>
                <tbody>
                    <c:choose>
                        <c:when test="${not empty attendeeList}">
                            <c:forEach var="attendee" items="${attendeeList}">
                                <tr>
                                    <td>${attendee.name}</td>
                                    <td>${attendee.email}</td>
                                </tr>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <tr><td colspan="2">No attendees have registered for this event yet.</td></tr>
                        </c:otherwise>
                    </c:choose>
                </tbody>
            </table>
             <a href="${pageContext.request.contextPath}/dashboard" style="margin-top: 2rem; display: inline-block;">« Back to Dashboard</a>
        </main>
    </div>
</body>
</html>