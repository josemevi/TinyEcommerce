angular.module('Ecommerce', [])
  .controller('EcommerceController', function($scope, $http) {

    $scope.signup = false;
    $scope.login = false;
    $scope.noItems = false;
    $scope.payment = false;

    $scope.userId = "";
    $scope.cartId = "";
    $scope.payTotal = 0;

    $scope.itemList = [];
    $scope.userCart = [];
    $scope.categories = [];
    $scope.userData = [];
    $scope.docTypes = [];

    $scope.maleCheck = false;
    $scope.womancheck = false;
    $scope.confirmPay = false;

    $scope.searchString = "";

    $scope.user = {
      name: '',
      lastname : '',
      email : '',
      password : '',
      repassword: '',
      docType: '',
      docNumber: '',
      direction: '',
      ccNumber: '',
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
      if(option == 0 ){
        $('#modal').modal('show');
      }else if (option == 1){
        $scope.getUserInfo();
        $scope.getDocTypes();        
      } else {
        $('#modalCart').modal('show');
      }   
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
              if(response.data.cart){
                if(response.data.cart.length > 0){                			
                  $scope.userCart = response.data.cart;
                  $scope.total();
                }      
              }                      
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
              if(response.data.cart){
                if(response.data.cart.length > 0){
                  $scope.userCart = response.data.cart;
                  $scope.total();
                }
              }              
              $scope.cleanUserData();
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
              $scope.userCart = [];                            
            }            
          }, function errorCallback(response) {
            console.log(response);
            alert(response.data.msg)            
          }
        );
    }
    var oldpassword = ""
    $scope.getUserInfo = function (){
      let data = {
        method: 'GET',        
        url: 'http://localhost:8080/Tiny_Ecommerce/getUserInfo',        
      }      
      $http(data).then(function successCallback(response) {          
            console.log(response);
            if(response.status == 200){
              $scope.userData = response.data.userData;
              $scope.user.ccNumber = response.data.userData.cc_number;
              $scope.user.direction = response.data.userData.direction;
              $scope.user.docType = response.data.userData.document_type;
              $scope.user.docNumber = response.data.userData.document_number;
              $scope.user.email = response.data.userData.email;
              $scope.user.lastname = response.data.userData.lastname;
              $scope.user.name = response.data.userData.name;
              oldpassword = response.data.userData.password;
              $('#modalUser').modal('show');
            }          
          }, function errorCallback(response) {
            console.log(response);
            alert(response.data.msg)            
          }
        );
    }

    $scope.editUserInfo = function (){
      console.log($scope.user);
      let flag = true;
      if($scope.user.password.length == 0){
        flag = false;
        $scope.user.password = oldpassword;
      }
      if(!flag || $scope.user.password.localeCompare($scope.user.repassword) == 0){
        let data = {
          method: 'PUT',        
          url: 'http://localhost:8080/Tiny_Ecommerce/editUserInfo',
          data: {
            "email": $scope.user.email,
            "password" : $scope.user.password,
            "name" : $scope.user.name,
            "lastName" : $scope.user.lastname,
            "document_type" : $scope.user.docType,
            "document_number" : $scope.user.docNumber,
            "direction" : $scope.user.direction,
            "ccNumber" : $scope.user.ccNumber
          }        
        }      
        $http(data).then(function successCallback(response) {
          console.log(response)          
              if(response.status == 200){                
                alert("Information Updated");
                $("#modalUser .close").click();
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
    
    $scope.getDocTypes = function () {
      let data = {
        method: 'GET',        
        url: 'http://localhost:8080/Tiny_Ecommerce/getDocumentTypes',        
      }      
      $http(data).then(function successCallback(response) {        
            console.log(response);
            if(response.status == 200){
              $scope.docTypes = response.data.documents;
            }              
          }, function errorCallback(response) {
            console.log(response);
            alert(response.data.msg)            
          }
        );    
    }

    $scope.addToCart = function (item){      
      var cartItem = {
        "item_id": item.item_id,
        "amount" : 1,
        "name" : item.name,
        "description" : item.description,
        "price" : item.price,
        "image" : item.images
      }
      if($scope.userCart.length > 0){
        let flag = false;
        for(let i = 0; i < $scope.userCart.length; i++){          
          if($scope.userCart[i].name == cartItem.name){
            $scope.userCart[i].amount = $scope.userCart[i].amount+1;
            flag = true;
            break;            
          }
        }
        if(!flag){          
          $scope.userCart.push(cartItem);          
        }
      }else {
        $scope.userCart.push(cartItem);
      }
        console.log($scope.userCart);
        let data = {
          method: 'POST',        
          url: 'http://localhost:8080/Tiny_Ecommerce/editCartItems',
          data : "{\"items\":"+JSON.stringify($scope.userCart)+"}"       
        }              
        $http(data).then(function successCallback(response) {        
              console.log(response);
              if(response.status == 200 || response.status == 201){                
                alert(response.data.msg);
                $scope.total();
              }              
            }, function errorCallback(response) {
              console.log(response);
              alert(response.data.msg)            
            }
          );    
    }
    
    $scope.removeItem = function (index){
      $scope.userCart.splice(index, 1)
      console.log($scope.userCart);
        let data = {
          method: 'POST',        
          url: 'http://localhost:8080/Tiny_Ecommerce/editCartItems',
          data : "{\"items\":"+JSON.stringify($scope.userCart)+"}"       
        }             
        $http(data).then(function successCallback(response) {        
              console.log(response);
              if(response.status == 200 || response.status == 201){
                $scope.total();                
                alert(response.data.msg);                
              }              
            }, function errorCallback(response) {
              console.log(response);
              alert(response.data.msg)            
            }
          );    
      }

      $scope.total = function () {
        $scope.payTotal = 0;
        for(let i = 0 ; i < $scope.userCart.length; i++){
          $scope.payTotal = parseFloat($scope.userCart[i].price)*$scope.userCart[i].amount + $scope.payTotal;          
        }
      }
  


      $scope.getCart = function(){
        let data = {
          method: 'GET',        
          url: 'http://localhost:8080/Tiny_Ecommerce/getCart'          
        }                 
        $http(data).then(function successCallback(response) {        
              console.log(response);
              if(response.status == 200){                
                $scope.cartId = response.data.cart_id;                
              }
              console.log($scope.cartId);              
            }, function errorCallback(response) {
              console.log(response);
              alert(response.data.msg)            
            }
          );    
      }     

      $scope.pay = function () {
        $scope.getCart();
        $scope.payment = true;
      }

      $scope.back = function () {
        $scope.payment = false;
      }

      $scope.checkout = function () {
        let data2 = {
          method: 'POST',        
          url: 'http://localhost:8080/Tiny_Ecommerce/addOrder',
          data: {"cartId" : $scope.cartId}          
        }   
        $http(data2).then(function successCallback(response) {        
          console.log(response);
          if(response.status == 201){
            $scope.userCart = [];                
            alert(response.data.msg);
            $("#modalCart .close").click();
          }                          
        }, function errorCallback(response) {
          console.log(response);
          alert(response.data.msg)            
        });
      }

     
    
  });