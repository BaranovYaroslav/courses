<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setBundle basename="lang" var="rb"/>
<html>
<head>
    <title>new professor</title>
    <link href="<c:url value="/resources/css/admin/new-professor-style.css"/>" rel="stylesheet">
    <link href="<c:url value="/resources/css/locale-style.css"/>" rel="stylesheet"/>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
</head>
<body>
    <c:import url="/resources/components/header-component.jsp"/>
    <c:import url="/resources/components/locale-component.jsp"/>

    <div class="form">
        <form method="get" action="<c:url value="/app/admin/new-professor/save"/>">
            <p><fmt:message key="admin.new.professor.login" bundle="${rb}"/>:</p>
            <input type="text" name="login" value="${previousLogin}" required>
            <p><fmt:message key="admin.new.professor.name" bundle="${rb}"/>:</p>
            <input type="text" name="fullName" value="${previousName}" required>
            <p><fmt:message key="admin.new.professor.email" bundle="${rb}"/>:</p>
            <input type="email" name="email" value="${previousEmail}" required>
            <p><fmt:message key="admin.new.professor.password" bundle="${rb}"/>:</p>
            <input type="password" name="password" required="">
            <input type="submit" value="<fmt:message key="admin.add" bundle="${rb}"/>" >
        </form>
    </div>

    <p id="message">${message}</p>

    <script src="<c:url value="/resources/js/locale.js"/>" type="text/javascript"></script>
</body>
</html>