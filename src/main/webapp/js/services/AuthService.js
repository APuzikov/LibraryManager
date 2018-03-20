(function(){
    angular.module('mainApp')
        .factory('AuthService', ['$location', '$cookies',AuthService]);

        function AuthService($location, $cookies){
            return {
               login: login,
               register: register,
               checkCredentials: checkCredentials
            };

            function login(data) {
               $cookies.put('isAuthorized', true);
            }

            function register(data) {

            }

            function checkCredentials() {
               if($cookies.get('isAuthorized')) {
                  return true;
               } else {
                  return false;
               }
            }
        }
}());