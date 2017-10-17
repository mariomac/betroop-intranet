(function() {
    'use strict';

    angular
        .module('app')
        .factory('Alerts', Alerts);

    function Alerts($rootScope, $interval) {
        var vm = this;
        
        vm.alerts = [];

        vm.closeAlert = function(index) {
            vm.alerts.splice(index, 1);
        };

        $rootScope.$on('data-error', function(event, alert) {
            vm.addAlert(alert.msg, 'danger');
        });
        $rootScope.$on('data-success', function(event, alert) {
            vm.addAlert(alert.msg, 'success');
        });

        var alertId = 0;
        vm.addAlert = function(message,type) {
            vm.alerts.push({id: ++alertId, type: !type ? 'info' : type, msg: message});
            programAlertRemoval(alertId);
        };

        var programAlertRemoval = function(alId) {
            $interval(function() {
                for(var i = 0 ; i < vm.alerts.length ; i++) {
                    if(vm.alerts[i].id == alId) {
                        vm.closeAlert(i);
                        break;
                    }
                }
            },3000);
        };

        return vm;

    }
})();