<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setBundle basename="lang" var="rb"/>
<html>
<head>
  <title>details</title>
  <link href="<c:url value="/resources/css/student/course-details.css"/>" rel="stylesheet"/>
  <link href="<c:url value="/resources/css/locale-style.css"/>" rel="stylesheet"/>
  <script src="<c:url value="/resources/js/jquery-3.2.1.min.js"/>"></script>
</head>
<body>
<c:import url="/resources/components/header-component.jsp"/>
<c:import url="/resources/components/locale-component.jsp"/>

<form class="goBack" method="get" action="<c:url value="/app/student"/>">
  <img src="<c:url value="/resources/images/return_icon.png"/>" onclick="this.parentNode.submit()"/>
</form>

<div id="backgroundLayer" class="backgroundLayer">
</div>

<div id="map">
  <div id="mapCanvas"></div>
  <input id="closeMap" type="button" value="Close">
</div>

<div class="wrapper">
  <div id="courseContainer" class="courseContainer">
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
        <form id="registerCourseForm" method="post" action="<c:url value="/app/student/register"/>">
          <input class="hidden" type="text" name="courseId" value="${course.id}">
          <input id="register" type="submit" class="registerButton" value="<fmt:message key="student.register" bundle="${rb}"/>">
        </form>
      </div>
  </div>
</div>

<script src="<c:url value="/resources/js/locale.js"/>" type="text/javascript"></script>

<script>
  function submitForm(form) {
    form.submit();
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

  $(document).ready(function() {
    $("#closeMap").click(function() {
      $("#backgroundLayer").fadeOut(500);
      $("#map").fadeOut(500);
    });
  });
</script>

<script async defer
        src="https://maps.googleapis.com/maps/api/js?key=AIzaSyDsKmMo3J76lTSMoV3AQKviaPKJq62vTvY">
</script>
</body>
</html>

