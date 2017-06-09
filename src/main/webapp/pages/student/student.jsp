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
                        <option> </option>
                        <option>Math</option>
                        <option>IT</option>
                        <option>Natural</option>
                        <option>Humanistic</option>
                    </select>
                    <p>Choose location:</p>
                    <select id="locationToSearch" name="location">
                        <option> </option>
                        <option>Kiev</option>
                        <option>1</option>
                        <option>2</option>
                        <option>3</option>
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
                    <input type="number" name="minPrice" value=2>
                    <input type="number" name="maxPrice" value=200>
                    <input type="submit" value="Search">
                </form>
            </div>
        </div>

        <div id="courseContainer" class="courseContainer">
            <c:forEach items="${coursesForRegistration}" var="course">
                <div class="course">
                    <p><fmt:message key="course.name" bundle="${rb}"/>: ${course.name}</p>
                    <p><fmt:message key="course.location" bundle="${rb}"/>: ${course.location}</p>
                    <p><fmt:message key="course.description" bundle="${rb}"/>: ${course.description}</p>
                    <p><fmt:message key="course.start" bundle="${rb}"/>: ${course.startDate}</p>
                    <p><fmt:message key="course.professor" bundle="${rb}"/>: ${course.professor.fullName}</p>
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
        var max = 400;

        $( function() {
            $("#min").html(min);
            $("#max").html(max);
            $( "#slider-range" ).slider({
                range: true,
                min: min,
                max: max,
                values: [ min, max ],
                slide: function( event, ui ) {
                    $( "#amount" ).val( "$" + ui.values[ 0 ] + " - $" + ui.values[ 1 ] );
                    $("#min").html(ui.values[0]);
                    $("#max").html(ui.values[1]);
                }
            });
        } );

        function getMin() {
            return min;
        }

        function getMax() {
            return max;
        }

        function disablePriceRange() {
            var isDisabled = $( "#slider-range" ).slider("option", "disabled");
            $("#slider-range").slider(isDisabled ? "enable" : "disable");
        }
    </script>
</body>
</html>

