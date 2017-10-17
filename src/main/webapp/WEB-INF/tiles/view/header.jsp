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
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<nav class="navbar navbar-inverse" >
    <div class="container-fluid">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="<c:url value="/gui/"/>">Intranet Betroop</a>
        </div>

        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1" >
            <ul class="nav navbar-nav" style="text-align: center"  ng-controller="TabBarController as tb">
                <li ng-class="tb.isActive('events')?'active':''"><a href="<c:url value="/gui/events"/>">Eventos <!--span id="nav-events" class="sr-only">(current)</span--></a></li>
                <li ng-class="tb.isActive('users')?'active':''"><a href="<c:url value="/gui/users"/>">Usuarios <!--span id="nav-events" class="sr-only">(current)</span--></a></li>
                <li ng-class="tb.isActive('meetings')?'active':''"><a href="<c:url value="/gui/meetings"/>">Quedadas <!--span id="nav-events" class="sr-only">(current)</span--></a></li>
                <%--<li><a href="#">Link</a></li>--%>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <li><a href="/logout">Salir</a></li>
            </ul>
        </div><!-- /.navbar-collapse -->
    </div><!-- /.container-fluid -->
</nav>