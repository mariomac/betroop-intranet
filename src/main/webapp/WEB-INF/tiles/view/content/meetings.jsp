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
<div class="container-fluid"  ng-controller="MeetingsController as mc">
    <table class="table table-condensed">
        <tr>
            <th>Quedada</th><th>Organizador</th><th>Nombre evento</th><th>Inicio</th><th></th><th>
            <a href="#" class="btn btn-warning" ng-click="mc.newMeeting()">
                <span class="glyphicon glyphicon-plus"></span>
            </a>
        </th>
            <!-- val id:Long, var name:String?, var highlighting:Byte?, var photoUrl:String?, var tag:String?, var type:String?, var event_time: Date? -->
        </tr>
        <tr class="clickable {{m.active ? '':'text-muted'}}" ng-repeat="m in mc.meetings">
            <td ng-click="mc.showMeetingInfo(m)">{{m.name}}</td>
            <td ng-click="mc.showMeetingInfo(m)">{{m.extra.ownerName}}</td>
            <td ng-click="mc.showMeetingInfo(m)">{{m.extra.eventName}}</td>
            <td ng-click="mc.showMeetingInfo(m)">{{showDate(m.startTime)}}</td>
            <td><button class="btn btn-xs {{isLocalized(m) ? 'btn-link' : 'btn-danger'}}"
                        ng-click="mc.localizeMeeting(m)"
            ><span class="glyphicon glyphicon-warning-sign" ng-show="!isLocalized(m)"></span>Dirección</button></td>
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


