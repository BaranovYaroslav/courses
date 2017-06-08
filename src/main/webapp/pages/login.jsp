<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setBundle basename="lang" var="rb"/>
<html>
<head>
    <title>login</title>
    <link href="<c:url value="/resources/css/login-style.css"/>" rel="stylesheet">
    <link href="<c:url value="https://fonts.googleapis.com/css?family=Roboto"/>" rel="stylesheet">
    <link href="<c:url value="/resources/css/locale-style.css"/>" rel="stylesheet"/>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
</head>
<body>
    <c:import url="/resources/components/header-component.jsp"/>
    <c:import url="/resources/components/locale-component.jsp"/>

    <form method="get" action="<c:url value="/"/>">
        <a onclick="this.parentNode.submit()"><p>Go back</p></a>
    </form>


  <div class="form">
    <form method="get" action="<c:url value="/app/login/apply"/>">
        <p><fmt:message key="login.login" bundle="${rb}"/>:</p>
        <input type="text" name="login" required>
        <p><fmt:message key="login.password" bundle="${rb}"/>:</p>
        <input type="password" name="password" required>
        <input type="submit" value="<fmt:message key="login.button.label" bundle="${rb}"/>">
    </form>
  </div>

  <p id="message">${message}</p>

    <script src="<c:url value="/resources/js/locale.js"/>" type="text/javascript"></script>
</body>
</html>
