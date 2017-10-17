(function() {
    'use strict';

    angular
        .module('app')
        .controller('TabBarController', TabBarController);

    function TabBarController($location) {
        var tb = this;

        var tabs = ["events","users","meetings"];
        tb.isActive = function (tabname) {
            var currentTab = null;
            tabs.forEach(function(tab) {
                if($location.absUrl().indexOf(tab) !== -1) {
                   currentTab = tab;
                }
            });
            return currentTab === null ? tabname === tabs[0] : tabname === currentTab;
        }
    }
})();