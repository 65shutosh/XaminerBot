var app = angular.module("instruction", []);
app.controller("instr", function ($scope, $http, $window) {

    $scope.loadSessionData = function () {
        console.log("loading session data");
        $scope.loggedUser = $window.sessionStorage.getItem('userName');
        $window.sessionStorage.setItem('ref',0);
        //window.alert($window.sessionStorage.getItem('userName'));
    }


    $scope.startTest = function () {
        window.location.href = "../../views/examinee/examForm.html";
    }
})