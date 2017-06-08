<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setBundle basename="lang" var="rb"/>
<html>
<head>
    <title>users</title>
    <link href="<c:url value="/resources/css/admin/users-list-style.css"/>" rel="stylesheet"/>
</head>
<body>
    <c:import url="/resources/components/header-component.jsp"/>

    <div class="usersList">
        <table>
            <tr>
                <th><fmt:message key="admin.users.id" bundle="${rb}"/></th>
                <th><fmt:message key="admin.users.login" bundle="${rb}"/></th>
                <th><fmt:message key="admin.users.name" bundle="${rb}"/></th>
                <th><fmt:message key="admin.users.password" bundle="${rb}"/></th>
                <th><fmt:message key="admin.users.role" bundle="${rb}"/></th>
            </tr>
            <c:forEach items="${users}" var="student">
                <tr>
                    <td>${student.id}</td>
                    <td>${student.login}</td>
                    <td>${student.fullName}</td>
                    <td>${student.password}</td>
                    <td>${student.role.role}</td>
                </tr>
            </c:forEach>
        </table>
    </div>
</body>
</html>
