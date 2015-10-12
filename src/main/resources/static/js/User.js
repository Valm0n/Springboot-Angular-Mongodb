(function(){
    'use strict';

    angular.module('SpringBootApp').service('User', UserService);

    UserService.$inject = ['$resource', '$http', '$location'];
    
    function UserService($resource, $http, $location){
                
        var that = this;
        var UserResource = $resource('user');

        this.user = null;

        this.getCurrentUser = function(){
            if(this.user === null){
                return UserResource.get().$promise
                    .then(function (data) {
                        if (data.name) {
                            var currentUser = data;
                            currentUser.hasAuthority = function(authority){
                                var hasAuthority = false;
                                currentUser.authorities.some(function(auth){
                                    if(authority === auth.authority){
                                        hasAuthority = true;
                                        return true;
                                    }
                                });
                                return hasAuthority;
                            };
                            that.user = currentUser;
                            return currentUser;
                        } else {
                            that.user = null;
                            return null;
                        }
                }, function (err) {
                    alert(err);
                });
            } else {
                return this.user;
            }
        };
        
        this.logout = function(){
            $http.post('/logout');
            this.user = null;
            $location.url('/');
        };
    }
})();