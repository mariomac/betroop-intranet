(function() {
    'use strict';

    angular
        .module('app')
        .factory('Loading', Loading);

    function Loading($uibModal) {
        var vm = this;

        var modalInstance = null;

        vm.setLoading = function () {
            if (modalInstance == null) {
                modalInstance = $uibModal.open({
                    animation: false,
                    template: '<div class="modal-body">Cargando...</div>',
                    size: 'sm'
                });
            }
        };

        vm.unsetLoading = function () {
            if(modalInstance != null) {
                modalInstance.dismiss();
                modalInstance = null;
            }
        }

        return vm;
    }
})();

