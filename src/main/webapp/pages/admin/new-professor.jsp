<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setBundle basename="lang" var="rb"/>
<html>
<head>
    <title>new professor</title>
    <link href="<c:url value="/resources/css/admin/new-professor-style.css"/>" rel="stylesheet">
</head>
<body>
    <c:import url="/resources/components/header-component.jsp"/>

    <div class="form">
        <form method="get" action="<c:url value="/app/admin/new-professor/save"/>">
            <p><fmt:message key="admin.new.professor.login" bundle="${rb}"/>:</p>
            <input type="text" name="login" required>
            <p><fmt:message key="admin.new.professor.name" bundle="${rb}"/>:</p>
            <input type="text" name="fullName" required>
            <p><fmt:message key="admin.new.professor.email" bundle="${rb}"/>:</p>
            <input type="email" name="email" required>
            <p><fmt:message key="admin.new.professor.password" bundle="${rb}"/>:</p>
            <input type="password" name="password" required="">
            <input type="submit" value="<fmt:message key="admin.add" bundle="${rb}"/>" >
        </form>
    </div>

    <p id="message">${message}</p>
</body>
</html>