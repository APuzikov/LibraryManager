(function () {
    'use strict';

    angular.module('mainApp')
        .controller('EditPupilController', ['$routeParams', '$log', '$location', '$timeout', 'restService', 'toasterService', EditPupilController]);

    function EditPupilController ($routeParams, $log, $location, $timeout, restService, toasterService) {

        var vm = this;

        $log.debug($routeParams.id);

        restService.getUserById($routeParams.id)
            .then(getUserByIdSuccess, null)
            .catch(getError);

        function getUserByIdSuccess(response) {
            $log.debug(response);
            vm.currentPupil = response;
            toasterService.getConfiguredToaster('success', 'Success', 'Successfully get pupil by id');
        }

        function getError(error) {
            $log.debug(error);
        }

        vm.cancelUpdating = function () {
            $timeout(function () {
                toasterService.getConfiguredToaster('info', 'Info', 'Pupil editing canceled');
            }, 10);
            $location.path('/welcome');
        };

        vm.updateUser = function () {
            $log.debug(vm.currentPupil);

            restService.updateUser($routeParams.id, vm.currentPupil)
                .then(updateUserSuccess, null)
                .catch(updateError);

            function updateUserSuccess(response) {
                $log.debug(response);
                $timeout(function () {
                    toasterService.getConfiguredToaster('success', 'Success', 'Pupil has been successfully updated');
                }, 10);
                $location.path('/welcome')
            }

            function updateError(error) {
                $log.debug(error);
                toasterService.getConfiguredToaster('error', 'Error', 'Failed to update pupil');
            }
        }
    }
}());
