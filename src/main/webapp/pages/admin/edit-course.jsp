<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setBundle basename="lang" var="rb"/>
<html>
<head>
    <title>delete course</title>
    <link href="<c:url value="/resources/css/admin/edit-course-style.css"/>" rel="stylesheet"/>
    <link href="<c:url value="/resources/css/locale-style.css"/>" rel="stylesheet"/>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
</head>
<body>

    <div id="backgroundLayer" class="backgroundLayer">
    </div>

    <div id="map">
        <div id="mapCanvas"></div>
        <p id="current"></p>
        <input type="button" value="<fmt:message key="admin.map.cancel" bundle="${rb}"/>" onclick="closeMap()">
        <input type="button" value="<fmt:message key="admin.map.ok" bundle="${rb}"/>" onclick="saveCoordinates()">
    </div>

    <c:import url="/resources/components/header-component.jsp"/>

    <div class="languageSelectionArea">
        <div id="uaLang" class="language">
            <p>ua</p>
        </div>
        <div id="enLang" class="language">
            <p>en</p>
        </div>
    </div>

    <div class="form">
        <form method="get" action="<c:url value="/app/admin/update/course/save"/>">
            <input type="text" class="hidden" name="id" value="${course.id}"/>
            <p><fmt:message key="course.name" bundle="${rb}"/>:</p>
            <input type="text" name="name" value="${course.name}"/>
            <p><fmt:message key="course.description" bundle="${rb}"/>:</p>
            <input type="text" name="description" value="${course.description}"/>
            <p><fmt:message key="course.location" bundle="${rb}"/>:</p>
            <input type="text" name="location" value="${course.location}"/>
            <p id="location"><fmt:message key="admin.edit.coordinates" bundle="${rb}"/></p>
            <p><fmt:message key="course.start" bundle="${rb}"/>:</p>
            <input type="text" name="startDate" value="${course.startDate}"/>
            <p><fmt:message key="course.end" bundle="${rb}"/>:</p>
            <input type="text" name="endDate" value="${course.endDate}"/>
            <p><fmt:message key="admin.edit.professor" bundle="${rb}"/>:</p>
            <input type="text" name="professor" value="${course.professor.login}"/>
            <input id="x" type="text" class="hidden" name="xCoordinate" value="${course.xCoordinate}"/>
            <input id="y" type="text" class="hidden" name="yCoordinate" value="${course.yCoordinate}"/>
            <input type="submit" value="<fmt:message key="admin.edit.update" bundle="${rb}"/>"/>
        </form>
    </div>

    <script>
        $(document).ready(function() {
            $("#location").click(function() {
                showMap();
            });
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
        });

        var map = null;
        var x = parseFloat($("#x").val());
        var y = parseFloat($("#y").val());
        var tempX = x;
        var tempY = y;

        function initMap() {
            map = new google.maps.Map(document.getElementById("mapCanvas"), {
                center: {lat: x, lng: y},
                zoom: 8
            });
            var marker = new google.maps.Marker({
                map: map,
                draggable: true,
                animation: google.maps.Animation.DROP,
                position: {lat: x, lng: y},
            });
            google.maps.event.addListener(marker, 'dragend', function (evt) {
                tempX = evt.latLng.lat().toFixed(3);
                tempY = evt.latLng.lng().toFixed(3);
            });
        }

        function initCoordinates(){
            x = parseFloat($("#x").val());
            y = parseFloat($("#y").val());
            tempX = x;
            tempY = y;
        }

        function showMap(){
            if(map == null){
                initCoordinates();
                initMap();
            }
            $("#backgroundLayer").fadeIn(500);
            $("#map").fadeIn(500);
        }

        function closeMap(){
            $("#backgroundLayer").fadeOut(500);
            $("#map").fadeOut(500);
        }

        function saveCoordinates() {
            $("#x").val(tempX);
            $("#y").val(tempY);
            closeMap();
        }

    </script>

    <script async defer
            src="https://maps.googleapis.com/maps/api/js?key=AIzaSyDsKmMo3J76lTSMoV3AQKviaPKJq62vTvY">
    </script>
</body>
</html>
