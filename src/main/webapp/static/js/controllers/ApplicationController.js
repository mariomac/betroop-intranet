(function() {
    'use strict';

    angular
        .module('app')
        .controller('ApplicationController', ApplicationController);

    function ApplicationController(Alerts) {
        var vm = this;

        vm.alerts = Alerts.alerts;
        vm.loading = true;

    }
})();