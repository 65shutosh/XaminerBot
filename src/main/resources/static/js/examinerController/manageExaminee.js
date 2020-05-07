var app = angular.module("examineeProfile", []);
app.controller("profileExaminee", function ($scope, $http, $window) {

    $scope.loadSessionData = function () {
        $scope.loggedUser = $window.sessionStorage.getItem('userName');
        window.alert(loggedUser);
        window.alert($window.sessionStorage.getItem('userName'));
    }

    
    $scope.loadExaminee = function () {
        $http.get("/examiner/examinerDetail").then(function (response) {
            console.log("response came");
            console.log(response.data);
            $scope.examineeData = response.data;
            console.log($scope.examinerData);
        })
    }



    $scope.remove = function () {
        window.alert("Are you sure you want to remove selected Examinee");
        $http.post("/examiner/deleteExaminee", {
            "id": $scope.idToRemove
        }).then(function (response) {
            console.log(response.data);
            window.location.reload();
        })
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



    $scope.idToRemove;
    $scope.takeId = function (a) {
        $scope.idToRemove = a.exdata[0];
        console.log($scope.idToRemove);
    };

})