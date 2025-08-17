<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isErrorPage="true" %> <%-- Important for getting exception details --%>
<!DOCTYPE html>
<html>
<head>
    <title>Error - EventHive</title>
     <link rel="stylesheet" href="assets/css/main.css">
</head>
<body class="dark-theme">
<jsp:include page="common/navbar.jsp" />
<main class="container">
    <div class="form-container">
        <h2>An Error Occurred</h2>
        <p>Sorry, something went wrong. Please try again later.</p>

        <%-- Optional: For debugging. You can remove this for production. --%>
        <div style="margin-top: 20px; color: #ff8a8a;">
             <strong>Error:</strong> ${requestScope['javax.servlet.error.message']} <br>
             <strong>Exception Type:</strong> ${requestScope['javax.servlet.error.exception_type']}
        </div>
    </div>
</main>
<jsp:include page="common/footer.jsp" />
</body>
</html>