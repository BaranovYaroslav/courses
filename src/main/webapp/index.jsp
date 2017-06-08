<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
    <head>
        <title>index</title>
        <link href="<c:url value="/resources/css/index-style.css"/>" rel="stylesheet">
        <link href="<c:url value="https://fonts.googleapis.com/css?family=Roboto"/>" rel="stylesheet">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    </head>

    <body>
        <div id="backgroundLayer" class="backgroundLayer">
        </div>

        <c:import url="/resources/components/header-component.jsp"/>

        <div class="container">
            <p>Number of courses:</p>
            <div class="number"></div>
            <p>Number of students:</p>
            <div class="number"></div>
            <p>Number of professors:</p>
            <div class="number"></div>

            ${message}

            <div class="controlButtons">
                <form method="get" action="<c:url value="/app/login"/>">
                    <input type="submit" id="login" value="Login"/>
                </form>
                <form method="get" action="<c:url value="/app/registration"/>">
                    <input type="submit" id="registration" value="Register"/>
                </form>
            </div>
        </div>

        <div class="footer">
        </div>
    </body>
</html>