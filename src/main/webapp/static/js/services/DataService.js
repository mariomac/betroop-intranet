(function() {
    'use strict';

    angular
        .module('app')
        .factory('DataService', DataService);

    function DataService($http, $log, $q, $rootScope) {
        /** Object to manage all backend URL's */
        var urls = {
                events : "/api/v1/events",
                users : "/api/v1/users",
                files : "/api/v1/files",
                meetings : "/api/v1/meetings"
            };

        /** A generic helper method to execute a HTTP request to the backend */
        var _request = function(url, method, model, isFile){
            var def = $q.defer();
            var req = {
                    method: method,
                    url: url
                };
            if(isFile) {
                req.headers = { 'Content-type' : "multipart/form-data"};
                req.transformRequest = angular.identity;
            }

            if(!method) {
                req.method = 'GET';
            }

            if(model) {
                req.data = model;
            }

            $http(req).success(function(data) {
                def.resolve(data);
            }).error(function(data, code) {
                def.reject(data);
                if(data.actionError) {
                    $rootScope.$emit('data-error', {  msg: data.actionError });
                }
                $log.error(data, code);
            });
            return def.promise;
        }

        function getEvents(searchCriteria) {
            return _request(urls.events+"/query", 'POST', searchCriteria);
        }

        function getEvent(id) {
            return _request(urls.events+"/"+id);
        }

        function updateEvents(events) {
            return _request(urls.events,'PUT', events);
        }

        function updateEvent(event) {
            return _request(urls.events+'/'+event.id,'PUT',event);
        }

		function insertEvent(event) {
			return _request(urls.events, 'POST', event);
		}

        function getEventPages(searchCriteria) {
            return _request(urls.events+'/pages','POST', searchCriteria);
        }

        function getUsers(searchCriteria) {
            return _request(urls.users+"/query", 'POST', searchCriteria);
        }

        function getUser(id) {
            return _request(urls.users+"/"+id);
        }


        function updateUser(user) {
            return _request(urls.users+'/'+user.id,'PUT',user);
        }

        function insertUser(user) {
            return _request(urls.users, 'POST', user);
        }

        function getUserPages(searchCriteria) {
            return _request(urls.users+'/pages','POST', searchCriteria);
        }

        function addFile(file) {
            var fd = new FormData();
            fd.append('file', file[0]);
            return $http.post(urls.files, fd, {
                transformRequest: angular.identity,
                headers: {'Content-Type': undefined}
            });
        }
        function getMeetings(searchCriteria) {
            return _request(urls.meetings+"/query", 'POST', searchCriteria);
        }
        function getMeetingPages(searchCriteria) {
            return _request(urls.meetings+'/pages','POST', searchCriteria);
        }
        function updateMeetingLocation(meeting) {
            return _request(urls.meetings+'/location','PUT', meeting);
        }
        function updateMeeting(meeting) {
            return _request(urls.meetings,'PUT', meeting);
        }
        function insertMeeting(multiMeetingInfo) {
            return _request(urls.meetings, 'POST', multiMeetingInfo);
        }
        function getAddress(meetingId) {
            return _request(urls.meetings +"/"+meetingId+"/location")
        }

        /** The DataService with all public methods */
        var service = {
            getEvents: getEvents,
            updateEvents: updateEvents,
            updateEvent: updateEvent,
            insertEvent: insertEvent,
            getEventPages: getEventPages,
            getEvent: getEvent,
            getUsers: getUsers,
            updateUser: updateUser,
            insertUser: insertUser,
            getUserPages: getUserPages,
            getUser: getUser,
            addFile: addFile,
            getMeetings: getMeetings,
            getMeetingPages: getMeetingPages,
            updateMeetingLocation: updateMeetingLocation,
            updateMeeting: updateMeeting,
            insertMeeting: insertMeeting,
            getAddress: getAddress
        };

        return service;

    }
})();