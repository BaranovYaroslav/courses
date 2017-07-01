<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setBundle basename="lang" var="rb"/>
<html>
<head>
    <title>student</title>
    <link href="<c:url value="/resources/css/student/student-style.css"/>" rel="stylesheet"/>
    <link href="<c:url value="/resources/css/locale-style.css"/>" rel="stylesheet"/>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
    <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
    <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
</head>
<body>
    <c:import url="/resources/components/header-component.jsp"/>
    <c:import url="/resources/components/locale-component.jsp"/>

    <div class="logout">
        <form method="get" action="<c:url value="/app/logout"/>">
            <img src="<c:url value="/resources/images/logout.png"/>" onclick="this.parentNode.submit()"/>
        </form>
    </div>

    <div id="backgroundLayer" class="backgroundLayer">
    </div>

    <div id="map">
        <div id="mapCanvas"></div>
        <input id="closeMap" type="button" value="Close">
    </div>

    <div class="wrapper">
        <div class="userOptionsBar">
            <div class="actionBar">
                <form method="get" action="<c:url value="/app/student/courses"/>">
                    <div class="action" onclick="submitForm(this.parentNode)">
                        <p><fmt:message key="student.courses" bundle="${rb}"/></p>
                    </div>
                </form>
                <form method="get" action="<c:url value="/app/student/feedbacks"/>">
                    <div class="action" onclick="submitForm(this.parentNode)">
                        <p><fmt:message key="student.feedbacks" bundle="${rb}"/></p>
                    </div>
                </form>
            </div>
            <div id="searchForm" class="searchForm">
                <form method="get" action="<c:url value="/app/search"/>" onsubmit="">
                    <p>Choose type:</p>
                    <select id="typeToSearch" name="type">
                        <option></option>
                        <c:forEach items="${types}" var="type">
                            <option>${type.type}</option>
                        </c:forEach>
                    </select>
                    <p>Choose location:</p>
                    <select id="locationToSearch" name="location">
                        <option></option>
                        <c:forEach items="${locations}" var="location">
                            <option>${location}</option>
                        </c:forEach>
                    </select>
                    <p>Select price:</p>
                    <div id=slider class="slider">
                        <div id="min" class="rangeValue">
                        </div>
                        <div id="slider-range"></div>
                        <div id="max" class="rangeValue">
                        </div>
                    </div>
                    <div id="coursesForFree">
                        <div class="onlyFree">
                            <input type="checkbox" name="onlyFree" id="onlyFreeCheckbox" onclick="disablePriceRange()">
                            <label for="onlyFreeCheckbox"></label>
                        </div>
                        <p>Only free courses</p>
                    </div>
                    <input id="minPrice" type="text" name="minPrice" value="0">
                    <input id="maxPrice" type="text" name="maxPrice" value="${maxPrice}">
                    <input type="submit" value="Search">
                </form>
            </div>
        </div>

        <div id="courseContainer" class="courseContainer">
            <c:forEach items="${coursesForRegistration}" var="course">
                <div class="course">
                    <p><fmt:message key="course.name" bundle="${rb}"/>: ${course.name}</p>
                    <p><fmt:message key="course.location" bundle="${rb}"/>: ${course.location.city} ${course.location.address}
                       <span class="showOnMap" onclick="showMap(${course.location.XCoordinate}, ${course.location.YCoordinate})">
                       <fmt:message key="professor.show.map" bundle="${rb}"/></span></p>
                    <p><fmt:message key="course.description" bundle="${rb}"/>: ${course.description}</p>
                    <p><fmt:message key="course.price" bundle="${rb}"/>: ${course.price}</p>
                    <p><fmt:message key="course.start" bundle="${rb}"/>: ${course.startDate}</p>
                    <p><fmt:message key="course.end" bundle="${rb}"/>: ${course.endDate}</p>
                    <p><fmt:message key="course.professor" bundle="${rb}"/>: ${course.professor.fullName}</p>
                    <p><fmt:message key="course.maxNumber" bundle="${rb}"/>: ${course.numberOfStudents}</p>
                    <p><fmt:message key="course.number" bundle="${rb}"/>: ${course.students.size()}</p>

                    <form id="deleteCourseForm" method="get" action="<c:url value="/app/student/register"/>">
                        <input class="hidden" type="text" name="courseId" value="${course.id}">
                        <input id="register" type="submit" class="registerButton" value="<fmt:message key="student.register" bundle="${rb}"/>">
                    </form>
                </div>
            </c:forEach>
        </div>
    </div>

    <script src="<c:url value="/resources/js/locale.js"/>" type="text/javascript"></script>

    <script>
        function submitForm(form) {
            form.submit();
        }

        var min = 0;
        var max = '${maxPrice}';

        $(function() {
            $("#min").html(min);
            $("#max").html(max);
            $( "#slider-range" ).slider({
                range: true,
                min: min,
                max: max,
                values: [ min, max ],
                slide: function( event, ui ) {
                    $("#amount").val( "$" + ui.values[0] + " - $" + ui.values[1] );
                    $("#min").html(ui.values[0]);
                    $("#max").html(ui.values[1]);
                    $("#minPrice").val(ui.values[0]);
                    $("#maxPrice").val(ui.values[1]);
                }
            });
        } );

        function disablePriceRange() {
            var isDisabled = $( "#slider-range" ).slider("option", "disabled");
            $("#slider-range").slider(isDisabled ? "enable" : "disable");
            if(!isDisabled) {
                $("#minPrice").val(0);
                $("#maxPrice").val(0);
            } else {
                $("#minPrice").val($("#min").html());
                $("#maxPrice").val($("#max").html());
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

