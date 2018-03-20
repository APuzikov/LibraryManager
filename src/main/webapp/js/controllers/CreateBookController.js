(function () {
    'use strict';

    angular.module('mainApp')
        .controller('CreateBookController', ['$routeParams', '$log', '$location', '$timeout', '$scope', 'restService', 'toasterService', CreateBookController]);

    function CreateBookController ($routeParams, $log, $location, $timeout, $scope, restService, toasterService) {

        var vm = this;

        vm.cancelCreation = function() {
            $timeout(function () {
                toasterService.getConfiguredToaster('info', 'Info', 'Book creation canceled');
            }, 10);
            $location.path('/welcome');
        };

        vm.createBook = function() {
            $log.debug($scope.book);

            restService.saveBook($scope.book)
                .then(saveBookSuccess, null)
                .catch(saveBookError);

            function saveBookSuccess(response) {
                $log.debug(response);
                $timeout(function () {
                    toasterService.getConfiguredToaster('success', 'Success', 'Successfully saved new book');
                }, 10);
                $location.path('/welcome');
            }

            function saveBookError(error) {
                $log.debug(error);
                toasterService.getConfiguredToaster('error', 'Error', 'Failed to save new book');
            }
        }
    }
}());
