<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setBundle basename="lang" var="rb"/>
<html>
<head>
    <title>new course</title>
    <link href="<c:url value="/resources/css/admin/new-course-style.css"/>" rel="stylesheet"/>
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
    <c:import url="/resources/components/locale-component.jsp"/>

    <div class="form">
        <form method="get" action="<c:url value="/app/admin/new-course/save"/>">
            <p><fmt:message key="course.name" bundle="${rb}"/>:</p>
            <input type="text" name="name" required>
            <p><fmt:message key="course.description" bundle="${rb}"/>:</p>
            <input type="text" name="description" required>
            <p><fmt:message key="course.location" bundle="${rb}"/>:</p>
            <input type="text" name="location" required>
            <p id="location"><fmt:message key="admin.new.course.coordinates" bundle="${rb}"/></p>
            <p><fmt:message key="course.start" bundle="${rb}"/>:</p>
            <input type="text" name="startDate" required pattern="^(0?[1-9]|[12][0-9]|3[01])[\.\-](0?[1-9]|1[012])[\.\-]\d{4}$">
            <p><fmt:message key="course.end" bundle="${rb}"/>:</p>
            <input type="text" name="endDate" required pattern="^(0?[1-9]|[12][0-9]|3[01])[\.\-](0?[1-9]|1[012])[\.\-]\d{4}$">
            <p><fmt:message key="admin.new.course.professor" bundle="${rb}"/>:</p>
            <input type="text" name="professor" required>
            <input id="x" type="text" class="hidden" name="xCoordinate" value="50.450">
            <input id="y" type="text" class="hidden" name="yCoordinate" value="30.523">
            <input type="submit" value="<fmt:message key="admin.add" bundle="${rb}"/>">
        </form>
    </div>

    <p id="message">${message}</p>

    <script>
        $(document).ready(function() {
            $("#location").click(function() {
                showMap();
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
                position: {lat: x, lng: y}
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
            console.log('save: ' + $("#x").val() + ' ' + $("#y").val());
            closeMap();
        }
    </script>

    <script src="<c:url value="/resources/js/locale.js"/>" type="text/javascript"></script>
    <script async defer
            src="https://maps.googleapis.com/maps/api/js?key=AIzaSyDsKmMo3J76lTSMoV3AQKviaPKJq62vTvY">
    </script>
</body>
</html>
