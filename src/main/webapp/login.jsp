<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Login - EventHive</title>
    <link rel="stylesheet" href="assets/css/main.css">
</head>
<body class="dark-theme">
    <jsp:include page="common/navbar.jsp" />

    <main class="container">
        <div class="form-container">
            <h2>Login to Your Account</h2>

            <c:if test="${not empty errorMessage}">
                <p class="form-error">${errorMessage}</p>
            </c:if>
             <c:if test="${param.success == 'true'}">
                <p class="form-success">Registration successful! Please log in.</p>
            </c:if>

            <form action="login" method="post">
                <div class="form-group">
                    <label for="email">Email:</label>
                    <input type="email" id="email" name="email" required>
                </div>
                <div class="form-group">
                    <label for="password">Password:</label>
                    <input type="password" id="password" name="password" required>
                </div>
                <button type="submit" class="btn-primary form-btn">Login</button>
            </form>
            <p>Don't have an account? <a href="register.jsp">Register here</a></p>
        </div>
    </main>

    <jsp:include page="common/footer.jsp" />
</body>
</html>