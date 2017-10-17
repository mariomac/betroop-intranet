(function () {
    'use strict';

    angular
        .module('app')
        .controller('MeetingsController', MeetingsController);


    function MeetingsController($log, $scope, $uibModal, DataService, Alerts, Loading) {
        var vm = this;

        $scope.currentPage = 1;
        $scope.pages = 1;

        $scope.showDate = function (millis) {
            return showDate(millis);
        };

        $scope.isLocalized = function (meeting) {
            return (meeting.latitude != 0 || meeting.longitude != 0)
                && meeting.extra.location
                && meeting.extra.location.city && meeting.extra.location.city.trim() != ""
                && meeting.extra.location.street && meeting.extra.location.street.trim() != "";
        };
        var loadPages = function () {
            var searchCriteria = {};
            DataService.getMeetingPages(searchCriteria).then(function (pages) {
                $scope.pages = pages;
            }, function () {
                Alerts.addAlert("Error contando el numero de páginas a mostrar", 'danger');
            });
        };

        $scope.pageChanged = function () {
            $log.log("Changing page to: " + $scope.currentPage);
            vm.getMeetings();
        };

        vm.getMeetings = function () {
            Loading.setLoading();
            var searchCriteria = {
                page: $scope.currentPage
            };
            loadPages();
            return DataService.getMeetings(searchCriteria).then(function (meetings) {
                vm.meetings = meetings;
                Loading.unsetLoading();
            }, function () {
                Alerts.addAlert("Error obteniendo lista de usuarios", 'danger');
                Loading.unsetLoading();
            });
        };

        init();
        function init() {
            loadPages();
            return vm.getMeetings();
        }


        vm.localizeMeeting = function (meeting) {
            console.log(meeting);
            var modalInstance = $uibModal.open({
                controller: 'LocalizationController',
                templateUrl: '/static/content/localization.html',
                size: 'lg',
                resolve: {
                    meeting: function () {
                        return meeting;
                    }
                }
            });
            // If the modal is correctly closed, it gets the updated
            // meeting information
            modalInstance.result.then(function (updatedMeeting) {
                DataService.updateMeetingLocation(updatedMeeting).then(
                    function () {
                        Loading.unsetLoading();
                        meeting.latitude = updatedMeeting.latitude;
                        meeting.longitude = updatedMeeting.longitude;
                        vm.getMeetings();
                    },
                    function () {
                        Loading.unsetLoading();
                        Alerts.addAlert("Error actualizando coordenadas Meeting", 'danger');
                    }
                );
            });
        };

        vm.showMeetingInfo = function (meeting) {
            var modalInstance = $uibModal.open({
                controller: 'MeetingInfoController',
                templateUrl: '/static/content/meeting.html',
                size: 'lg',
                resolve: {
                    meeting: function () {
                        return meeting;
                    }
                }
            });
            // If the modal is correctly closed, it gets the updated
            // meeting information
            modalInstance.result.then(function (updatedMeeting) {
                Loading.setLoading();
                DataService.updateMeeting(updatedMeeting).then(
                    function () {
                        Loading.unsetLoading();
                        copyMeeting(updatedMeeting, meeting);
                    },
                    function () {
                        Loading.unsetLoading();
                        Alerts.addAlert("Error actualizando usuario", 'danger');
                    }
                );
            });
        };

        vm.newMeeting = function () {
            var meeting = {}; // Crear aquí meeting con default values
            var modalInstance = $uibModal.open({
                controller: 'NewMeetingController',
                templateUrl: '/static/content/newMeeting.html',
                size: 'lg',
                resolve: {
                    meeting: function () {
                        return meeting;
                    }
                }
            });
            // If the modal is correctly closed, it gets the updated
            // meeting information
            modalInstance.result.then(function (multiMeeting) {
                Loading.setLoading();
                // Adapting meeting structure to change events and owners for an array of IDs
                var events = [];
                for (var e in multiMeeting.events) {
                    events.push(Number.parseInt(e));
                }
                multiMeeting.events = events;
                var owners = [];
                for (var o in multiMeeting.owners) {
                    owners.push(Number.parseInt(o));
                }
                multiMeeting.owners = owners;
                DataService.insertMeeting(multiMeeting).then(
                    function () {
                        Loading.unsetLoading();
                        init();
                    },
                    function () {
                        Loading.unsetLoading();
                        Alerts.addAlert("Error insertando usuario", 'danger');
                    }
                );
            });
        };

    }

    // Insertion meetings are different from typical meetings, since they store
    // multiple events and owners
    // also change the start and finish time for "StartsBefore" and "endsAfter"
    // each meeting it applies
    var createInsertionMeetingTemplate = function () {
        return {
            "events": {},
            "owners": {},
            "startBefore": 0,
            "endAfter": 120,
            "smoke": false,
            "alcohol": true,
            "active": true,
            "name": "",
            "description": "",
            "numAsistentes": 25,
            "tag": "",
            "team": "",
            "type": "BAR"
        };
    };
    var validateInsertionMeeting = function (m) {
        var numEvents = 0;
        for (var e in m.events) numEvents++;
        var numOwners = 0;
        for (var o in m.owners) numOwners++;

        if (numEvents == 0) {
            return "Debes introducir al menos un evento";
        } else if (numOwners == 0) {
            return "Debes introducir al menos un anfitrión";
        } else if (!m.name || m.name.trim() == "") {
            return "Debes introducir el nombre";
        } else if (!m.description || m.description.trim() == "") {
            return "Debes introducir la descripción";
        } else if (!m.numAsistentes || m.numAsistentes <= 0) {
            return "Debes introducir un número válido de asistentes";
        }
        return undefined;
    };

    var copyMeeting = function (src, dst) {
        dst.id = src.id;
        dst.name = src.name;
        dst.eventId = src.eventId;
        dst.numAsistentes = src.numAsistentes;
        dst.type = src.type;
        dst.smoke = src.smoke;
        dst.alcohol = src.alcohol;
        dst.team = src.team;
        dst.description = src.description;
        dst.startTime = src.startTime;
        dst.finishTime = src.finishTime;
        dst.tag = src.tag;
        dst.latitude = src.latitude;
        dst.longitude = src.longitude;
        dst.active = src.active;
        dst.extra = src.extra;
    };

    angular
        .module('app')
        .controller('LocalizationController', LocalizationController);

    function LocalizationController($uibModalInstance, $scope, meeting, Alerts, Loading, DataService) {
        $scope.meeting = JSON.parse(JSON.stringify(meeting));
        if(!$scope.meeting.extra.location) {
            $scope.meeting.extra.location = {};
        }
        $scope.ok = function () {
            $scope.meeting.extra.location.latitude = $scope.meeting.latitude;
            $scope.meeting.extra.location.longitude = $scope.meeting.longitude;
            $scope.meeting.extra.location.meetingId = $scope.meeting.id;
            $uibModalInstance.close($scope.meeting.extra.location);
        };

        $scope.cancel = function () {
            $uibModalInstance.dismiss();
        };

    }

    angular
        .module('app')
        .controller('MeetingInfoController', MeetingInfoController);

    function MeetingInfoController($uibModalInstance, $scope, meeting, Alerts, Loading, DataService) {

        // deep clone
        $scope.meeting = JSON.parse(JSON.stringify(meeting));

        $scope.showDate = function (millis) {
            return showDate(millis);
        };
        $scope.ok = function () {
            $uibModalInstance.close($scope.meeting);
        };

        $scope.cancel = function () {
            $uibModalInstance.dismiss();
        };
    }

    angular
        .module('app')
        .controller('NewMeetingController', NewMeetingController);

    function NewMeetingController($uibModalInstance, $scope, Alerts, Loading, DataService, AutoCompletion) {

        // deep clone
        $scope.meeting = createInsertionMeetingTemplate();
        $scope.searchEvent = "";
        $scope.searchedEvents = [];

        $scope.searchBar = "";
        $scope.searchedBars = [];

        $scope.errorMessage = undefined;

        $scope.showDate = function (millis) {
            return showDate(millis);
        };
        $scope.updateEvents = function () {
            $scope.searchedEvents = AutoCompletion.getEvents($scope.searchEvent);
        };
        $scope.onEventSelect = function ($item, $model, $label) {
            console.log($item);
            $scope.meeting.events[$item.id] = $item;
            //$scope.meeting.owners.push($item);
            $scope.searchEvent = "";
        };
        $scope.deleteEvent = function (event) {
            delete $scope.meeting.events[event.id];
        };

        $scope.updateBars = function () {
            $scope.searchedBars = AutoCompletion.getBars($scope.searchBar);
        };
        $scope.onBarSelect = function ($item, $model, $label) {
            console.log($item);
            $scope.meeting.owners[$item.id] = $item;
            //$scope.meeting.owners.push($item);
            $scope.searchBar = "";
        };
        $scope.deleteBar = function (bar) {
            delete $scope.meeting.owners[bar.id];
        };
        $scope.ok = function () {
            var ret = validateInsertionMeeting($scope.meeting);
            if (ret) {
                $scope.errorMessage = ret;
            } else {
                $uibModalInstance.close($scope.meeting);
            }
        };
        $scope.cancel = function () {
            $uibModalInstance.dismiss();
        };

    }

})();