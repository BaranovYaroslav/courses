<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setBundle basename="lang" var="rb"/>
<html>
<head>
    <title>courses</title>
    <link href="<c:url value="/resources/css/student/student-courses-style.css"/>" rel="stylesheet"/>
    <link href="<c:url value="/resources/css/locale-style.css"/>" rel="stylesheet"/>
    <script src="<c:url value="/resources/js/jquery-3.2.1.min.js"/>"></script>
</head>
<body>
  <div id="backgroundLayer" class="backgroundLayer">
  </div>

  <div id="map">
    <div id="mapCanvas"></div>
    <input id="closeMap" type="button" value="Close">
  </div>

  <div id="confirmationForm" class="confirmationForm">
    <p><fmt:message key="student.courses.notification" bundle="${rb}"/></p>
    <input id="confirmUnregister" type="button" class="confirmButton" value="<fmt:message key="student.courses.notification.apply" bundle="${rb}"/>"/>
    <input id="cancelUnregister" type="button" class="cancelButton" value="<fmt:message key="student.courses.notification.cancel" bundle="${rb}"/>"/>
  </div>

  <c:import url="/resources/components/header-component.jsp"/>
  <c:import url="/resources/components/locale-component.jsp"/>

  <form class="goBack" method="get" action="<c:url value="/app/student"/>">
    <img src="<c:url value="/resources/images/return_icon.png"/>" onclick="this.parentNode.submit()"/>
  </form>

  <div id="courseContainer" class="courseContainer">
    <c:forEach items="${courses}" var="course">
      <div class="course">
        <p><fmt:message key="course.name" bundle="${rb}"/>: ${course.name}</p>
        <p><fmt:message key="course.location" bundle="${rb}"/>: ${course.location.city} ${course.location.address}
                       <span class="showOnMap" onclick="showMap(${course.location.XCoordinate}, ${course.location.YCoordinate})">
                       <fmt:message key="professor.show.map" bundle="${rb}"/></span></p>
        <p><fmt:message key="course.description" bundle="${rb}"/>: ${course.description}</p>
        <p><fmt:message key="course.price" bundle="${rb}"/>: ${course.price}</p>
        <p><fmt:message key="course.start" bundle="${rb}"/>: ${course.startDateToString}</p>
        <p><fmt:message key="course.end" bundle="${rb}"/>: ${course.endDateToString}</p>
        <p><fmt:message key="course.professor" bundle="${rb}"/>: ${course.professor.fullName}</p>
        <p><fmt:message key="course.maxNumber" bundle="${rb}"/>: ${course.numberOfStudents}</p>
        <p><fmt:message key="course.number" bundle="${rb}"/>: ${course.students.size()}</p>

        <form id="unregisterForm" method="post" action="<c:url value="/app/student/courses/unregister"/>">
          <input class="hidden" type="text" name="courseId" value="${course.id}">
          <input id="unregister" type="button" class="unregisterButton" value="<fmt:message key="student.courses.unregister" bundle="${rb}"/>"
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
          type: 'POST',
          data: "courseId=" + selectedForm.childNodes[1].value,
          success: function() {
            document.getElementById("courseContainer").removeChild(selectedForm.parentNode);
            selectedForm = null;
          }
        });
        $("#backgroundLayer").fadeOut(1000);
        $("#confirmationForm").fadeOut(1000);
      });
      $("#closeMap").click(function() {
        $("#backgroundLayer").fadeOut(500);
        $("#map").fadeOut(500);
      });
    });

    function confirmUnregister(form){
      selectedForm = form;
      if(selectedForm != null) {
        $("#backgroundLayer").fadeIn(1000);
        $("#confirmationForm").fadeIn(1000);
      }
    }

    var map = null;

    function initMap(x, y) {
      map = new google.maps.Map(document.getElementById("mapCanvas"), {
        center: {lat: x, lng: y},
        zoom: 8
      }, console.log(x, y));
      var marker = new google.maps.Marker({
        map: map,
        animation: google.maps.Animation.DROP,
        position: {lat: x, lng: y}
      });
    }

    function showMap(xCoordinate, yCoordinate) {
      $("#backgroundLayer").fadeIn(500);
      $("#map").fadeIn(500);
      initMap(xCoordinate, yCoordinate);
    }
  </script>

  <script src="<c:url value="/resources/js/locale.js"/>" type="text/javascript"></script>
  <script async defer
          src="https://maps.googleapis.com/maps/api/js?key=AIzaSyDsKmMo3J76lTSMoV3AQKviaPKJq62vTvY">
  </script>
</body>
</html>
