<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html>
<head>
    <title>${event.title} - Event Details</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/main.css">
</head>
<body class="dark-theme">
    <jsp:include page="common/navbar.jsp" />

    <main class="container">
        <div class="event-details-container">
            <img src="${pageContext.request.contextPath}/${event.coverImagePath}" alt="${event.title}" class="event-detail-image"/>
            <div class="event-detail-info">
                <h1>${event.title}</h1>
                <span class="event-category">${event.category}</span>

                <h2>Description</h2>
                <p>${event.description}</p>

                <h2>Details</h2>
                <ul>
                    <li><strong>Date & Time:</strong> <fmt:formatDate value="${event.date}" pattern="EEEE, MMMM d, yyyy" /> at ${event.time}</li>
                    <li><strong>Venue:</strong> ${event.venue}</li>
                    <li><strong>Price:</strong>
                         <c:choose>
                            <c:when test="${event.price > 0}">
                                $<fmt:formatNumber value="${event.price}" type="number" minFractionDigits="2" maxFractionDigits="2" />
                            </c:when>
                            <c:otherwise>Free</c:otherwise>
                        </c:choose>
                    </li>
                </ul>

                <%-- Show registration button ONLY to logged-in Attendees --%>
                <c:if test="${sessionScope.user.role == 'Attendee'}">
                    <form action="${pageContext.request.contextPath}/registration-manager" method="post" style="margin-top: 20px;">
                        <input type="hidden" name="action" value="register"/>
                        <input type="hidden" name="eventId" value="${event.id}"/>
                        <button type="submit" class="btn-primary">Register for this Event</button>
                    </form>
                </c:if>

                 <c:if test="${sessionScope.user.role == 'Organizer' && sessionScope.user.id == event.organizerId}">
                    <a href="${pageContext.request.contextPath}/event-manager?action=showEditForm&id=${event.id}" class="btn-secondary">Edit My Event</a>
                 </c:if>
            </div>
        </div>
    </main>

    <jsp:include page="common/footer.jsp" />
</body>
</html>