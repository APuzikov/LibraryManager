(function () {
   angular.module('mainApp')
      .controller('HomeController', ['$scope', '$uibModal', '$location', '$document', '$log', 'restService', HomeController]);

   function HomeController($scope, $uibModal, $location, $document, $log, restService) {

      var vm = this;

      vm.animationsEnabled = true;

      vm.open = function (size, parentSelector) {
         var parentElem = parentSelector ?
            angular.element($document[0].querySelector('.modal-demo ' + parentSelector)) : undefined;
         var modalInstance = $uibModal.open({
            animation: vm.animationsEnabled,
            ariaLabelledBy: 'modal-title',
            ariaDescribedBy: 'modal-body',
            templateUrl: 'myModalContent.html',
            controller: 'ModalCtrl',
            controllerAs: 'vm',
            size: size,
            appendTo: parentElem,
            resolve: {
               items: function () {
                  return vm.items;
               }
            }
         });

         modalInstance.result.then(function (selectedItem) {
            vm.selected = selectedItem;
         }, function () {
            $log.info('Modal dismissed at: ' + new Date());
         });
      };
   }
}());