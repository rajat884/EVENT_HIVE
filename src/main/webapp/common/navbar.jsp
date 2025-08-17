<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/main.css">

<nav class="navbar">
    <div class="navbar-container">
        <a href="${pageContext.request.contextPath}/" class="navbar-brand">EventHive</a>

        <div class="navbar-links">
            <c:choose>
                <%-- User is logged in --%>
                <c:when test="${not empty sessionScope.user}">
                    <span class="navbar-userinfo">
                        Welcome, ${sessionScope.user.name}
                        (<em>${sessionScope.user.role}</em>)
                    </span>
                    <a href="${pageContext.request.contextPath}/dashboard" class="nav-link">Dashboard</a>
                    <a href="${pageContext.request.contextPath}/logout" class="nav-link">Logout</a>
                </c:when>

                <%-- User is logged out --%>
                <c:otherwise>
                    <a href="${pageContext.request.contextPath}/login" class="nav-link">Login</a>
                    <a href="${pageContext.request.contextPath}/register" class="nav-link register-btn">Register</a>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</nav>