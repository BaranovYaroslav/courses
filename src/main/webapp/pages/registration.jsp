<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setBundle basename="lang" var="rb"/>
<!DOCTYPE html>
<html>
    <head>
        <title>index</title>
        <link href="<c:url value="/resources/css/registration-style.css"/>" rel="stylesheet">
        <link href="<c:url value="https://fonts.googleapis.com/css?family=Roboto"/>" rel="stylesheet">
    </head>

    <body>
        <div class="header">
            <img src="<c:url value="/resources/images/team_icon.png"/>"/>
        </div>


        <form method="get" action="<c:url value="/"/>">
            <a onclick="this.parentNode.submit()"><p>Go back</p></a>
        </form>

        <div class="form">
            <form method="get" action="<c:url value="/app/registration/apply"/>">
            <p><fmt:message key="registration.login" bundle="${rb}"/>:</p>
            <input type="text" name="login">
            <p><fmt:message key="registration.email" bundle="${rb}"/>:</p>
            <input type="text" name="email">
            <p><fmt:message key="registration.name" bundle="${rb}"/>:</p>
            <input type="text" name="fullName">
            <p><fmt:message key="registration.password" bundle="${rb}"/>:</p>
            <input type="password" name="password">
            <input type="submit" width="100px" value="<fmt:message key="registration.register" bundle="${rb}"/>">
            </form>
        </div>

        <p id="message">${message}</p>
    </body>
</html>