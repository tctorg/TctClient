angular.module('starter.controllers', [])

.controller('AppCtrl', function($scope, $state, $ionicModal, $timeout, $http, User) {
  // Form data for the login modal
  $scope.loginData = {};
  $scope.signinData = {};

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

    User.login({name: $scope.loginData.username, password:$scope.loginData.password}).success(function(data){
      $state.go('app.events',$state.$current.params, {reload: true});
    });

  };

  $scope.doSignin = function(){
    console.log('Doing Signin', $scope.signinData);

    User.signin({name: $scope.signinData.username, password:$scope.signinData.password}).success(function(data){
      $state.go('app.events',$state.$current.params, {reload: true});
    });

  };

})

.controller('EventsCtrl', function($scope,Events) {

  Events.getAll().success(function(data){
        console.log(data.results);
        $scope.events=data.results;
    });

})

.controller('EventCtrl', function($scope, $stateParams, $http, Events) {

  $scope.event = {};

  Events.get($stateParams.eventId).success(function(data){
    $scope.event = data;
  });

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
      $state.go('app.posts',$state.$current.params, {reload: true});
    });

  };

  $scope.doCancelPost = function() {
    console.log("onCancelPost");
    $state.go('app.posts');
  };

});
