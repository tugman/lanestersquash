// List Player controler
'use strict';

// Contrôleur de la page players
routeApp.controller('players', [
		'$scope',
		function($scope, $http) {
			// Refresh player liste
			$scope.refreshPlayerList = function() {
				console.log("MAJ de la liste des joueurs");
				gapi.client.playerendpoint.listPlayer().execute(function(resp) {
					console.log("listPlayer api sucessfully called");
					console.log(resp.items);
					$scope.players = resp.items;
					$scope.$digest();
				});
			};
			
			// On player selected
			$scope.modifyPlayer = function(player) {
				console.log("Player selected : " + player.name + " / "
						+ player.firstName);
				$("#playerModal").modal('show');
				$scope.playerSelected = player;
				$scope.editplayerName = player.name;
				$scope.editplayerFirstName = player.firstName;
			};

			// On new Player
			$scope.newPlayer = function() {
				console.log("New Player");
				$("#playerModal").modal('show');
				$scope.editplayerName = "";
				$scope.editplayerFirstName = "";
				$scope.playerSelected = null;

			}

			// On save Player
			$scope.savePlayer = function() {
				console.log("Save Player");
				console.log($scope.playerSelected);
				// Check if new player
				if ($scope.playerSelected == null) {
					gapi.client.playerendpoint.insertPlayer({
						name : $scope.editplayerName,
						firstName : $scope.editplayerFirstName
					}).execute(function(resp) {
						console.log("insertPlayer api sucessfully called");
						console.log(resp);
					});
				} else {
					 //Build the Request Object
					var myPlayer = {};
					myPlayer.id = $scope.playerSelected.id;
					myPlayer.name = $scope.editplayerName;
					myPlayer.firstName = $scope.editplayerFirstName; 
					gapi.client.playerendpoint.updatePlayer(myPlayer).execute(function(resp) {
						console.log("update endpoint api sucessfully called");
						console.log(myPlayer);					
						console.log(resp);
						// Refresh list
						gapi.client.playerendpoint.listPlayer().execute(function(resp) {
							console.log("listPlayer api sucessfully called");
							console.log(resp.items);
							$scope.players = resp.items;
							$scope.$digest();
						});
					});

				}
				$("#playerModal").modal('hide');
			}

			// On delete Player
			$scope.deletePlayer = function() {
				console.log("Delete Player");
				console.log($scope.playerSelected);
				gapi.client.playerendpoint.removePlayer(
						$scope.playerSelected).execute(function(resp) {
					console.log("removePlayer api sucessfully called");
					console.log(resp);
					// Refresh list
					gapi.client.playerendpoint.listPlayer().execute(function(resp) {
						console.log("listPlayer api sucessfully called");
						console.log(resp.items);
						$scope.players = resp.items;
						$scope.$digest();
					});
				});
				$("#playerModal").modal('hide');
			}

		} ]);
			    
