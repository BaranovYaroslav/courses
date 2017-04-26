<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setBundle basename="lang" var="rb"/>
<html>
<head>
    <title>login</title>
    <link href="<c:url value="/resources/css/login-style.css"/>" rel="stylesheet">
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
    <form method="get" action="<c:url value="/app/login/apply"/>">
        <p><fmt:message key="login.login" bundle="${rb}"/>:</p>
        <input type="text" name="login" required>
        <p><fmt:message key="login.password" bundle="${rb}"/>:</p>
        <input type="password" name="password" required>
        <input type="submit" value="<fmt:message key="login.button.label" bundle="${rb}"/>">
    </form>
  </div>

  <p id="message">${message}</p>
</body>
</html>
