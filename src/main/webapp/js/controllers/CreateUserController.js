(function () {
    'use strict';

    angular.module('mainApp')
        .controller('CreateUserController', ['$routeParams', '$log', '$location', '$timeout', '$scope', 'restService', 'toasterService', CreateUserController]);

    function CreateUserController ($routeParams, $log, $location, $timeout, $scope, restService, toasterService) {

        var vm = this;

        vm.newPupil = {};

        vm.cancelCreation = function() {
            $timeout(function () {
                toasterService.getConfiguredToaster('info', 'Info', 'Pupil creation canceled');
            }, 10);
            $location.path('/welcome');
        };

        vm.createUser = function() {
            $log.debug($scope.pupil);

            restService.createPupil($scope.pupil)
                .then(creationSuccess, null)
                .catch(creationFailure);

            function creationSuccess(response) {
                $log.debug(response);
                $timeout(function () {
                    toasterService.getConfiguredToaster('success', 'Success', 'Successfully saved new pupil');
                }, 10);
                $location.path('/welcome');
            }

            function creationFailure(error) {
                $log.debug(error);
                toasterService.getConfiguredToaster('error', 'Error', 'Failed to save new pupil');
            }
        }
    }
}());
