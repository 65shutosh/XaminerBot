var app = angular.module("approval", []);
app.controller("PendingApprovalsController", function ($scope, $http) {



    $scope.loadExaminer = function () {
        $http.get("/admin/examinerDetailForApproval").then(function (response) {
            $scope.examinerData = response.data;
            console.log($scope.examinerData);
        })
    }



    $scope.approve = function () {
        window.alert("Are you sure you want to approve selected Examiner");
        $http.post("/admin/approveExaminer", {
            "id": $scope.idToApprove
        }).then(function (response) {
            console.log(response.data);
            console.log(response.status);
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



    $scope.idToApprove;
    $scope.takeId = function (a) {
        $scope.idToApprove = a.exdata[0];
        console.log($scope.idToApprove);
    };
});