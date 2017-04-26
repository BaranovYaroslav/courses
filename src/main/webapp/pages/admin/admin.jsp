<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setBundle basename="lang" var="rb"/>
<html>
<head>
    <title>admin page</title>
    <link href="<c:url value="/resources/css/admin/admin-style.css"/>" rel="stylesheet"/>
    <link href="<c:url value="/resources/css/locale-style.css"/>" rel="stylesheet"/>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
</head>
<body>
    <div id="backgroundLayer" class="backgroundLayer">
  </div>

  <div id="confirmationForm" class="confirmationForm">
    <p><fmt:message key="admin.confirmation.label" bundle="${rb}"/></p>
    <input id="confirmDeleting" type="button" class="confirmButton" value="<fmt:message key="admin.confirm" bundle="${rb}"/>"/>
    <input id="cancelDeleting" type="button" class="cancelButton" value="<fmt:message key="admin.cancel" bundle="${rb}"/>"/>
  </div>

  <div class="header">
    <img src="<c:url value="/resources/images/team_icon.png"/>"/>
  </div>

  <div class="logout">
    <form method="get" action="<c:url value="/app/logout"/>">
      <img src="<c:url value="/resources/images/logout.png"/>" onclick="this.parentNode.submit()"/>
    </form>
  </div>

  <div class="languageSelectionArea">
    <div id="uaLang" class="language">
      <p>ua</p>
    </div>
    <div id="enLang" class="language">
      <p>en</p>
    </div>
  </div>

  <div class="wrapper">
    <div class="actionBar">
      <form id="newCourseForm" method="get" action="<c:url value="/app/admin/new-course"/>">
        <div id="newCourseAction" class="action"><p><fmt:message key="admin.new.course" bundle="${rb}"/></p></div>
      </form>
      <form id="newProfessorForm" method="get" action="<c:url value="/app/admin/new-professor"/>">
        <div id="newProfessorAction" class="action"><p><fmt:message key="admin.new.professor" bundle="${rb}"/></p></div>
      </form>
      <form id="usersListForm" method="get" action="<c:url value="/app/admin/users"/>">
        <div id="usersListAction" class="action"><p><fmt:message key="admin.users" bundle="${rb}"/></p></div>
      </form>

    </div>
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

          <form id="updateForm" method="get" action="<c:url value="/app/admin/update/course"/>">
            <input class="hidden" type="text" name="id" value="${course.id}">
            <input type="submit" class="updateButton" value="<fmt:message key="edit" bundle="${rb}"/>">
          </form>

          <form id="deleteCourseForm" method="get" action="<c:url value="/app/admin/delete-course"/>">
            <input class="hidden" type="text" name="id" value="${course.id}">
            <input id="deleteCourseButton" type="button" class="deleteButton" value="<fmt:message key="delete" bundle="${rb}"/>"
                   onclick="confirmDeleting(this.form)">
          </form>

        </div>
      </c:forEach>
    </div>

    <script>
      var selectedForm = null;

      $(document).ready(function() {
        $("#newCourseAction").click(function() {
          $("#newCourseForm").submit();
        });
        $("#newProfessorAction").click(function() {
          $("#newProfessorForm").submit();
        });
        $("#usersListAction").click(function() {
          $("#usersListForm").submit();
        });
        $("#cancelDeleting").click(function() {
          $("#backgroundLayer").fadeOut(1000);
          $("#confirmationForm").fadeOut(1000);
        });
        $("#confirmDeleting").click(function() {
          $.ajax({
            url: selectedForm.action,
            type: 'GET',
            data: "id=" + selectedForm.childNodes[1].value,
            success: function(result) {
              document.getElementById("courseContainer").removeChild(selectedForm.parentNode);
              selectedForm = null;
            }
          });
          $("#backgroundLayer").fadeOut(1000);
          $("#confirmationForm").fadeOut(1000);
        });
      });
      function confirmDeleting(form){
        selectedForm = form;
        if(selectedForm != null) {
          $("#backgroundLayer").fadeIn(1000);
          $("#confirmationForm").fadeIn(1000);
        }
      }

      $("#uaLang").click(function(){
        $.get("<c:url value="/app/locale"/>", {lang: 'ua'}).done(function() {
          location.reload();
        });
      });
      $("#enLang").click(function(){
        $.get("<c:url value="/app/locale"/>", {lang: 'en'}).done(function() {
          location.reload();
        });
      });

    </script>
  </div>

</body>
</html>
