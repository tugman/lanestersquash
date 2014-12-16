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
		hideAll();
		$scope.$broadcast("updateListOfMatch", null);
		$scope.list_matchs_show = true;
	};
	$scope.onNewMatch = function() {
		$("#newMatchModal").modal('show');
	};
	$scope.onPlayMatchArbitration = function() {
		hideAll();
		$scope.match_arbitration_show = true;
	};
	$scope.onWarmUp = function() {
		hideAll();
		$scope.warmup_show = true;
	};
	$scope.onPlayMatchFollow = function() {
		alert("Suivre un match");
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
		$scope.match_arbitration_show = false;
		$scope.list_matchs_show = false;
		$scope.warmup_show = false;
	}

};
