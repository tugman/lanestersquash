// List Player controler
'use strict';

var listPlayers = function($scope, $http) {

	// Update the list of players
	$scope.$on('updateListOfPlayer', function(event, args) {
		console.log("MAJ de la liste des joueurs");
		gapi.client.playerendpoint.listPlayer().execute(function(resp) {
			console.log("endpoint api sucessfully called");
			console.log(resp.items);
			$scope.players = resp.items;
			$scope.$digest();
		});
	});

	// On player selected
	$scope.modifyPlayer = function(player) {
		console.log("Player selected : " + player.name + " / "
				+ player.firstName);
		$("#playerModal").modal('show');
		
		// TODO
		$scope.$player=player;
		
		
	};

}
