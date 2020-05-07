var app = angular.module("examineeHome", []);
app.controller("homeOfExaminee", function ($scope, $http, $window) {

    $scope.loadSessionData = function () {
        $scope.loggedUser = $window.sessionStorage.getItem('userName');
        //window.alert($window.sessionStorage.getItem('userName'));
    }

    $scope.loadAvailableTests = function () {
        $http.get("/examinee/availableTests").then(function (response) {
            $scope.availableTests = response.data;
            console.log($scope.availableTests);
        })
    }



    $scope.approve = function () {
        $window.sessionStorage.setItem('selectedTestId', $scope.testToSet);
        window.location.href = "../../views/examinee/instruction.html";
    };

    $scope.checkAll = function () {
        if (!$scope.selectedAll) {
            $scope.selectedAll = true;
        } else {
            $scope.selectedAll = false;
        }
        angular.forEach($scope.personalDetails, function (personalDetail) {
            personalDetail.selected = $scope.selectedAll;
        });
    };



    $scope.testToSet;
    $scope.takeId = function (a) {
        $scope.testToSet = a.exdata.id;
        console.log($scope.testToSet);
    };

})