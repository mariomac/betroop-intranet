(function() {
    'use strict';

    angular
        .module('app')
        .controller('UsersController', UsersController);


    function UsersController($log, $scope, $uibModal, DataService, Alerts, Loading) {
        var vm = this;

        $scope.roles = [{ id: 0, label: "Particular"},
            {id: 1, label: "Bar"},
            {id: -1, label: "Todos"}];

        $scope.orderBy = [{ label : "Fecha ASC", field: "create_time", sortByAsc : true},
            { label : "Fecha DESC", field: "create_time", sortByAsc : false},
            { label : "Nombre ASC", field: "name", sortByAsc : true},
            { label : "Nombre DESC", field: "name", sortByAsc : false},
        ];

        $scope.selectedOrderBy = $scope.orderBy[1];
        $scope.selectedRole = $scope.roles[2];

        $scope.currentPage = 1;
        $scope.pages = 1;
		$scope.searchtext="";

        $scope.showDate = function(millis){
            return showDate(millis);
        };
		var loadPages = function() {
			var searchCriteria = {
				text : $scope.searchtext,
                rol : $scope.selectedRole.id
			};
			DataService.getUserPages(searchCriteria).then(function(pages) {
				$scope.pages = pages;
			}, function() {
				Alerts.addAlert("Error contando el numero de páginas a mostrar", 'danger');
			});
		};

        $scope.pageChanged = function() {
            $log.log("Changing page to: " + $scope.currentPage);
            vm.getUsers();
        };

        vm.getUsers = function() {
            Loading.setLoading();
			var searchCriteria = {
				page : $scope.currentPage,
				text : $scope.searchtext,
                rol : $scope.selectedRole.id,
                orderBy : $scope.selectedOrderBy.field,
                orderByAsc : $scope.selectedOrderBy.sortByAsc
            };
			loadPages();
            return DataService.getUsers(searchCriteria).then(function(users) {
                vm.users = users;
                Loading.unsetLoading();
            }, function() {
                Alerts.addAlert("Error obteniendo lista de usuarios", 'danger');
                Loading.unsetLoading();
            });
        };

        init();
        function init() {
			loadPages();
            return vm.getUsers();
        }

        vm.newUser = function() {
            var modalInstance = $uibModal.open({
                controller: 'UserInfoController',
                templateUrl: '/static/content/user.html',
                size: 'lg',
                resolve: {
                    user: function() {
                        return { role: "BAR" };
                    }
                }
            });
            modalInstance.result.then(function(newUser) {
                newUser.image_modified = true;
                Loading.setLoading();
                DataService.insertUser(newUser).then(
                    function() {
                        Loading.unsetLoading();
                        vm.getUsers();
                    },
                    function() {
                        Loading.unsetLoading();
                        Alerts.addAlert("Error insertando usuario", 'danger');
                    }
                );
            });
        }

        vm.showUserInfo = function(user) {
            var modalInstance = $uibModal.open({
                controller: 'UserInfoController',
                templateUrl: '/static/content/user.html',
                size: 'lg',
                resolve: {
                    user: function() {
                        return user;
                    }
                }
            });
            // If the modal is correctly closed, it gets the updated
            // user information
            modalInstance.result.then(function(updatedUser) {
                Loading.setLoading();
                DataService.updateUser(updatedUser).then(
                    function() {
                        Loading.unsetLoading();
                        copyUser(updatedUser,user);
                    },
                    function() {
                        Loading.unsetLoading();
                        Alerts.addAlert("Error actualizando usuario", 'danger');
                    }
                );
            });
        };
    }

    var copyUser = function(src,dst) {
        dst.id = src.id;
        dst.name = src.name;
        dst.email = src.email;
        dst.photoUrl = src.photoUrl;
        dst.role = src.role;
    };

    angular
        .module('app')
        .controller('UserInfoController', UserInfoController);

    function UserInfoController($uibModalInstance, $scope, user, Alerts, Loading, DataService) {

        // deep clone
        $scope.user = JSON.parse(JSON.stringify(user));

        $scope.userRoles = ["PARTICULAR","BAR"];
        $scope.showDate = function(millis){
            return showDate(millis);
        };
        $scope.ok = function () {
            $uibModalInstance.close($scope.user);
        };

        $scope.cancel = function () {
            $uibModalInstance.dismiss();
        };

        $scope.uploadFile = function(file) {
            Loading.setLoading();
            DataService.addFile(file).then(
                function(result) {  // success
                    Loading.unsetLoading();
                    $scope.user.photoUrl = result.data;
                },
                function(err) {
                    Loading.unsetLoading();
                    Alerts.addAlert("Error cargando archivo usuario: " + err.error, 'danger');
                    $scope.errorMsg = err.error;
                    console.log("Error uploading file");
                    console.log(err);

                }
            );

        }
    }

})();