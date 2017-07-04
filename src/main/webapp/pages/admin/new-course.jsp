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
        <form method="post" action="<c:url value="/app/admin/new-course/save"/>" onsubmit="prepareForm()">
            <p><fmt:message key="course.name" bundle="${rb}"/>:</p>
            <input type="text" name="name" value="${previousName}" required>
            <p><fmt:message key="course.description" bundle="${rb}"/>:</p>
            <input type="text" name="description" value="${previousDescription}" required>
            <p><fmt:message key="course.type" bundle="${rb}"/>:</p>
            <select id="typeToSearch" name="type">
                <option>${previousType}</option>
                <c:forEach items="${types}" var="type">
                    <c:if test="${type.type != previousType}">
                        <option>${type.type}</option>
                    </c:if>
                </c:forEach>
            </select>
            <p><fmt:message key="course.location.city" bundle="${rb}"/>:</p>
            <input type="text" name="city" value="${previousCity}" required>
            <p><fmt:message key="course.location.address" bundle="${rb}"/>:</p>
            <input type="text" name="address" value="${previousAddress}" required>
            <p id="location"><fmt:message key="admin.new.course.coordinates" bundle="${rb}"/></p>
            <p><fmt:message key="course.students" bundle="${rb}"/>:</p>
            <input type="number" name="students" value="${previousNumberOfStudents}" min="1" required>
            <p><fmt:message key="course.price" bundle="${rb}"/>:</p>
            <input id="price" type="text" name="price" min="0"
                   value="${previousPrice}" required pattern="^\(?[\d.]+\)?$">
            <div id="isFreeCourse">
                <div class="isFree">
                    <input id="isFree" type="checkbox" name="isFree" onclick="swapPriceField()">
                    <label for="isFree"></label>
                </div>
                <p><fmt:message key="course.isFree" bundle="${rb}"/></p>
            </div>
            <p><fmt:message key="course.start" bundle="${rb}"/>:</p>
            <input type="text" name="startDate" value="${previousStartDate}" required
                   pattern="^(0?[1-9]|[12][0-9]|3[01])[\.\-](0?[1-9]|1[012])[\.\-]\d{4}$">
            <p><fmt:message key="course.end" bundle="${rb}"/>:</p>
            <input type="text" name="endDate" value="${previousEndDate}" required
                   pattern="^(0?[1-9]|[12][0-9]|3[01])[\.\-](0?[1-9]|1[012])[\.\-]\d{4}$">
            <p><fmt:message key="admin.new.course.professor" bundle="${rb}"/>:</p>
            <input type="text" name="professor" value="${previousProfessorLogin}" required>
            <input id="x" type="text" class="hidden" name="x" value="50.450">
            <input id="y" type="text" class="hidden" name="y" value="30.523">
            <input type="submit" value="<fmt:message key="admin.add" bundle="${rb}"/>">
        </form>
    </div>

    <p id="message">${message}</p>

    <script>
        $(document).ready(function() {
            $("#location").click(function() {
                showMap();
            });
            selectFree();
            setCoordinates();
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
            $("#y").val(tempY);;
            closeMap();
        }

        function selectFree() {
            var isFree = '${previousFree}';
            if(isFree == 'true') {
                $("#price").prop('disabled', true);
                $("#isFree").prop('checked', true);
            }
        }

        function setCoordinates() {
            var x = '${previousX}';
            var y = '${previousY}';

            if(x.length != 0) {
                $("#x").val(x);
            }
            if(y.length != 0) {
                $("#y").val(y);
            }
        }

        function swapPriceField() {
            var price = $("#price");
            price.val(0).prop('disabled', !price.prop('disabled'));
        }

        function prepareForm() {
            $("#price").prop('disabled', false);
        }
    </script>

    <script src="<c:url value="/resources/js/locale.js"/>" type="text/javascript"></script>
    <script async defer
            src="https://maps.googleapis.com/maps/api/js?key=AIzaSyDsKmMo3J76lTSMoV3AQKviaPKJq62vTvY">
    </script>
</body>
</html>
