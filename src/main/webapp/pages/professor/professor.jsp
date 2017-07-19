<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix = "user" uri = "/WEB-INF/usertag.tld"%>

<fmt:setBundle basename="lang" var="rb"/>
<html>
<head>
    <title>welcome </title>
    <link href="<c:url value="/resources/css/professor/professor-style.css"/>" rel="stylesheet"/>
    <link href="<c:url value="/resources/css/locale-style.css"/>" rel="stylesheet"/>
    <script src="<c:url value="/resources/js/jquery-3.2.1.min.js"/>"></script>
</head>
<body>
    <div id="backgroundLayer" class="backgroundLayer">
    </div>

    <div id="map">
        <div id="mapCanvas"></div>
        <input id="closeMap" type="button" value="<fmt:message key="map.close" bundle="${rb}"/>"/>
    </div>

    <c:import url="/resources/components/header-component.jsp"/>
    <div class="logout">
        <p id="login">
            <fmt:message key="user.welcome" bundle="${rb}"/>
            <user:login/>
        </p>
        <c:import url="/resources/components/logout-component.jsp"/>
    </div>
    <c:import url="/resources/components/locale-component.jsp"/>

    <div class="wrapper">
        <div class="courseContainer">
            <c:forEach items="${courses}" var="course">
                <div class="course">
                    <p><fmt:message key="course.name" bundle="${rb}"/>: ${course.name}</p>
                    <p><fmt:message key="course.location" bundle="${rb}"/>: ${course.location.city} ${course.location.address}
                       <span class="showOnMap" onclick="showMap(${course.location.XCoordinate}, ${course.location.YCoordinate})">
                       <fmt:message key="professor.show.map" bundle="${rb}"/></span></p>
                    <p><fmt:message key="course.description" bundle="${rb}"/>: ${course.description}</p>
                    <p><fmt:message key="course.start" bundle="${rb}"/>: ${course.startDateToString}</p>
                    <p><fmt:message key="course.end" bundle="${rb}"/>: ${course.endDateToString}</p>
                    <p><fmt:message key="course.price" bundle="${rb}"/>: ${course.price}</p>
                    <p><fmt:message key="course.maxNumber" bundle="${rb}"/>: ${course.numberOfStudents}</p>
                    <p><fmt:message key="course.number" bundle="${rb}"/>: ${course.students.size()}</p>

                    <form id="updateForm" method="get" action="<c:url value="/app/professor/feedbacks"/>">
                        <input class="hidden" type="text" name="id" value="${course.id}">
                        <input class="submitButton" type="submit" value="<fmt:message key="professor.grade" bundle="${rb}"/>">
                    </form>
                </div>
            </c:forEach>
        </div>
    </div>

    <script>
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

    <script src="<c:url value="/resources/js/locale.js"/>" type="text/javascript"></script>

    <script async defer
        src="https://maps.googleapis.com/maps/api/js?key=AIzaSyDsKmMo3J76lTSMoV3AQKviaPKJq62vTvY">
    </script>
</body>
</html>
