(function(){
    'use strict';
angular.module('SpringBootApp')
        .controller('navigation',
                ['$rootScope', '$scope', '$http', '$location', 'User', function ($rootScope, $scope, $http, $location, User) {

                $scope.user = User;
                
        $scope.user.getCurrentUser();
    }]);
})();