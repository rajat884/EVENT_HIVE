<%-- Add isELIgnored="false" and the JSTL directives at the top --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html>
<head>
    <%-- The title is now dynamic based on whether we are creating or cloning --%>
    <title>
        <c:choose>
            <c:when test="${not empty eventToClone}">Clone Event</c:when>
            <c:otherwise>Create New Event</c:otherwise>
        </c:choose>
    </title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/dashboard.css">
</head>
<body class="dark-theme">
    <jsp:include page="../common/navbar.jsp" />
    <div class="dashboard-layout">
        <jsp:include page="../common/dashboard_sidebar.jsp" />
        <main class="dashboard-content">
            <%-- Dynamic H1 Title --%>
            <h1>
                <c:choose>
                    <c:when test="${not empty eventToClone}">Clone Event</c:when>
                    <c:otherwise>Create a New Event</c:otherwise>
                </c:choose>
            </h1>

            <div class="form-container">
                <%-- IMPORTANT: enctype is required for file uploads --%>
                <form action="${pageContext.request.contextPath}/event-manager" method="post" enctype="multipart/form-data">
                    <input type="hidden" name="action" value="create">

                    <%-- Each 'value' attribute will pre-fill with data if 'eventToClone' is not empty --%>
                    <div class="form-group"><label for="title">Event Title:</label><input type="text" id="title" name="title" value="${eventToClone.title}" required></div>
                    <div class="form-group"><label for="description">Description:</label><textarea id="description" name="description" rows="5" required>${eventToClone.description}</textarea></div>

                    <%-- We must format date and time objects to the specific string format that the input fields require (YYYY-MM-DD and HH:mm) --%>
                    <fmt:formatDate value="${eventToClone.date}" pattern="yyyy-MM-dd" var="isoDate"/>
                    <div class="form-group"><label for="date">Date:</label><input type="date" id="date" name="date" value="${isoDate}" required></div>

                    <fmt:formatDate value="${eventToClone.time}" pattern="HH:mm" var="isoTime"/>
                    <div class="form-group"><label for="time">Time:</label><input type="time" id="time" name="time" value="${isoTime}" required></div>

                    <div class="form-group"><label for="venue">Venue:</label><input type="text" id="venue" name="venue" value="${eventToClone.venue}" required></div>
                    <div class.form-group"><label for="price">Price (USD):</label><input type="number" id="price" name="price" min="0" step="0.01" value="${eventToClone.price}" required></div>
                    <div class="form-group"><label for="category">Category:</label><input type="text" id="category" name="category" value="${eventToClone.category}" required></div>

                    <%-- The user must always upload a cover image, even when cloning. We add a small helper text for clarity --%>
                    <div class="form-group">
                        <label for="coverImage">Cover Image:</label>
                        <c:if test="${not empty eventToClone}"><small style="display:block; margin-bottom: 5px;">Please select a new cover image.</small></c:if>
                        <input type="file" id="coverImage" name="coverImage" accept="image/png, image/jpeg" required>
                    </div>

                    <button type="submit" class="btn-primary form-btn">
                        <c:choose>
                            <c:when test="${not empty eventToClone}">Create Cloned Event</c:when>
                            <c:otherwise>Create Event</c:otherwise>
                        </c:choose>
                    </button>
                </form>
            </div>
        </main>
    </div>
</body>
</html>