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
  <c:import url="/resources/components/header-component.jsp"/>

  <p id="message">${message}</p>
</body>
</html>
