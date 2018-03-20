(function () {
    angular.module('mainApp')
        .controller('LoginController', ['$location', '$rootScope', 'AuthService', '$timeout', 'toasterService', LoginController]);

    function LoginController($location, $rootScope, AuthService, $timeout, toasterService) {

        var vm = this;

        vm.login = function () {
            AuthService.login(null);
            $location.path('/home');
            $timeout(function () {
                toasterService.getConfiguredToaster('success', 'Success', 'Successfully logged in');
            }, 10);
        };

        vm.register = function () {
            $timeout(function () {
                toasterService.getConfiguredToaster('info', 'Registration page', 'Please, register an account');
            }, 10);
            $location.path('/register');
        }
    }
}());