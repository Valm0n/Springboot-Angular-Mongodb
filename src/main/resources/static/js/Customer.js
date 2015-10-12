(function(){
    'use strict';
    
    angular.module('SpringBootApp')
            .service('Customer', Customer);
    
    Customer.$inject = ['$resource'];
    
    function Customer($resource){
        return $resource('customer/:customerId');
    }
    
})();

