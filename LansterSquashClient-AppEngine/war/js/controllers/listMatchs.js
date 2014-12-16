// List Match controler
'use strict';

var listMatchs = function($scope, $http) {

	// Update the list of matchs
	$scope.$on('updateListOfMatch', function(event, args) {
		console.log("MAJ de la liste des matchs");
		gapi.client.matchendpoint.listMatch().execute(function(resp) {
			console.log("endpoint api sucessfully called");
			console.log(resp.items);
			$scope.matchs = resp.items;
			$scope.$digest();
		});
	});

	// On player selected
	$scope.modifyMatch = function(match) {
		console.log("Match selected : " + match.matchname);

	};

}
