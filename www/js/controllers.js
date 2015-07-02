angular.module('starter.controllers', [])

.controller('AppCtrl', function($scope, $ionicModal, $timeout, $http) {
  // Form data for the login modal
  $scope.loginData = {};

  // Create the login modal that we will use later
  $ionicModal.fromTemplateUrl('templates/login.html', {
    scope: $scope
  }).then(function(modal) {
    $scope.modal = modal;
  });

  // Triggered in the login modal to close it
  $scope.closeLogin = function() {
    $scope.modal.hide();
  };

  // Open the login modal
  $scope.login = function() {
    $scope.modal.show();


   $http.get('https://cors-test.appspot.com/test').then(function(resp) {
    console.log('Success', resp);
    // For JSON responses, resp.data contains the result
    }, function(err) {
      console.error('ERR', err);
    // err.status will contain the status code
    });

  };

  // Perform the login action when the user submits the login form
  $scope.doLogin = function() {
    console.log('Doing login', $scope.loginData);


    // Simulate a login delay. Remove this and replace with your login
    // code if using a login system
    $timeout(function() {
      $scope.closeLogin();
    }, 1000);
  };
})

.controller('EventsCtrl', function($scope,Events) {

  Events.getAll().success(function(data){
        console.log(data.results);
        $scope.events=data.results;
    });


/*
$$hashKey: "object:19"
content: "abababab"
createdAt: "2015-06-11T08:49:32.733Z"
objectId: "BLJTVgmTJt"
updatedAt: "2015-06-11T08:49:32.733Z"
*/
/*
  $scope.events = [
    { title: 'Reggae', id: 1 },
    { title: 'Chill', id: 2 },
    { title: 'Dubstep', id: 3 },
    { title: 'Indie', id: 4 },
    { title: 'Rap', id: 5 },
    { title: 'Cowbell', id: 6 }
  ];

  $scope.doRefresh = function() {
   $http.get('https://cors-test.appspot.com/test').then(function(resp) {
    console.log('Success', resp);
    // For JSON responses, resp.data contains the result
    }, function(err) {
      console.error('ERR', err);
    // err.status will contain the status code
    });
  };

*/  
})

.controller('EventCtrl', function($scope, $stateParams, $http) {


})

.controller('PostsCtrl', function($scope, $state, Posts) {

  Posts.getAll().success(function(data){
        console.log("PostsCtrl");
        console.log(data.results);
        $scope.posts=data.results;
    });

  $scope.createPost = function() {
    console.log("createPost");
    $state.go('createpost');
  };

})

.controller('PostCtrl', function($scope, $state, $stateParams, Posts) {

  $scope.post={};

  $scope.onCreatePost = function() {
    console.log("onCreatePost" + $scope.post.title);

    Posts.create({content:$scope.post.title}).success(function(data){
      $state.go('app.posts');
    });

  };

  $scope.onCancelPost = function() {
    console.log("onCancelPost");
    $state.go('app.posts');
  };

});
