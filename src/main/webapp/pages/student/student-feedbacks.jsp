<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setBundle basename="lang" var="rb"/>
<html>
<head>
    <title>feedback</title>
    <link href="<c:url value="/resources/css/student/student-feedbacks-style.css"/>" rel="stylesheet"/>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
</head>
<body>
  <div class="header">
    <img src="<c:url value="/resources/images/team_icon.png"/>"/>
  </div>

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
</body>
</html>
