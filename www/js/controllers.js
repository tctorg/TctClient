angular.module('starter.controllers', [])

.controller('AppCtrl', function($scope, $state, $ionicModal, $timeout, $http, User) {
  // Form data for the login modal
  $scope.loginData = {};


  // Create the login modal that we will use later
  $ionicModal.fromTemplateUrl('templates/logout.html', {
    scope: $scope
  }).then(function(modal) {
    $scope.logoutModal = modal;
  });

  $scope.closeLogout = function() {
    $scope.logoutModal.hide();
  };

  $scope.logout = function() {
    $scope.logoutModal.show();
  };

  $scope.doLogout = function() {
    $scope.logoutModal.hide();
    $state.go("landing");
  };

  $scope.login = function() {
    $state.go("login");
  };

  $scope.signin = function(){
    $state.go("signin");
  };

  // Perform the login action when the user submits the login form
  $scope.doLogin = function() {
    console.log('Doing login', $scope.loginData);

    User.getAll().success(function(data){
        console.log(data.results);
    });

    $state.go("app.events");
  };

  $scope.doSignin = function(){
    $state.go("app.events");
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

.controller('PostsCtrl', function($scope, $state, $ionicModal, Posts) {

  Posts.getAll().success(function(data){
        console.log("PostsCtrl");
        console.log(data.results);
        $scope.posts=data.results;
    });

  // Create comment modal that we will use later
  $ionicModal.fromTemplateUrl('templates/comment-create.html', {
    scope: $scope
  }).then(function(modal) {
    $scope.modalcomment = modal;
  });


  $scope.createPost = function() {
    console.log("createPost");
    $state.go('createpost');
  };

  $scope.createComment = function(post) {
    console.log("createComment");
    $scope.modalcomment.show();
  };

  $scope.createLike = function(post) {
    console.log("createLike");
    Posts.like(post.objectId);
  };

  $scope.gotoPostDetail = function(post) {
    console.log("gotoPostDetail");
    $state.go('#/app/posts/' + post.objectId);
  };
  

})

.controller('PostCtrl', function($scope, $state, $stateParams, Posts) {

  $scope.post={};

  $scope.doCreatePost = function() {
    console.log("onCreatePost" + $scope.post.subject);

    Posts.create({subject: $scope.post.subject, content:$scope.post.content}).success(function(data){
      $state.go('app.posts');
    });

  };

  $scope.doCancelPost = function() {
    console.log("onCancelPost");
    $state.go('app.posts');
  };

});
