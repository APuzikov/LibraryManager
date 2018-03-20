(function () {
    'use strict';

    angular.module('mainApp')
        .controller('EditBookController', ['$routeParams', '$log', '$location', '$timeout', 'restService', '$scope', 'toasterService', EditBookController]);

    function EditBookController ($routeParams, $log, $location, $timeout, restService, $scope, toasterService) {

        var vm = this;

        $log.debug($routeParams.id);

        restService.getBookById($routeParams.id)
            .then(getBookByIdSuccess, null)
            .catch(getError);

        function getBookByIdSuccess(response) {
            vm.currentBook = response;
            toasterService.getConfiguredToaster('success', 'Success', 'Successfully get book by id');
        }

        function getError(error) {
            $log.debug(error);
        }

        vm.cancelUpdating = function() {
            $timeout(function () {
                toasterService.getConfiguredToaster('info', 'Info', 'Book editing canceled');
            }, 20);
            $location.path('/welcome');
        };

        vm.updateBook = function() {
            $log.debug(vm.currentBook);

            restService.updateBook($routeParams.id, vm.currentBook)
                .then(updateBookSuccess, null)
                .catch(updateError);

            function updateBookSuccess(response) {
                $log.debug(response);
                $timeout(function () {
                    toasterService.getConfiguredToaster('success', 'Success', 'Book has been successfully updated');
                }, 10);
                $location.path('/welcome');
            }

            function updateError(error) {
                $log.debug(error);
                toasterService.getConfiguredToaster('error', 'Error', 'Failed to update book');
            }
        }
    }
}());
