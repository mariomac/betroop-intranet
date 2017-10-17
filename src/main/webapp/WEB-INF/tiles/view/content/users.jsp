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
<div class="container-fluid"  ng-controller="UsersController as uc">
    <%--<form theme="simple" action="eventList">--%>
    <div class="row">
        <div class="col-md-7">
            <div class="form-group">
                <label for="searchtext" class="control-label">Texto</label>
                <input id="searchtext" ng-model="searchtext" class="form-control" placeholder="Busca texto en nombre, o descripción"/>
            </div>
        </div>
        <div class="col-md-2">
            <div class="form-group">
                <label for="role" class="control-label">Rol</label>
                <select id="role" ng-model="selectedRole" class="form-control"
                    ng-options="role.label for role in roles"></select>
            </div>
        </div>
        <div class="col-md-2">
            <div class="form-group">
                <label for="orderBy" class="control-label">Ordenar según</label>
                <select id="orderBy" ng-model="selectedOrderBy" class="form-control"
                        ng-options="ob.label for ob in orderBy"></select>
            </div>
        </div>
        <div class="col-md-1">
            <div class="form-group">
                <label for="searchbutton" class="control-label">Buscar</label>
                <a href="#" class="btn btn-info" class = "form-control" id="searchbutton" ng-click="uc.getUsers()">
                    <span class="glyphicon glyphicon-search"></span>
                </a>
            </div>
        </div>
    </div>
    <table class="table table-condensed">
        <tr>
            <th>Nombre</th><th>Email</th><th>Fecha inscripción</th><th>Tipo</th><th>
            <a href="#" class="btn btn-warning" ng-click="uc.newUser()">
                <span class="glyphicon glyphicon-plus"></span>
            </a>
        </th>
            <!-- val id:Long, var name:String?, var highlighting:Byte?, var photoUrl:String?, var tag:String?, var type:String?, var event_time: Date? -->
        </tr>
        <tr class="clickable" ng-repeat="u in uc.users" ng-init="usrs=[]" ng-click="uc.showUserInfo(u)">
            <td>{{u.name}}</td>
            <td>{{u.email}}</td>
            <td>{{showDate(u.createTime)}}</td>
            <td>{{u.role}}</td>
        </tr>
    </table>
    <div class="row" style="text-align: center">
        <uib-pagination items-per-page="1" total-items="pages" ng-model="currentPage" max-size="5" class="pagination-sm" boundary-link-numbers="true" rotate="false"
                        ng-change="pageChanged()"></uib-pagination>
    </div>
    <%--<div class="row" style="text-align: center">--%>
    <%--<div class="col-md-4">--%>
    <%--<button type="submit" class="btn btn-warning" ng-click="ec.updateEvents()">Guardar cambios</button>--%>
    <%--</div>--%>
    <%--<div class="col-md-4">--%>
    <%--<form><button type="submit" class="btn btn-info">Cancelar</button></form>--%>
    <%--</div>--%>
    <%--</div>--%>
</div>


