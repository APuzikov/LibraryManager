(function () {
   angular.module('mainApp')
      .controller('MainController', ['$rootScope', 'AuthService', '$location', '$cookies', '$scope', '$timeout', 'toasterService', MainController]);

   function MainController($rootScope, AuthService, $location, $cookies, $scope, $timeout, toasterService) {
      var vm = this;

      vm.logout = function () {
         $cookies.remove('isAuthorized');
         $rootScope.isAuthenticated = false;
         $timeout(function () {
             toasterService.getConfiguredToaster('success', 'Success', 'Successfully logged out');
         }, 10);
         $location.path('/');
      }
   }
}());