<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setBundle basename="lang" var="rb"/>
<html>
<head>
    <title>feedback</title>
    <link href="<c:url value="/resources/css/professor/feedback-style.css"/>" rel="stylesheet"/>
    <link href="<c:url value="/resources/css/locale-style.css"/>" rel="stylesheet"/>
    <script src="<c:url value="/resources/js/jquery-3.2.1.min.js"/>"></script>
</head>
<body>
  <c:import url="/resources/components/header-component.jsp"/>
  <c:import url="/resources/components/locale-component.jsp"/>

  <form class="goBack" method="get" action="<c:url value="/app/professor/feedbacks"/>">
      <input class="hidden" type="text" name="id" value="${feedback.course.id}">
      <img src="<c:url value="/resources/images/return_icon.png"/>" onclick="this.parentNode.submit()"/>
  </form>

  <div class="feedBackForm">
    <form method="post" action="<c:url value="/app/professor/feedback/save"/>">
      <p><fmt:message key="professor.feedback.student" bundle="${rb}"/>: ${feedback.student.fullName}</p>
      <p><fmt:message key="professor.feedback.score" bundle="${rb}"/>:</p>
      <input type="text" name="score" value="${feedback.score}" pattern="^\(?[\d.]+\)?$" required/>
      <p><fmt:message key="professor.feedback.comment" bundle="${rb}"/>:</p>
      <textarea name="comment" value="${feedback.comment}" required>${feedback.comment}</textarea>
      <input type="text" class="hidden" name="id" value="${feedback.id}"/>
      <input type="text" class="hidden" name="courseId" value="${feedback.course.id}"/>
      <input type="submit" value="<fmt:message key="professor.feedback.change" bundle="${rb}"/>"/>
    </form>
  </div>

  <p id="message">${message}</p>

  <script src="<c:url value="/resources/js/locale.js"/>" type="text/javascript"></script>
</body>
</html>
