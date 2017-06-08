<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setBundle basename="lang" var="rb"/>
<html>
<head>
    <title>notification</title>
    <link href="<c:url value="/resources/css/notification-style.css"/>" rel="stylesheet"/>
    <link href="<c:url value="/resources/css/locale-style.css"/>" rel="stylesheet"/>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
</head>
<body>
  <c:import url="/resources/components/header-component.jsp"/>
  <c:import url="/resources/components/locale-component.jsp"/>

  <p id="message">${message}</p>

  <script src="<c:url value="/resources/js/locale.js"/>" type="text/javascript"></script>
</body>
</html>
