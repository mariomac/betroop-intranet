<%--
This code is distributed under a BEER-WARE license.

Mario Macias Lloret wrote this file. Considering this, you can do what you
want with it: modify it, redistribute it, sell it, etc... But you will always
have to credit me as an author in the code.

In addition, if we meet someday and you think this code has been useful for
you, you MUST pay me a beer (a good one, if possible) as a reward for my
contribution.
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>

<!DOCTYPE html>
<html ng-app="app">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <!-- Latest compiled and minified CSS -->
    <link rel="stylesheet" href="/static/lib/css/bootstrap.min.css">
    <!-- Optional theme -->
    <link rel="stylesheet" href="/static/lib/css/bootstrap-theme.min.css">
    <link rel="stylesheet" href="/static/css/common.css">
    <!-- Latest compiled and minified JavaScript -->
    <script src="/static/lib/js/jquery-2.2.2.min.js"></script>
    <script src="/static/lib/js/bootstrap.min.js"></script>
    <title>
        <tiles:getAsString name="title"/>
    </title>
</head>
<body ng-controller="ApplicationController as app">
<header>
    <tiles:insertAttribute name="header"/>
</header>
<div id="alerts" class="container col-md-6" style="position:fixed; right: 0px; z-index: 255">
    <uib-alert ng-repeat="alert in app.alerts" type="{{alert.type}}" close="app.closeAlert($index)">{{alert.msg}}
    </uib-alert>
</div><!--/col-md-12--->
<tiles:insertAttribute name="body"/>
<footer>
    <tiles:insertAttribute name="footer"/>
</footer>
<script src="/static/lib/js/angular.min.js"></script>
<script src="/static/lib/js/ui-bootstrap-tpls-1.2.5.min.js"></script>
<script src="/static/js/utils.js"></script>
<script src="/static/lib/js/angular-confirm.min.js"></script>

<script src="/static/js/app.js"></script>
<script src="/static/js/services/Alerts.js"></script>
<script src="/static/js/services/Loading.js"></script>
<script src="/static/js/services/DataService.js"></script>
<script src="/static/js/services/AutoCompletion.js"></script>
<script src="/static/js/controllers/ApplicationController.js"></script>
<script src="/static/js/controllers/EventsController.js"></script>
<script src="/static/js/controllers/UsersController.js"></script>
<script src="/static/js/controllers/TabBarController.js"></script>
<script src="/static/js/controllers/MeetingsController.js"></script>

</body>
</html>