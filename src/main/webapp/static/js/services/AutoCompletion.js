(function() {
    'use strict';

    angular
        .module('app')
        .factory('AutoCompletion', AutoCompletion);

    var MIN_CHARS_TO_TRIGGER = 1;

    function AutoCompletion(DataService) {
        function getEvents(text) {
            if(text.length < MIN_CHARS_TO_TRIGGER) {
                return [];
            } else {
                var searchCriteria = {
                    text : text,
                    fromTimestamp: Date.now(),
                    page: 1
                };
                return DataService.getEvents(searchCriteria);
            }
        }
        function getBars(text) {
            if(text.length < MIN_CHARS_TO_TRIGGER) {
                return [];
            } else {
                var searchCriteria = {
                    text : text,
                    orderBy : "name",
                    orderByAsc : true,
                    rol : 1,
                    page: 1,
                    nullEmail: true
                };
                return DataService.getUsers(searchCriteria);
            }
        }

        var service = {
            getBars : getBars,
            getEvents : getEvents
        };

        return service;
    }
})();