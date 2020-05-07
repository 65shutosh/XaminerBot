var app = angular.module("manageQuestions", []);
app.controller("questionManage", function ($scope, $http, $window) {

    $scope.loadSessionData = function () {
        $scope.loggedUser = $window.sessionStorage.getItem('userName');
        window.alert(loggedUser);
        window.alert($window.sessionStorage.getItem('userName'));
    }

})