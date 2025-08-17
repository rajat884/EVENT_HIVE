<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html>
<head>
    <title>Welcome to EventHive</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/main.css?v=3">
</head>
<body class="dark-theme">
    <jsp:include page="common/navbar.jsp" />


    <header class="page-header hero-section">
        <div class="hero-container">


            <div class="hero-left">
                <h1>Find Your Next Experience</h1>
                <p>Discover, create, and manage amazing events with the most intuitive platform built for communities.</p>

                <div class="hero-stats">
                    <div class="stat-item">
                        <strong>${totalEvents}+</strong>
                        <span>Events Hosted</span>
                    </div>
                    <div class="stat-item">
                        <strong>${totalUsers}+</strong>
                        <span>Happy Members</span>
                    </div>
                </div>

                <div class="cta-buttons-container">
                    <a href="#event-grid" class="btn-cta btn-primary-cta">Browse Events</a>
                    <a href="${pageContext.request.contextPath}/organizer/create_event.jsp" class="btn-cta btn-secondary-cta">Create an Event</a>
                </div>
            </div>


<div class="hero-right">
    <div class="carousel-container">

        <%-- Store the list of events in a variable for reuse --%>
        <c:set var="featuredList" value="${featuredEvents}" />

        <div class="carousel-slides">

            <c:forEach var="event" items="${featuredList}">
                <div class="slide">
                    <a href="${pageContext.request.contextPath}/event-manager?action=details&id=${event.id}">
                        <img src="${pageContext.request.contextPath}/${event.coverImagePath}" alt="${event.title}">
                        <div class="slide-caption">${event.title}</div>
                    </a>
                </div>
            </c:forEach>

            <%-- Second, identical set of slides for the looping effect --%>
            <c:forEach var="event" items="${featuredList}">
                <div class="slide">
                    <a href="${pageContext.request.contextPath}/event-manager?action=details&id=${event.id}">
                        <img src="${pageContext.request.contextPath}/${event.coverImagePath}" alt="${event.title}">
                        <div class="slide-caption">${event.title}</div>
                    </a>
                </div>
            </c:forEach>
        </div>
    </div>
</div>

        </div>
    </header>


    <main class="container" id="event-grid">
        <h2 class="section-title">All Upcoming Events</h2>
        <div class="event-grid">
            <c:choose>
                <c:when test="${not empty events}">
                    <c:forEach var="event" items="${events}">
                        <div class="event-card">
                            <img src="${pageContext.request.contextPath}/${event.coverImagePath}" alt="${event.title} Cover Image" class="event-card-image">
                            <div class="event-card-content">
                                <h3 class="event-card-title">${event.title}</h3>
                                <p class="event-card-date">
                                    <fmt:formatDate value="${event.date}" pattern="E, MMM dd, yyyy" /> at ${event.time}
                                </p>
                                <p class="event-card-venue">${event.venue}</p>
                                <p class="event-card-price">
                                    <c:choose>
                                        <c:when test="${event.price > 0}">
                                            $<fmt:formatNumber value="${event.price}" type="number" minFractionDigits="2" maxFractionDigits="2" />
                                        </c:when>
                                        <c:otherwise>Free</c:otherwise>
                                    </c:choose>
                                </p>
                                <a href="${pageContext.request.contextPath}/event-manager?action=details&id=${event.id}" class="btn-primary">View Details</a>
                            </div>
                        </div>
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <div style="text-align: center; grid-column: 1 / -1; color: #aaa;">
                         <h2>No upcoming events at the moment.</h2>
                         <p>Please check back later!</p>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
    </main>

    <jsp:include page="common/footer.jsp" />
    <script src="${pageContext.request.contextPath}/assets/js/main.js"></script>
</body>
</html>