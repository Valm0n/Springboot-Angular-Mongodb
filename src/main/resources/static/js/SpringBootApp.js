(function(){
    'use strict';
    angular.module('SpringBootApp', ['ngResource', 'ui.router', 'ui.grid', 'ui.grid.selection'])
    
        .run(['$rootScope', '$state', '$stateParams', 
                function($rootScope, $state, $stateParams){
                    $rootScope.state = $state;
                    $rootScope.stateParams = $stateParams;
                }
            ]
        )
    
        .config(['$stateProvider', '$urlRouterProvider', '$httpProvider',
                function($stateProvider, $urlRouterProvider, $httpProvider) {
                    
                    $urlRouterProvider.otherwise('/');
                    
                    $stateProvider
                        .state('app', {
                            abstract: true,
                            template: '<ui-view/>'
                    })
                        .state('app.home', {
                            url: "/",
                            templateUrl: 'partials/home.html',
                            controller: 'homeCtrl'
                        })
                        .state('app.list', {
                            url: "/list",
                            templateUrl: 'partials/list.html',
                            controller: 'MainCtrl',
                            data: {
                                minRole: 'USER'
                            },
                            onEnter: function($location, User){
                                if(User.user === null){
                                    $location.url('/login');
                                }
                            }
                        });
                        
                    $httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';
                }
            ]
        );
})();