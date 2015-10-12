(function(){
    'use strict';
    angular.module('SpringBootApp')
            .controller('MainCtrl', MainCtrl);
    
    MainCtrl.$inject = ['$scope', 'User', 'Customer', 'uiGridConstants'];
    
    function MainCtrl($scope, User, Customer, uiGridConstants){
        $scope.title = "Spring Boot / MongoDB / AngularJS";
        
        $scope.user = User.user;
        
        $scope.customers = Customer.query();
        
        $scope.editedEntry = false;
        $scope.newEntry = false;
        
        $scope.entryModel = {};
        
        $scope.gridOptions = {
            data : 'customers',
            columnDefs: [
                { field: 'id', visible: false },
                { field: 'firstName', displayName: 'Prénom' },
                { field: 'lastName', displayName: 'Nom' },
                { field: 'race', displayName: 'Planète d\'origine' },
                { field: 'job', displayName: 'Activité' }
            ],
            enableRowSelection: true,
            enableFullRowSelection: true,
            enableRowHeaderSelection: false,
            enableSelectAll: false,
            multiSelect: false,
            showGridFooter:true,
            onRegisterApi: function( gridApi ) {
                $scope.grid1Api = gridApi;
                $scope.grid1Api.selection.on.rowSelectionChanged($scope,function(row){
                    $scope.selectedEntry = row.isSelected ? row.entity : null;
                });
            }
        };
        
        $scope.createOrEditEntry = function(newEntry){
            $scope.newEntry = newEntry;
            $scope.entryModel = newEntry ? {} : angular.copy($scope.selectedEntry);
            $scope.editedEntry = true;
        };
        
        $scope.saveEntry = function(){
                //todo: validate form
                Customer.save($scope.entryModel, function(entry){
                            $scope.editedEntry=false;
                            if($scope.newEntry){
                                $scope.customers.splice(0,0,entry);
                            } else {
                                $scope.customers.splice($scope.customers.indexOf($scope.selectedEntry),1,entry);
                            }
                            $scope.gridOptions.data = $scope.customers;
                            $scope.selectedEntry = entry;
                }, function(err){ 
                    alert(err.data.message); 
                });
        };
        
        $scope.removeEntry = function(){
            $scope.selectedEntry.$delete({customerId : $scope.selectedEntry.id}, function(){
                $scope.selectedEntry = null;
                $scope.entryModel = {};
                $scope.editedEntry = false;
                $scope.customers = Customer.query();
            }, function(err){ 
                alert(err.data.message); 
            });
        };
        
        $scope.tryAndLoadImg = function(){
            $scope.tryImgUrl = $scope.entryModel.imgUrl;
        };
    }
})();
