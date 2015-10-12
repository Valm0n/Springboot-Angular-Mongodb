(function(){
    'use strict';
    angular.module('SpringBootApp')
      .controller('homeCtrl', ['$scope', 'User', function($scope, User) {
        $scope.user = User;
    }]);
})();