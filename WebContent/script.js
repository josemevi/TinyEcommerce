angular.module('Ecommerce', [])
  .controller('EcommerceController', function($scope, $http) {

    $scope.login = false;

    $scope.getCategories = function (){
      let data = {
        method: 'GET',
        url: 'http://localhost:8080/Tiny_Ecommerce/getCategories'
      }
      $http(data).then(function successCallback(response) {
          console.log(response);              
          }, function errorCallback(response) {
            console.log(response)
          }
        );
    }
    $scope.getCategories();

    // $scope.forma = {
    // 		name: '',
    // 		surname: '',
    // 		active: false,
    // 		email: '',
    // 		city_id: '',
    // 		bdate: '',
    // 		date: ''
    // }
    // $scope.city = {
    //   name : ''
    // };
    // $scope.userData = [];
    // $scope.citiesData = [];
    // $scope.citiesDataInput = [];
    // $scope.filterUserData = [];
    // $scope.flag = false;   

    // $scope.requestWrap = function (data){
    //   $http(data).then(function successCallback(response) {
    //     console.log(response.data);
    //       if(response.data == 1){
    //         $scope.flag = true;
    //       }else {
    //         //alert("Please confirm you've accepted terms and conditions");
    //       }
    //     }, function errorCallback(response) {
    //       console.log(response)
    //     }
    //   );        
    // }
    
    // $scope.terms = function(selected, memory){
    //   var data = {
    //     method: 'GET',
    //     url: 'http://localhost:8080/testBE_JM/disclaimer'
    //   }
    //   if(selected){
    //     data.url += "?acpt=true"
    //     console.log(data);
    //    $scope.requestWrap(data);
    //   }else if (memory){
    //     $scope.requestWrap(data);
    //   }else{
    //     $scope.flag = false;
    //     alert("You must accept terms and conditions in order to continue");
    //   }
    // }
    // $scope.terms(false,true);

    // $scope.getActiveUser = function () {
    //   $http({
    //     method: 'GET',
    //     url: 'http://localhost:8080/testBE_JM/userList'
    //   }).then(function successCallback(response) {
    //     console.log(response);
    //       $scope.userData = response.data
    //     }, function errorCallback(response) {
    //       console.log(response)
    //     }
    //   );        
    // }
    // $scope.getActiveUser();

    
    // $scope.getCities = function (fTime){      
    //   $http({
    //     method: 'POST',
    //     url: 'http://localhost:8080/testBE_JM/cities',
    //     data: {city_name : $scope.city.name}
    //   }).then(function successCallback(response) {
    //     console.log(response);
    //     if(fTime){
    //       $scope.citiesDataInput = response.data;
    //     }else {
    //       $scope.citiesData = response.data
    //     }          
    //     }, function errorCallback(response) {
    //       console.log(response)
    //     }
    //   );        
    // }
    // $scope.getCities(true);

    // $scope.filterUsers = function (mode){
    //   $http({
    //     method: 'POST',
    //     url: 'http://localhost:8080/testBE_JM/userList',
    //     data: {order: mode}
    //   }).then(function successCallback(response) {
    //     console.log(response);
    //       $scope.filterUserData = response.data
    //     }, function errorCallback(response) {
    //       console.log(response)
    //     }
    //   );
    // }
  
    
    // $scope.create = function (){
    //   console.log($scope.forma)
    //   $http({
    //     method: 'POST',
    //     url: 'http://localhost:8080/testBE_JM/newUser',
    //     data: $scope.forma
    //   }).then(function successCallback(response) {
    //     console.log(response);
    //     alert("User Created!")          
    //     }, function errorCallback(response) {
    //       console.log(response)          
    //       alert(response.data.msg);
    //     }
    //   );

      
    //}


    
    


  
  });