(function() {
    'use strict';

    angular
        .module('app')
        .controller('EventsController', EventsController);

    function EventsController($log, $scope,$confirm, DataService, Alerts, Loading) {
        var vm = this;

        $scope.currentPage = 1;
        $scope.pages = 1;
		$scope.searchdate=new Date();
		$scope.searchtext="";

		var loadPages = function() {
			var searchCriteria = {
				text : $scope.searchtext,
				fromTimestamp : $scope.searchdate == null ? undefined : $scope.searchdate.getTime()
			};
			DataService.getEventPages(searchCriteria).then(function(pages) {
				$scope.pages = pages;
			}, function() {
				Alerts.addAlert("Error contando el numero de páginas a mostrar", 'danger');
			});
		};

        $scope.pageChanged = function() {
            $log.log("Changing page to: " + $scope.currentPage);
            vm.getEvents();
        };

		var newIdGenerator = -1;
		vm.addEvent = function() {
			vm.events.unshift({id:newIdGenerator--,modified:true});
		};

        vm.getEvents = function() {
            Loading.setLoading();
			var searchCriteria = {
				page : $scope.currentPage,
				text : $scope.searchtext,
				fromTimestamp : $scope.searchdate == null ? undefined : $scope.searchdate.getTime()
			};
			loadPages();
            return DataService.getEvents(searchCriteria).then(function(events) {
                for(var i = 0 ; i < events.length ; i++) {
                    events[i] = eventJsonToForm(events[i]);
                }
                vm.events = events;
                Loading.unsetLoading();
            }, function() {
                Alerts.addAlert("Error obteniendo lista de eventos", 'danger');
            });
        };

        function eventJsonToForm(event) {
            var d = new Date(event.eventTime);
            event.eventTime = d.getDate() + "/" + (d.getMonth() + 1) + '/' + d.getFullYear() + " " + d.getHours() + ":" + (d.getMinutes() < 10 ? "0" + d.getMinutes() : d.getMinutes());
            // console.log(meeting);
            event.event_type = event.event_type + "";
            return event;
        }

        function eventFormToJson(event) {
            var s = event.eventTime.trim();
            if(s.match(/^\d{1,2}\/\d{1,2}\/\d{4}\s+\d{1,2}:\d{2}$/) == null ) {
                Alerts.addAlert("Formato de fecha/hora " + s + " incorrecto. Debe ser: DD/MM/AAAA HH:MM",'danger');
                return null;
            }
            s=s.split(/[^\d]+/);
            $log.log("Parsed date: " + s);
            event.eventTime=new Date(s[2],s[1]-1,s[0],s[3],s[4]).getTime();
            event.event_type=parseInt(event.event_type);
            event.modified=undefined;

            return event;
        }

        init();
        function init() {
			loadPages();
            return vm.getEvents();
        }

		vm.insertEvent = function(event) {
			event = eventFormToJson(event);
			if(event != null) {
				DataService.insertEvent(event).then(function(id) {
					event = eventJsonToForm(event);
					event.id = id;
					event.modified = undefined;
				},function() {
					event = eventJsonToForm(event);
					Alerts.addAlert("Error intentando guardar nuevo evento: " + event.name);
				});
			}
		};

        vm.updateEvent = function(event) {
			if(event.id < 0) {
				event.id = undefined;
				vm.insertEvent(event);
			} else {
				event = eventFormToJson(event);
				if(event!=null) {
					$log.log(event);
					DataService.updateEvent(event).then(function() {
					   $log.log("Event " + event.id + " updated correctly");
						vm.events[_index(event.id)]=eventJsonToForm(event);
					},function() {
						Alerts.addAlert("Error intentando guardar evento con id " + event.id);
						vm.events[_index(event.id)]=eventJsonToForm(event);
						vm.events[_index(event.id)].modified=true;
					});
				}
			}
        };
        
        vm.restoreEvent = function(eventId) {
			if(eventId < 0) { // if non-persistent meeting, just remove it
				vm.events.splice(_index(eventId),1);
			} else {
				DataService.getEvent(eventId).then(function(event) {
					vm.events[_index(event.id)]=eventJsonToForm(event);
				},function() {
					Alerts.addAlert("Error restaurando evento con id " + event.id);
					vm.events[_index(event.id)]=eventJsonToForm(event);
					vm.events[_index(event.id)].modified=true;
				});
			}
        };

        vm.updateEvents = function() {
            $confirm({text:"Los cambios que hagas no se podrán deshacer",title:"¿Estás seguro?"})
                .then(function() {
                    Loading.setLoading();
                    DataService.updateEvents(vm.events).then(function() {
                        Loading.unsetLoading();
                        Alerts.addAlert("Los eventos se han guardado correctamente", 'success');
                    }, function() {
                        Alerts.addAlert("Error guardando los eventos", 'danger');
                    });
                });
        };

        function _index(eventId) {
            for (var i = 0; i < vm.events.length; i++) {
                if(vm.events[i].id == eventId) {
                    return i;
                }
            }
			return -1;
        }
    }
})();