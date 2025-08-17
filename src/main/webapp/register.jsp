<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Register - EventHive</title>
    <link rel="stylesheet" href="assets/css/main.css">
</head>
<body class="dark-theme">
    <jsp:include page="common/navbar.jsp" />

    <main class="container">
        <div class="form-container">
            <h2>Create an Account</h2>

            <c:if test="${not empty errorMessage}">
                <p class="form-error">${errorMessage}</p>
            </c:if>

            <form action="register" method="post">
                <div class="form-group">
                    <label for="name">Full Name:</label>
                    <input type="text" id="name" name="name" required>
                </div>
                <div class="form-group">
                    <label for="email">Email:</label>
                    <input type="email" id="email" name="email" required>
                </div>
                 <div class="form-group">
                    <label for="password">Password:</label>
                    <input type="password" id="password" name="password" required>
                </div>
                <div class="form-group">
                    <label for="role">Register as:</label>
                    <select id="role" name="role">
                        <option value="Attendee">Attendee</option>
                        <option value="Organizer">Organizer</option>
                    </select>
                </div>
                <button type="submit" class="btn-primary form-btn">Register</button>
            </form>
             <p>Already have an account? <a href="login.jsp">Login here</a></p>
        </div>
    </main>

    <jsp:include page="common/footer.jsp" />
</body>
</html>