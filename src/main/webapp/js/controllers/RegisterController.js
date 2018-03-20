(function() {
    'use strict';

    angular.module('mainApp')
        .controller('RegisterController', ['restService', '$scope', '$log', '$rootScope', '$location', '$timeout', 'toasterService', RegisterController]);

    function RegisterController(restService, $scope, $log, $rootScope, $location, $timeout, toasterService) {
        var vm = this;

        vm.login = function() {
            $timeout(function () {
                toasterService.getConfiguredToaster('info', 'Login page', 'Please, enter your credentials');
            }, 10);
            $location.path('/');
        };

        vm.register = function() {
            $log.debug($scope.register);

            restService.saveUser($scope.register)
                .then(userRegistered, null)
                .catch(errorDuringRegistration)

            function userRegistered(response) {
                $log.debug(response);
                $timeout(function () {
                    toasterService.getConfiguredToaster('success', 'Success', 'Successfully register new account');
                }, 10);
                $location.path('/');
            }

            function errorDuringRegistration(error) {
                $log.debug(error);
                toasterService.getConfiguredToaster('error', 'Error', 'Failed to register new user');
            }
        }
    }
}());
