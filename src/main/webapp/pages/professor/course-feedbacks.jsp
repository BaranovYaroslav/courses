<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setBundle basename="lang" var="rb"/>
<html>
<head>
    <title>feedbacks</title>
    <link href="<c:url value="/resources/css/professor/course-feedbacks-style.css"/>" rel="stylesheet"/>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
</head>
<body>
    <c:import url="/resources/components/header-component.jsp"/>

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
</body>
</html>
