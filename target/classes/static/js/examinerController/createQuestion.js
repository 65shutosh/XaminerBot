var app = angular.module("createQuestion", []);
app.controller("questionCreate", function ($scope, $http, $window) {

    $scope.loadSessionData = function () {
        $scope.loggedUser = $window.sessionStorage.getItem('userName');
        window.alert(loggedUser);
        window.alert($window.sessionStorage.getItem('userName'));
    }

})