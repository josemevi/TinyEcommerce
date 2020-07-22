angular.module('Ecommerce', [])
  .controller('EcommerceController', function($scope, $http) {

    $scope.signup = false;
    $scope.login = false;
    $scope.noItems = false;

    $scope.userId = "";

    $scope.itemList = [];
    $scope.userCart = [];
    $scope.categories = [];

    $scope.maleCheck = false;
    $scope.womancheck = false;

    $scope.searchString = "";

    $scope.user = {
      name: '',
      lastname : '',
      email : '',
      password : '',
      repassword: ''
    }

    $scope.getCategories = function (){
      let data = {
        method: 'GET',
        url: 'http://localhost:8080/Tiny_Ecommerce/getCategories'
      }
      $http(data).then(function successCallback(response) {          
          $scope.categories = response.data.categories;            
          }, function errorCallback(response) {
            console.log(response);            
          }
        );
    }
    $scope.getCategories();

    $scope.getItems = function (category, search){      
      let _url = "http://localhost:8080/Tiny_Ecommerce/getItems?searchString="+$scope.searchString
      if($scope.maleCheck && !$scope.womanCheck){
        _url = _url+"&mode=male";
      }else if($scope.womanCheck && !$scope.manCheck){
        _url = _url+"&mode=female";
      }
      if(category){
        _url = _url+"&mode=category&catalogId="+category;
      }
      console.log(_url);
      let data = {
        method: 'GET',
        url: _url
      }
      $http(data).then(function successCallback(response) {
        console.log(response);
        if(response.status == 200) {
          $scope.itemList = response.data.items;
          $scope.noItems = false;            
        }else {
          $scope.itemList = [];
          $scope.noItems = true;
        }          
          
      }, function errorCallback(response) {
            console.log(response);            
        }
      );
    }

    $scope.getItems();

    $scope.goToSex = function (value){
      if (value == 0 && !$scope.maleCheck){
        $scope.maleCheck = true;
        $scope.womanCheck = false;
        $scope.getItems('','');
      } else if (value == 0 && $scope.maleCheck) {
        $scope.maleCheck = false;
        $scope.getItems('','');
      }else if(value == 1 && !$scope.womanCheck) {
        $scope.womanCheck = true;
        $scope.maleCheck = false;
        $scope.getItems('','');
      }else if(value == 1 && $scope.womanCheck) {
        $scope.womanCheck = false;
        $scope.getItems('','');
      }
    }

    $scope.showCategory = function (catId){
      $scope.getItems(catId,'');
    }

    // $scope.searchItems = function (search){
    //   $scope.getItems('',search);
    // }

    $scope.toggleModal = function (option){   
      console.log(option)   
      $('#modal').modal('show');
    }

    $scope.toggleSignUp = function (){
      $scope.signup = true;
    }

    $scope.cleanUserData = function (){
      $scope.user = {
        name: '',
        lastname : '',
        email : '',
        password : '',
        repassword: ''
      }
    }

    $scope.cancelSignUp = function() {
      $scope.signup = false;
      $scope.cleanUserData();
    }

    $scope.signIn = function(){      
      let data = {
        method: 'POST',        
        url: 'http://localhost:8080/Tiny_Ecommerce/login',
        data : {"email": $scope.user.email, "password": $scope.user.password},
      }      
      $http(data).then(function successCallback(response) {
        console.log(response)          
            if(response.data.login){
              $("#modal .close").click();
              $scope.login = true;
              $scope.userId = response.data.userId;
              $scope.userCart = response.data.cart;
              $scope.cleanUserData();             
            }            
          }, function errorCallback(response) {
            console.log(response);
            alert(response.data.msg)            
          }
        );
    }

    $scope.checkSession = function(){
      $scope.cleanUserData();
      let data = {
        method: 'POST',        
        url: 'http://localhost:8080/Tiny_Ecommerce/login',
        data : {"email": $scope.user.email, "password": $scope.user.password},        
      }      
      $http(data).then(function successCallback(response) {
        console.log(response)          
            if(response.data.login){
              $scope.login = true;
              $scope.userId = response.data.userId;
              $scope.userCart = response.data.cart;
              console.log($scope.userId, $scope.userCart);             
            }            
          }, function errorCallback(response) {

          }
        );
    }
    $scope.checkSession();

    $scope.signUp = function(){
      console.log($scope.user);
      if($scope.user.password.localeCompare($scope.user.repassword) == 0){
        let data = {
          method: 'POST',        
          url: 'http://localhost:8080/Tiny_Ecommerce/signup',
          data: {
            "email": $scope.user.email,
            "password" : $scope.user.password,
            "name" : $scope.user.name,
            "lastname" : $scope.user.lastname
          }        
        }      
        $http(data).then(function successCallback(response) {
          console.log(response)          
              if(response.status == 201){
                $scope.signup = false;
                $scope.cleanUserData();
                alert("Please Log In");
              }            
            }, function errorCallback(response) {
              console.log(response);
              alert(response.data.msg)            
            }
          );
      }else {
        alert("Password doesn't match")
      }      
    }

    $scope.logout = function(){
      let data = {
        method: 'GET',        
        url: 'http://localhost:8080/Tiny_Ecommerce/logout',        
      }      
      $http(data).then(function successCallback(response) {
        console.log(response)          
            if(!response.data.login){
              $("#modal .close").click();
              $scope.login = false;
              $scope.userId = "";
              $scope.userCart = "";                            
            }            
          }, function errorCallback(response) {
            console.log(response);
            alert(response.data.msg)            
          }
        );
    }
    

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