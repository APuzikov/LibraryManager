(function () {
   'use strict';

   angular.module('mainApp')
      .factory('restService', ['$q', '$http', 'constants', restService]);

   function restService($q, $http, constants) {

      return {
         saveUser: saveUser,
         updateUser: updateUser,
         deleteUser: deleteUser,
         saveBook: saveBook,
         updateBook: updateBook,
         deleteBook: deleteBook,
         getAllBooks: getAllBooks,
         getAllUsers: getAllUsers,
         getBookById: getBookById,
         getUserById: getUserById,
         checkIfPresent: checkIfPresent,
         giveBookToUser: giveBookToUser,
         createPupil: createPupil,
         getDisabledPupils: getDisabledPupils,
         disablePupil: disablePupil,
         enablePupil: enablePupil,
         getDisabledBooks: getDisabledBooks,
         disableBook: disableBook,
         enableBook: enableBook,
         getPupilBooks: getPupilBooks,
         returnBook: returnBook,
         giveBook: giveBook,
         getAllAvailableBooks: getAllAvailableBooks
      };

      function saveUser(data) {
         return $http.post(
            'http://localhost:8082/api/v.1.0/createUser',
            data
         )
            .then(sendResponseData)
            .catch(sendErrorData)
      }

      function createPupil(data) {
         return $http.post(
            'http://localhost:8082/api/v.1.0/createPupil',
            data
         )
            .then(sendResponseData)
            .catch(sendErrorData)
      }

      function updateUser(id, data) {
         return $http.post(
            'http://localhost:8082/api/v.1.0/updatePupil/' + id,
            data
         )
            .then(sendResponseData)
            .catch(sendErrorData)
      }

      function deleteUser(id) {
         return $http.delete(
            'http://localhost:8082/api/v.1.0/deleteUser/' + id
         )
            .then(sendResponseData)
            .catch(sendErrorData)
      }

      function saveBook(data) {
         return $http.post(
            'http://localhost:8082/api/v.1.0/createBook',
            data
         )
            .then(sendResponseData)
            .catch(sendErrorData)
      }

      function updateBook(id, data) {
         return $http.post(
            'http://localhost:8082/api/v.1.0/updateBook/' + id,
            data
         )
            .then(sendResponseData)
            .catch(sendErrorData)
      }

      function giveBookToUser(bookId, userId) {
         return $http.get(
            'http://localhost:8082/api/v.1.0/giveBook/' + bookId + '/' + userId
         )
            .then(sendResponseData)
            .catch(sendErrorData)
      }

      function deleteBook(id) {
         return $http.delete(
            'http://localhost:8082/api/v.1.0/deleteBook/' + id
         )
            .then(sendResponseData)
            .catch(sendErrorData)
      }

      function getAllBooks() {
         return $http.get(
            'http://localhost:8082/api/v.1.0/getAllBooks'
         )
            .then(sendResponseData)
            .catch(sendErrorData)
      }

      function getAllUsers() {
         return $http.get(
            'http://localhost:8082/api/v.1.0/getAllPupil'
         )
            .then(sendResponseData)
            .catch(sendErrorData)
      }

      function getBookById(id) {
         return $http.get(
            'http://localhost:8082/api/v.1.0/getBook/' + id
         )
            .then(sendResponseData)
            .catch(sendErrorData)
      }

      function getUserById(id) {
         return $http.get(
            'http://localhost:8082/api/v.1.0/getpupil/' + id
         )
            .then(sendResponseData)
            .catch(sendErrorData)
      }

      function checkIfPresent(data) {
         return $http.post(
            'http://localhost:8082/api/v.1.0/authenticate',
            data
         )
            .then(sendResponseData)
            .catch(sendErrorData)
      }

      function getDisabledPupils() {
         return $http.get(
            'http://localhost:8082/api/v.1.0/getAllDisabledPupil'
         )
            .then(sendResponseData)
            .catch(sendErrorData)
      }

      function disablePupil(id) {
         return $http.get(
            'http://localhost:8082/api/v.1.0/deactivatePupil/' + id
         )
            .then(sendResponseData)
            .catch(sendErrorData)
      }

      function enablePupil(id) {
         return $http.get(
            'http://localhost:8082/api/v.1.0/activatePupil/' + id
         )
            .then(sendResponseData)
            .catch(sendErrorData)
      }

      function getDisabledBooks() {
         return $http.get(
            'http://localhost:8082/api/v.1.0/getAllDisabledBooks'
         )
            .then(sendResponseData)
            .catch(sendErrorData)
      }

      function disableBook(id) {
         return $http.get(
            'http://localhost:8082/api/v.1.0/deactivateBook/' + id
         )
            .then(sendResponseData)
            .catch(sendErrorData)
      }

      function enableBook(id) {
         return $http.get(
            'http://localhost:8082/api/v.1.0/activateBook/' + id
         )
            .then(sendResponseData)
            .catch(sendErrorData)
      }

      function getPupilBooks(id) {
         return $http.get(
            'http://localhost:8082/api/v.1.0/getPupilBooks/' + id
         )
            .then(sendResponseData)
            .catch(sendErrorData)
      }

      function returnBook(bookId, pupilId) {
         return $http.get(
            'http://localhost:8082/api/v.1.0/returnBook/' + bookId + '/' + pupilId
         )
            .then(sendResponseData)
            .catch(sendErrorData)
      }

      function giveBook(bookId, pupilId) {
         return $http.get(
            'http://localhost:8082/api/v.1.0/giveBook/' + bookId + '/' + pupilId
         )
            .then(sendResponseData)
            .catch(sendErrorData)
      }

      function getAllAvailableBooks(pupilId) {
         return $http.get(
            'http://localhost:8082/api/v.1.0/getAllAvailableBooks/' + pupilId
         )
            .then(sendResponseData)
            .catch(sendErrorData)
      }

      function sendResponseData(response) {
         return response.data;
      }

      function sendErrorData(response) {
         return $q.reject('Error retrieving devices. HTTP status : ' + response.status);
      }
   }
}());
