<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setBundle basename="lang" var="rb"/>
<html>
<head>
    <title>student</title>
    <link href="<c:url value="/resources/css/student/student-style.css"/>" rel="stylesheet"/>
</head>
<body>
    <c:import url="/resources/components/header-component.jsp"/>

    <div class="logout">
        <form method="get" action="<c:url value="/app/logout"/>">
            <img src="<c:url value="/resources/images/logout.png"/>" onclick="this.parentNode.submit()"/>
        </form>
    </div>

    <div class="wrapper">
        <div class="actionBar">
            <form method="get" action="<c:url value="/app/student/courses"/>">
                <div class="action" onclick="submitForm(this.parentNode)">
                    <p><fmt:message key="student.courses" bundle="${rb}"/></p>
                </div>
            </form>
            <form method="get" action="<c:url value="/app/student/feedbacks"/>">
                <div class="action" onclick="submitForm(this.parentNode)">
                    <p><fmt:message key="student.feedbacks" bundle="${rb}"/></p>
                </div>
            </form>
        </div>

        <div id="courseContainer" class="courseContainer">
            <c:forEach items="${coursesForRegistration}" var="course">
                <div class="course">
                    <p><fmt:message key="course.name" bundle="${rb}"/>: ${course.name}</p>
                    <p><fmt:message key="course.location" bundle="${rb}"/>: ${course.location}</p>
                    <p><fmt:message key="course.description" bundle="${rb}"/>: ${course.description}</p>
                    <p><fmt:message key="course.start" bundle="${rb}"/>: ${course.startDate}</p>
                    <p><fmt:message key="course.professor" bundle="${rb}"/>: ${course.professor.fullName}</p>
                    <p><fmt:message key="course.number" bundle="${rb}"/>: ${course.students.size()}</p>

                    <form id="deleteCourseForm" method="get" action="<c:url value="/app/student/register"/>">
                        <input class="hidden" type="text" name="courseId" value="${course.id}">
                        <input id="register" type="submit" class="registerButton" value="<fmt:message key="student.register" bundle="${rb}"/>">
                    </form>
                </div>
            </c:forEach>
        </div>
    </div>

    <script>
        function submitForm(form) {
            form.submit();
        }
    </script>
</body>
</html>

