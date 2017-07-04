<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setBundle basename="lang" var="rb"/>
<!DOCTYPE html>
<html>
    <head>
        <title>index</title>
        <link href="<c:url value="/resources/css/registration-style.css"/>" rel="stylesheet">
        <link href="<c:url value="https://fonts.googleapis.com/css?family=Roboto"/>" rel="stylesheet">
        <link href="<c:url value="/resources/css/locale-style.css"/>" rel="stylesheet"/>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    </head>

    <body>
        <c:import url="/resources/components/header-component.jsp"/>
        <c:import url="/resources/components/locale-component.jsp"/>

        <form method="get" action="<c:url value="/"/>">
            <a onclick="this.parentNode.submit()"><p id="goBack"><fmt:message key="return" bundle="${rb}"/></p></a>
        </form>

        <div class="form">
            <form method="post" action="<c:url value="/app/registration/apply"/>">
            <p><fmt:message key="registration.login" bundle="${rb}"/>:</p>
            <input type="text" name="login" value="${previousLogin}" required>
            <p><fmt:message key="registration.email" bundle="${rb}"/>:</p>
            <input type="email" name="email" value="${previousEmail}" required>
            <p><fmt:message key="registration.name" bundle="${rb}"/>:</p>
            <input type="text" name="fullName" value="${previousName}" required>
            <p><fmt:message key="registration.password" bundle="${rb}"/>:</p>
            <input type="password" name="password" required>
            <input type="submit" width="100px" value="<fmt:message key="registration.register" bundle="${rb}"/>">
            </form>
        </div>

        <p id="message">${message}</p>

        <script src="<c:url value="/resources/js/locale.js"/>" type="text/javascript"></script>
    </body>
</html>