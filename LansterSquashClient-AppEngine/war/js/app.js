// Main app javascript
'use strict';

var MainCtrl = function($scope, $http) {
	hideAll();

	// Actions du menu
	$scope.onPlayerList = function() {
		hideAll();
		$scope.$broadcast("updateListOfPlayer", null);
		$scope.list_players_show = true;
	};
	$scope.onNewPlayer = function() {
		$("#playerModal").modal('show');
	};
	$scope.onMatchList = function() {
		alert("Match List");
	};
	$scope.onNewMatch = function() {
		$("#newMatchModal").modal('show');
	};
	$scope.onPlayMatch = function() {
		alert("Jouer un match");
	};
	$scope.onTournementList = function() {
		alert("Tournement List");
	};
	$scope.onNewTournement = function() {
		alert("New Tournement");
	};
	$scope.onHelp = function() {
		alert("Help");
	};

	// Hide all DIVs
	function hideAll() {
		$scope.list_players_show = false;
		$scope.match_show = true;
	}

};
