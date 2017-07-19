<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setBundle basename="lang" var="rb"/>
<html>
<head>
    <title>feedbacks</title>
    <link href="<c:url value="/resources/css/professor/course-feedbacks-style.css"/>" rel="stylesheet"/>
    <link href="<c:url value="/resources/css/locale-style.css"/>" rel="stylesheet"/>
    <script src="<c:url value="/resources/js/jquery-3.2.1.min.js"/>"></script>
</head>
<body>
    <c:import url="/resources/components/header-component.jsp"/>
    <c:import url="/resources/components/locale-component.jsp"/>

    <form class="goBack" method="get" action="<c:url value="/app/professor"/>">
        <img src="<c:url value="/resources/images/return_icon.png"/>" onclick="this.parentNode.submit()"/>
    </form>

    <div class="studentList">
        <c:forEach items="${feedbacks}" var="feedback">
            <div class="student">
                <div class="studentName">
                    <p>${feedback.student.fullName}</p>
                </div>
                <div class="feedBackLink">
                    <form method="get" action="<c:url value="/app/professor/feedback"/>">
                        <input type="text" class="hidden" name="id" value="${feedback.id}">
                        <p onclick="submitForm(this.parentNode)"><fmt:message key="professor.feedbacks.grade" bundle="${rb}"/></p>
                    </form>
                </div>
            </div>
        </c:forEach>
    </div>

    <script>
        function submitForm(form) {
            form.submit();
        }
    </script>

    <script src="<c:url value="/resources/js/locale.js"/>" type="text/javascript"></script>
</body>
</html>
