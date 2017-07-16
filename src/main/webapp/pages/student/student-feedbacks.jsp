<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setBundle basename="lang" var="rb"/>
<html>
<head>
    <title>feedback</title>
    <link href="<c:url value="/resources/css/student/student-feedbacks-style.css"/>" rel="stylesheet"/>
    <link href="<c:url value="/resources/css/locale-style.css"/>" rel="stylesheet"/>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
</head>
<body>
  <c:import url="/resources/components/header-component.jsp"/>
  <c:import url="/resources/components/locale-component.jsp"/>

  <form class="goBack" method="get" action="<c:url value="/app/student"/>">
      <img src="<c:url value="/resources/images/return_icon.png"/>" onclick="this.parentNode.submit()"/>
  </form>

  <div class="feedbacksContainer">
      <table>
          <tr>
              <th><fmt:message key="student.feedback.course" bundle="${rb}"/></th>
              <th><fmt:message key="student.feedback.score" bundle="${rb}"/></th>
              <th><fmt:message key="student.feedback.comment" bundle="${rb}"/></th>
          </tr>
          <c:forEach items="${feedbacks}" var="feedback">
              <tr>
                  <td>${feedback.course.name}</td>
                  <td>${feedback.score}</td>
                  <td>${feedback.comment}</td>
              </tr>
          </c:forEach>
      </table>
  </div>

  <script src="<c:url value="/resources/js/locale.js"/>" type="text/javascript"></script>
</body>
</html>
