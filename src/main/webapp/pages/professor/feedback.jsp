<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setBundle basename="lang" var="rb"/>
<html>
<head>
    <title>feedback</title>
    <link href="<c:url value="/resources/css/professor/feedback-style.css"/>" rel="stylesheet"/>
    <link href="<c:url value="/resources/css/locale-style.css"/>" rel="stylesheet"/>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
</head>
<body>
  <c:import url="/resources/components/header-component.jsp"/>
  <c:import url="/resources/components/locale-component.jsp"/>

  <div class="feedBackForm">
    <form method="get" action="<c:url value="/app/professor/feedback/save"/>">
      <p><fmt:message key="professor.feedback.student" bundle="${rb}"/>: ${feedback.student.fullName}</p>
      <p><fmt:message key="professor.feedback.score" bundle="${rb}"/>:</p>
      <input pattern="^-?(\d*\.)?\d*$" type="text" name="score" value="${feedback.score}" required/>
      <p><fmt:message key="professor.feedback.comment" bundle="${rb}"/>:</p>
      <textarea name="comment" value="${feedback.comment}" required>${feedback.comment}</textarea>
      <input type="text" class="hidden" name="id" value="${feedback.id}"/>
      <input type="text" class="hidden" name="courseId" value="${feedback.course.id}"/>
      <input type="submit" value="<fmt:message key="professor.feedback.change" bundle="${rb}"/>"/>
    </form>
  </div>

  <script src="<c:url value="/resources/js/locale.js"/>" type="text/javascript"></script>
</body>
</html>
