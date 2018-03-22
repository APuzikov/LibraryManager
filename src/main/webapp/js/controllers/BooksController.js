(function () {
   'use strict';

   angular.module('mainApp')
      .controller('BooksController', ['restService', '$scope', '$log', '$rootScope', '$location', 'toasterService', BooksController]);

   function BooksController(restService, $scope, $log, $rootScope, $location, toasterService) {
      var vm = this;

      restService.getAllBooks()
         .then(getAllBooksSuccess, null)
         .catch(getAllBooksError);

      vm.enableBook = function(id) {
         $log.debug(id);
         restService.enableBook(id)
            .then(successfullyEnableBook)
            .catch(failedToEnableBook)
      };

      function successfullyEnableBook(response) {
         $log.debug(response);
         restService.getDisabledBooks()
            .then(getDisabledBooksSuccess)
            .catch(getDisabledBooksError);
         toasterService.getConfiguredToaster('success', 'Success', 'Successfully enable book');
      }

      function failedToEnableBook(error) {
         $log.debug(error);
         toasterService.getConfiguredToaster('error', 'Error', 'Failed to' +
            ' enable book');
      }

      vm.disableBook = function(id) {
         $log.debug(id);
         restService.disableBook(id)
            .then(successfullyDisableBook)
            .catch(failedToDisableBook)
      };

      function successfullyDisableBook(response) {
         $log.debug(response);
         restService.getAllBooks()
            .then(getAllBooksSuccess, null)
            .catch(getAllBooksError);
         toasterService.getConfiguredToaster('success', 'Success', 'Successfully disable book');
      }

      function failedToDisableBook(error) {
         $log.debug(error);
         toasterService.getConfiguredToaster('error', 'Error', 'Failed to' +
            ' disable book');
      }

      vm.showEnabledBooks = function() {
         restService.getAllBooks()
            .then(getAllBooksSuccess, null)
            .catch(getAllBooksError);
      };

      vm.showDisabledBooks = function() {
         restService.getDisabledBooks()
            .then(getDisabledBooksSuccess)
            .catch(getDisabledBooksError)
      };

      function getDisabledBooksSuccess(response) {
         $log.debug(response);
         vm.allDisabledBooks = response;
         toasterService.getConfiguredToaster('success', 'Success', 'Successfully got all disabled books');
      }

      function getDisabledBooksError(error) {
         $log.debug(error);
         toasterService.getConfiguredToaster('error', 'Error', 'Failed to' +
            ' load all disabled books');
      }

      function getAllBooksSuccess(response) {
         $log.debug(response);
         vm.allBooks = response;
         toasterService.getConfiguredToaster('success', 'Success', 'Successfully got all books');
      }

      function getAllBooksError(error) {
         $log.debug(error);
         toasterService.getConfiguredToaster('error', 'Error', 'Failed to load all books');
      }
   }
}());
