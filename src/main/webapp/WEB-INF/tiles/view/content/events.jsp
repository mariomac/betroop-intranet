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

<div class="container-fluid"  ng-controller="EventsController as ec">
<%--<form theme="simple" action="eventList">--%>
	<div class="row">
		<div class="col-md-8">
			<div class="form-group">
				<label for="searchtext" class="control-label">Texto</label>
				<input id="searchtext" ng-model="searchtext" class="form-control" placeholder="Busca texto en nombre, tags, ..."/>
			</div>
		</div>
		<div class="col-md-3">
			<div class="form-group">
				<label for="searchdate" class="control-label">Desde fecha:</label>
				<input id="searchdate" ng-model="searchdate" class="form-control" type="date"/>
			</div>
		</div>
		<div class="col-md-1">
			<div class="form-group">
				<label for="searchbutton" class="control-label">Buscar</label>
				<a href="#" class="btn btn-info" class = "form-control" id="searchbutton" ng-click="ec.getEvents()">
					<span class="glyphicon glyphicon-search"></span>
				</a>
			</div>
		</div>
	</div>
<table class="table table-condensed">
    <tr>
        <th>Nombre</th><th>Destac</th><th>Foto</th><th></th><th>Tags</th><th>Tipo</th><th>Deporte</th><th>Fecha</th><th>
			<a href="#" class="btn btn-warning" ng-click="ec.addEvent()">
				<span class="glyphicon glyphicon-plus"></span>
			</a>
		</th>
    <!-- val id:Long, var name:String?, var highlighting:Byte?, var photoUrl:String?, var tag:String?, var type:String?, var event_time: Date? -->
    </tr>
    <tr ng-repeat="e in ec.events" ng-init="evns=[]">
        <td><input type="hidden" ng-value="e.id" size="8" disabled="true"/>
        <input class="form-control" ng-model="e.name" size="35" ng-change="e.modified=true"/></td>
        <td><input class="form-control" type="checkbox" ng-model="e.highlighting" ng-change="e.modified=true"/></td>
        <td><input size="12" onfocus="this.select()" class="form-control" ng-model="e.photoUrl" ng-change="e.modified=true"/></td>
        <td><div class="eventPhotoThumb"><img src="{{e.photoUrl}}"/></div></td>
        <td><input class="form-control" ng-model="e.tag" ng-change="e.modified=true"/></td>
        <td><input class="form-control" ng-model="e.type" size="12" ng-change="e.modified=true"/></td>
        <td><select class="form-control" ng-model="e.event_type" ng-change="e.modified=true">
            <option value="1">1: Fútbol</option>
            <option value="2">2: Baloncesto</option>
            <option value="3">3: Pádel</option>
            <option value="4">4: Tenis</option>
            <option value="5">5: Motos</option>
            <option value="6">6: F1</option>
            <option value="7">7: otros</option>
        </select></td>
        <td>
            <input type="text" class="form-control" size="10" onfocus="this.select()" placeholder="DD/MM/AAAA HH:MM"
                   ng-model="e.eventTime" ng-change="e.modified=true"/>
        </td>
        <td>
            <a ng-if="e.modified" href="#" class="btn btn-warning" ng-click="ec.updateEvent(e);">
                <span class="glyphicon glyphicon-floppy-disk"></span>
            </a>
            <a ng-if="e.modified" href="#" class="btn btn-info" ng-click="ec.restoreEvent(e.id)"> <!-- to finalize -->
                <span class="glyphicon glyphicon-repeat"></span>
            </a>
        </td>
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
