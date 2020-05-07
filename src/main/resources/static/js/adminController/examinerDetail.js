var app = angular.module("myapp", []);
app.controller("ListController", function ($scope, $http) {



    $scope.loadExaminer = function () {
        $http.get("/admin/examinerDetail").then(function (response) {
            console.log("response came");
            console.log(response.data);
            $scope.examinerData = response.data;
            console.log($scope.examinerData);
        })
    }



    $scope.remove = function () {
        window.alert("Are you sure you want to remove selected Examiner");
        $http.post("/admin/deleteExaminer", {
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
});