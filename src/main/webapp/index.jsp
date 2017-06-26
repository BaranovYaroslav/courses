<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setBundle basename="lang" var="rb"/>
<!DOCTYPE html>
<html>
    <head>
        <title>index</title>
        <link href="<c:url value="/resources/css/index-style.css"/>" rel="stylesheet">
        <link href="<c:url value="https://fonts.googleapis.com/css?family=Roboto"/>" rel="stylesheet">
        <link href="<c:url value="/resources/css/locale-style.css"/>" rel="stylesheet"/>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    </head>

    <body>
        <div id="backgroundLayer" class="backgroundLayer">
        </div>

        <c:import url="/resources/components/header-component.jsp"/>
        <c:import url="/resources/components/locale-component.jsp"/>

        <div class="container">
            <p ><fmt:message key="index.courses" bundle="${rb}"/>:</p>
            <div id="courses" class="number"> </div>
            <p><fmt:message key="index.students" bundle="${rb}"/>:</p>
            <div id="students" class="number"> </div>
            <p><fmt:message key="index.professors" bundle="${rb}"/>:</p>
            <div id="professors" class="number"> </div>

            ${message}

            <div class="controlButtons">
                <form method="get" action="<c:url value="/app/login"/>">
                    <input type="submit" id="login" value="<fmt:message key="index.login" bundle="${rb}"/>"/>
                </form>
                <form method="get" action="<c:url value="/app/registration"/>">
                    <input type="submit" id="registration" value="<fmt:message key="index.registration" bundle="${rb}"/>"/>
                </form>
            </div>
        </div>

        <div class="footer">
        </div>

        <script>
            $(document).ready(
            function getInfo(){
                $.ajax({
                    url: "<c:url value="/app/index"/>",
                    type: 'GET',
                    success: function(result) {
                        setInfo(JSON.parse(result));
                        console.log(JSON.parse(result));
                    }
                });
            });

            function setInfo(infoObject) {
                $("#courses").html(infoObject.courseNumber);
                $("#students").html(infoObject.studentNumber);
                $("#professors").html(infoObject.professorNumber);
            }
        </script>
        <script src="<c:url value="/resources/js/locale.js"/>" type="text/javascript"></script>
    </body>
</html>