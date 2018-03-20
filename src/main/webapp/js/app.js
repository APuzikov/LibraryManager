(function () {
    'use strict';

    var mainApp = angular.module('mainApp',
        ['ngSanitize',
            'ngRoute',
            'ngCookies',
            'toaster',
            'ngAnimate',
           'ui.bootstrap']);

    mainApp.provider('appData', ['constants', function (constants) {
        this.$get = function () {

            var appName = constants.APP_NAME;
            var authors = constants.AUTHORS;
            var appVersion = constants.APP_VERSION;

            return {
                appName: appName,
                appAuthors: authors,
                appVersion: appVersion
            };
        };
    }]);

    mainApp.config([
        '$logProvider',
        '$routeProvider',
        '$locationProvider',
        '$httpProvider',
        function ($logProvider, $routeProvider, $locationProvider, $httpProvider) {
            $logProvider.debugEnabled(true);

            $routeProvider
                .when('/', {
                    controller: 'LoginController',
                    controllerAs: 'vm',
                    templateUrl: 'js/partials/login.html'
                })
                .when('/home', {
                    controller: 'HomeController',
                    controllerAs: 'vm',
                    templateUrl: 'js/partials/welcome.html'
                })
                .when('/register', {
                    controller: 'RegisterController',
                    controllerAs: 'vm',
                    templateUrl: 'js/partials/register.html'
                })
                .when('/createBook', {
                    controller: 'CreateBookController',
                    controllerAs: 'vm',
                    templateUrl: 'js/partials/createBook.html'
                })
                .when('/createUser', {
                    controller: 'CreateUserController',
                    controllerAs: 'vm',
                    templateUrl: 'js/partials/createPupil.html'
                })
                .when('/books', {
                    controller: 'BooksController',
                    controllerAs: 'vm',
                    templateUrl: 'js/partials/books.html',
                    pageName: 'Books'
                })
                .when('/pupils', {
                    controller: 'PupilsController',
                    controllerAs: 'vm',
                    templateUrl: 'js/partials/pupils.html',
                    pageName: 'Pupils'
                })
                .when('/editBook/:id', {
                    controller: 'EditBookController',
                    controllerAs: 'vm',
                    templateUrl: 'js/partials/editBook.html'
                })
                .when('/editPupil/:id', {
                    controller: 'EditPupilController',
                    controllerAs: 'vm',
                    templateUrl: 'js/partials/editPupil.html'
                })
                .otherwise('/home')
        }
    ]);

    mainApp.run(['$rootScope', '$templateCache', '$log', 'AuthService', function ($rootScope, $templateCache, $log, AuthService) {

        $rootScope.$on('$routeChangeStart', function (event, current, previous, next) {
            $log.debug(event);
            $log.debug(current);
            $log.debug(previous);
            $log.debug(next);
            $rootScope.isAuthenticated = AuthService.checkCredentials();
            $rootScope.pageName = current.$$route.pageName;
        });

        $rootScope.$on('$routeChangeSuccess', function (event, current, previous) {
            console.log('successfully changed routes');
        });

        $rootScope.$on('$routeChangeError', function (event, current, previous, rejection) {
            console.log('error changing routes');

            $log.debug(event);
            $log.debug(current);
            $log.debug(previous);
            $log.debug(rejection)
        })
    }])
}());
