<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Attendees for ${event.title}</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/dashboard.css">
</head>
<body class="dark-theme">
    <jsp:include page="../common/navbar.jsp" />
    <div class="dashboard-layout">
        <jsp:include page="../common/dashboard_sidebar.jsp" />

        <main class="dashboard-content">
            <h1>Attendees for: <em>${event.title}</em></h1>
            <p>Total Registered: ${attendeeList.size()}</p>

            <table>
                <thead>
                    <tr>
                        <th>Attendee ID</th>
                        <th>Name</th>
                        <th>Email</th>
                    </tr>
                </thead>
                <tbody>
                    <c:choose>
                        <c:when test="${not empty attendeeList}">
                            <c:forEach var="attendee" items="${attendeeList}">
                                <tr>
                                    <td>${attendee.id}</td>
                                    <td>${attendee.name}</td>
                                    <td>${attendee.email}</td>
                                </tr>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <tr>
                                <td colspan="3">No attendees have registered for this event yet.</td>
                            </tr>
                        </c:otherwise>
                    </c:choose>
                </tbody>
            </table>
            <br/>
            <a href="${pageContext.request.contextPath}/dashboard" class="btn-secondary">← Back to Dashboard</a>
        </main>
    </div>
</body>
</html>