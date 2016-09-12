<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>Spring and Angularjs Tutorial</title>
    <meta name="description" content="">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="static/css/app.css">
</head>
<body ng-app="myCacheApp">
    <h1 style="background-color: lightgrey">Cache Manager</h1>
    <hr>
    <h2>${msg}</h2>

    <div class="home-section">
        <ul class="menu-list">
            <li><a href="#/CacheInfo">View Cache</a></li>
            <li><a href="#/CacheRefresh">Refresh Cache</a></li>
        </ul>
    </div>

    <div ng-view></div>

    <%--<script src="./webjars/angularjs/1.4.8/angular.js"></script>--%>
    <%--<script src="./webjars/angularjs/1.4.8/angular-resource.js"></script>--%>
    <%--<script src="./webjars/angularjs/1.4.8/angular-route.js"></script>--%>
    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.8/angular.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.8/angular-route.js"></script>
    <script src="<c:url value='/static/js/app.js' />"></script>
    <script src="<c:url value='/static/js/controller/cache_manager_controller.js' />"></script>
    <script src="<c:url value='/static/js/service/cache_manager_service.js' />"></script>
    <%--<link rel="stylesheet" href="./webjars/bootstrap/3.3.6/css/bootstrap.css">--%>

</body>
</html>
