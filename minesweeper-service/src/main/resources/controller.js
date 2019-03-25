angular.module('Minesweeper', [])
    .controller('Game', function($scope, $http) {

        var size;
        var url = "http://localhost:8081/minesweeper-service";
        $scope.gameStarted = false;
        $scope.cellOpacity = 0.3;
        $scope.numberOfRows = 8;

        $scope.startGame = function (rowSize, numOfMines) {

            if(rowSize === undefined) rowSize = 8;
            if(numOfMines === undefined) numOfMines = 10;

            $http.post(url + "/start", "{\"rowSize\": " + rowSize + ", \"numOfMines\": " + numOfMines + "}").then(function (response) {
                size = response.data.size;
                $scope.numberOfRows = response.data.numberOfRows;
                $scope.cellOpacity = 1;
                $scope.gameStarted = true;

                $scope.cellColor = new Array(size);
                $scope.neighborMines = new Array(size);
                for(i=0; i < size; i++) {
                    $scope.cellColor[i] = "lightgrey";
                    $scope.neighborMines[i] = "";
                }
            });
        };

        $scope.clickCell = function (cellNum) {
            if($scope.gameStarted && $scope.cellColor[cellNum] !== "tomato") {
                $http.post(url + "/click", "{\"cellNum\": \"" + cellNum + "\"}").then(function (response) {
                    if (response.status === 200) {
                        $scope.response = response.data;
                        displayClickAndNeighborMines(cellNum);
                    }
                    if(response.data.alive === false || response.data.winner){
                        $scope.gameStarted = false;
                        $scope.cellOpacity = 0.3;
                        showMines();
                    }
                });
            }
        };

        $scope.flagCell = function (cellNum) {
            if($scope.gameStarted) {
                $http.post(url + "/flagChange", "{\"cellNum\": \"" + cellNum + "\"}").then(function (response) {
                    if (response.status === 200) {
                        $scope.response = response.data;
                        changeFlaggedCellColors(cellNum);
                    }
                    if(response.data.alive === false || response.data.winner) {
                        $scope.gameStarted = false;
                        $scope.cellOpacity = 0.3;
                        showMines();
                    }
                });
            }
        };

        function displayClickAndNeighborMines(cellNum) {
            for(i=0; i < $scope.response.clickedCells.length; i++) {
                var currentClickedCell = $scope.response.clickedCells[i];
                $scope.cellColor[currentClickedCell] = "darkgrey";
                if ($scope.response.numOfNeighborMines[i] === 0) {
                    $scope.neighborMines[currentClickedCell] = "";
                } else {
                    $scope.neighborMines[currentClickedCell] = $scope.response.numOfNeighborMines[i].toString();
                }
            }
        }

        function showMines() {
            for(i=0; i < size; i++) {
                if($scope.response.mines.includes(i)){
                    $scope.cellColor[i] = "firebrick";
                }
            }
        }

        function changeFlaggedCellColors(cellNum) {
            if ($scope.cellColor[cellNum] === "tomato") {
                $scope.cellColor[cellNum] = "lightgrey";
            } else if ($scope.cellColor[cellNum] === "lightgrey") {
                $scope.cellColor[cellNum] = "tomato";
            }
        }


    })
    .directive('ngRightClick', function($parse) {
        return function(scope, element, attrs) {
            var fn = $parse(attrs.ngRightClick);
            element.bind('contextmenu', function(event) {
                scope.$apply(function() {
                    event.preventDefault();
                    fn(scope, {$event:event});
               });
            });
       };
    });

