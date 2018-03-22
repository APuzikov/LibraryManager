(function () {
   'use strict';

   angular.module('mainApp')
      .controller('PupilsController', ['restService', '$scope',
         '$log', '$rootScope', '$location', 'toasterService', '$uibModal',
         '$document', PupilsController]);

   function PupilsController(restService, $scope, $log, $rootScope,
                             $location, toasterService, $uibModal, $document) {
      var vm = this;

      restService.getAllUsers()
         .then(getUsersSuccess, null)
         .catch(getUsersError);

      vm.disablePupil = function (id) {
         $log.debug(id);
         restService.disablePupil(id)
            .then(successfullyDisablePupil)
            .catch(failedToDisablePupil)
      };

      function successfullyDisablePupil(response) {
         $log.debug(response);
         restService.getAllUsers()
            .then(getUsersSuccess, null)
            .catch(getUsersError);
         toasterService.getConfiguredToaster('success', 'Success', 'Successfully disable pupil');
      }

      function failedToDisablePupil(error) {
         $log.debug(error);
         toasterService.getConfiguredToaster('error', 'Error', 'Failed to' +
            ' disable pupil');
      }

      vm.enablePupil = function (id) {
         $log.debug(id);
         restService.enablePupil(id)
            .then(successfullyEnablePupil)
            .catch(failedToEnablePupil)
      };

      function successfullyEnablePupil(response) {
         $log.debug(response);
         restService.getDisabledPupils()
            .then(getDisabledPupilsSuccess)
            .catch(getDisabledPupilsError);
         toasterService.getConfiguredToaster('success', 'Success', 'Successfully enable pupil');
      }

      function failedToEnablePupil(error) {
         $log.debug(error);
         toasterService.getConfiguredToaster('error', 'Error', 'Failed' +
            ' enable pupil');
      }

      vm.showDisabledPupils = function () {
         restService.getDisabledPupils()
            .then(getDisabledPupilsSuccess)
            .catch(getDisabledPupilsError);
      };

      vm.showEnabledPupils = function () {
         restService.getAllUsers()
            .then(getUsersSuccess, null)
            .catch(getUsersError);
      };

      function getUsersSuccess(response) {
         $log.debug(response);
         vm.allPupils = response;
         toasterService.getConfiguredToaster('success', 'Success', 'Successfully got all pupils');
      }

      function getUsersError(error) {
         $log.debug(error);
         toasterService.getConfiguredToaster('error', 'Error', 'Failed to load all pupils');
      }


      function getDisabledPupilsSuccess(response) {
         $log.debug(response);
         vm.allDisabledPupils = response;
         toasterService.getConfiguredToaster('success', 'Success', 'Successfully got all disabled pupils');
      }

      function getDisabledPupilsError(error) {
         $log.debug(error);
         toasterService.getConfiguredToaster('error', 'Error', 'Failed to' +
            ' load all disabled pupils');
      }

      // Ui bootstrap modal

      vm.animationsEnabled = true;

      vm.open = function (id, size, parentSelector) {
         $log.debug(id);
         var parentElem = parentSelector ?
            angular.element($document[0].querySelector('.modal-demo ' + parentSelector)) : undefined;
         var modalInstance = $uibModal.open({
            animation: vm.animationsEnabled,
            ariaLabelledBy: 'modal-title',
            ariaDescribedBy: 'modal-body',
            templateUrl: 'myModalContent.html',
            controller: 'ModalBookReturningController',
            controllerAs: 'vm',
            size: size,
            appendTo: parentElem,
            resolve: {
               items: function () {
                  vm.items = id;
                  return vm.items;
               }
            }
         });

         modalInstance.result.then(function () {
            // Make something on modal close
         }, function () {
            $log.info('Modal dismissed at: ' + new Date());
         });
      };
   }
}());
