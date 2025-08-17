<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html>
<head>
    <title>My Revenue</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/dashboard.css">
</head>
<body class="dark-theme">
    <jsp:include page="../common/navbar.jsp" />
    <div class="dashboard-layout">
        <jsp:include page="../common/dashboard_sidebar.jsp" />

        <main class="dashboard-content">
            <h1>My Revenue Details</h1>

            <div class="stats-grid" style="grid-template-columns: 1fr;">
                 <div class="stat-card">
                    <h3>Total Gross Revenue</h3>
                    <p>
                        $<fmt:formatNumber value="${totalOrganizerRevenue}" type="number" minFractionDigits="2" maxFractionDigits="2" />
                    </p>
                </div>
            </div>

            <h2>Revenue Breakdown by Event</h2>
             <table>
                <thead>
                    <tr>
                        <th>Event Title</th>
                        <th>Price per Ticket</th>
                        <th># of Registrations</th>
                        <th>Sub-Total Revenue</th>
                    </tr>
                </thead>
                <tbody>
                    <c:choose>
                        <c:when test="${not empty organizerEvents}">
                            <c:forEach var="event" items="${organizerEvents}">
                                <tr>
                                    <td>${event.title}</td>
                                    <td>$<fmt:formatNumber value="${event.price}" type="number" minFractionDigits="2" /></td>
                                    <td>
                                        <%-- We can calculate the # of registrations from the data we have --%>
                                        <c:set var="revenue" value="${eventRevenueMap[event.id]}"/>
                                        <c:choose>
                                            <c:when test="${event.price > 0}">
                                                <fmt:formatNumber value="${revenue / event.price}" maxFractionDigits="0"/>
                                            </c:when>
                                            <c:otherwise>N/A (Free Event)</c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td>
                                        <strong>$<fmt:formatNumber value="${revenue}" type="number" minFractionDigits="2" maxFractionDigits="2"/></strong>
                                    </td>
                                </tr>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                             <tr><td colspan="4">No event data to display revenue.</td></tr>
                        </c:otherwise>
                    </c:choose>
                </tbody>
             </table>
        </main>
    </div>
</body>
</html>