<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html>
<head>
    <title>Edit Event - ${event.title}</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/dashboard.css">
</head>
<body class="dark-theme">
    <jsp:include page="../common/navbar.jsp" />
    <div class="dashboard-layout">
        <jsp:include page="../common/dashboard_sidebar.jsp" />
        <main class="dashboard-content">
            <h1>Edit Event</h1>
            <div class="form-container">
                <form action="${pageContext.request.contextPath}/event-manager" method="post">
                    <input type="hidden" name="action" value="update">
                    <input type="hidden" name="eventId" value="${event.id}">

                    <div class="form-group"><label for="title">Event Title:</label><input type="text" id="title" name="title" value="${event.title}" required></div>
                    <div class="form-group"><label for="description">Description:</label><textarea id="description" name="description" rows="5" required>${event.description}</textarea></div>

                    <fmt:formatDate value="${event.date}" pattern="yyyy-MM-dd" var="isoDate"/>
                    <div class="form-group"><label for="date">Date:</label><input type="date" id="date" name="date" value="${isoDate}" required></div>

                    <fmt:formatDate value="${event.time}" pattern="HH:mm" var="isoTime"/>
                    <div class="form-group"><label for="time">Time:</label><input type="time" id="time" name="time" value="${isoTime}" required></div>

                    <div class="form-group"><label for="venue">Venue:</label><input type="text" id="venue" name="venue" value="${event.venue}" required></div>
                    <div class="form-group"><label for="price">Price (USD):</label><input type="number" id="price" name="price" min="0" step="0.01" value="${event.price}" required></div>
                    <div class="form-group"><label for="category">Category:</label><input type="text" id="category" name="category" value="${event.category}" required></div>

                    <%-- Note: Image editing is not included for simplicity, as it adds considerable complexity --%>

                    <button type="submit" class="btn-primary form-btn">Save Changes</button>
                </form>
            </div>
        </main>
    </div>
</body>
</html>