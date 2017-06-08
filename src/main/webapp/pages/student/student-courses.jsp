<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setBundle basename="lang" var="rb"/>
<html>
<head>
    <title>courses</title>
    <link href="<c:url value="/resources/css/student/student-courses-style.css"/>" rel="stylesheet"/>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
</head>
<body>
  <div id="backgroundLayer" class="backgroundLayer">
  </div>

  <div id="confirmationForm" class="confirmationForm">
    <p><fmt:message key="student.courses.notification" bundle="${rb}"/></p>
    <input id="confirmUnregister" type="button" class="confirmButton" value="<fmt:message key="student.courses.notification.apply" bundle="${rb}"/>"/>
    <input id="cancelUnregister" type="button" class="cancelButton" value="<fmt:message key="student.courses.notification.cancel" bundle="${rb}"/>"/>
  </div>

  <c:import url="/resources/components/header-component.jsp"/>

  <div id="courseContainer" class="courseContainer">
    <c:forEach items="${courses}" var="course">
      <div class="course">
        <p><fmt:message key="course.name" bundle="${rb}"/>: ${course.name}</p>
        <p><fmt:message key="course.location" bundle="${rb}"/>: ${course.location}</p>
        <p><fmt:message key="course.description" bundle="${rb}"/>: ${course.description}</p>
        <p><fmt:message key="course.start" bundle="${rb}"/>: ${course.startDate}</p>
        <p><fmt:message key="course.end" bundle="${rb}"/>: ${course.endDate}</p>
        <p><fmt:message key="course.professor" bundle="${rb}"/>: ${course.professor.fullName}</p>
        <p><fmt:message key="course.number" bundle="${rb}"/>: ${course.students.size()}</p>

        <form id="unregisterForm" method="get" action="<c:url value="/app/student/courses/unregister"/>">
          <input class="hidden" type="text" name="courseId" value="${course.id}">
          <input id="register" type="button" class="unregisterButton" value="<fmt:message key="student.courses.unregister" bundle="${rb}"/>"
                 onclick="confirmUnregister(this.form)">
        </form>

      </div>
    </c:forEach>
  </div>

  <script>
    var selectedForm = null;

    $(document).ready(function() {
      $("#cancelUnregister").click(function() {
        $("#backgroundLayer").fadeOut(1000);
        $("#confirmationForm").fadeOut(1000);
      });
      $("#confirmUnregister").click(function() {
        $.ajax({
          url: selectedForm.action,
          type: 'GET',
          data: "courseId=" + selectedForm.childNodes[1].value,
          success: function(result) {
            document.getElementById("courseContainer").removeChild(selectedForm.parentNode);
            selectedForm = null;
          }
        });
        $("#backgroundLayer").fadeOut(1000);
        $("#confirmationForm").fadeOut(1000);
      });
    });

    function confirmUnregister(form){
      selectedForm = form;
      if(selectedForm != null) {
        $("#backgroundLayer").fadeIn(1000);
        $("#confirmationForm").fadeIn(1000);
      }
    }

  </script>
</body>
</html>
