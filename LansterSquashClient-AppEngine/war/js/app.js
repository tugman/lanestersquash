// Main app javascript
'use strict';

// create the module and name it scotchApp
var routeApp = angular.module('routeApp', [ 'ngRoute' ]);

// configuration des routes
routeApp.config(function($routeProvider) {
	$routeProvider

	// route for the home page
	.when('/', {
		templateUrl : 'partials/home.html',
		controller : 'home'
	}).when('/players', {
		templateUrl : 'partials/players.html',
		controller : 'players'
	}).when('/trainings', {
		templateUrl : 'partials/trainings.html',
		controller : 'trainings'
	}).otherwise({
		redirectTo : '/'
	});

});

routeApp.controller('home', [ '$scope', function($scope, $http) {
	console.log("Home");
} ]);