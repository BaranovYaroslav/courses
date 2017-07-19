<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setBundle basename="lang" var="rb"/>
<html>
<head>
    <title>users</title>
    <link href="<c:url value="/resources/css/admin/users-list-style.css"/>" rel="stylesheet"/>
    <link href="<c:url value="/resources/css/locale-style.css"/>" rel="stylesheet"/>
    <script src="<c:url value="/resources/js/jquery-3.2.1.min.js"/>"></script>
</head>
<body>
    <c:import url="/resources/components/header-component.jsp"/>
    <c:import url="/resources/components/locale-component.jsp"/>

    <form class="goBack" method="get" action="<c:url value="/app/admin"/>">
        <img src="<c:url value="/resources/images/return_icon.png"/>" onclick="this.parentNode.submit()"/>
    </form>

    <div class="usersList">
        <table>
            <tr>
                <th><fmt:message key="admin.users.id" bundle="${rb}"/></th>
                <th><fmt:message key="admin.users.login" bundle="${rb}"/></th>
                <th><fmt:message key="admin.users.name" bundle="${rb}"/></th>
                <th><fmt:message key="admin.users.role" bundle="${rb}"/></th>
            </tr>
            <c:forEach items="${users}" var="user">
                <tr>
                    <td>${user.id}</td>
                    <td>${user.login}</td>
                    <td>${user.fullName}</td>
                    <td>${user.role.role}</td>
                </tr>
            </c:forEach>
        </table>
    </div>

    <script src="<c:url value="/resources/js/locale.js"/>" type="text/javascript"></script>
</body>
</html>
