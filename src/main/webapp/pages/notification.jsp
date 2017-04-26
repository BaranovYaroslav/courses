<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setBundle basename="lang" var="rb"/>
<html>
<head>
    <title>notification</title>
  <link href="<c:url value="/resources/css/notification-style.css"/>" rel="stylesheet"/>
</head>
<body>
  <div class="header">
    <img src="<c:url value="/resources/images/team_icon.png"/>"/>
  </div>

  <p id="message">${message}</p>
</body>
</html>
