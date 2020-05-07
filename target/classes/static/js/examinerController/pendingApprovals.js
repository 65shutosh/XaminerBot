var app = angular.module("PendingApproval", []);
app.controller("approvalPending", function ($scope, $http, $window) {

    $scope.loadSessionData = function () {
        $scope.loggedUser = $window.sessionStorage.getItem('userName');
        window.alert(loggedUser);
        window.alert($window.sessionStorage.getItem('userName'));
    }

  $scope.loadExaminee = function () {
        $http.get("/examiner/examineeDetailForApproval").then(function (response) {
            $scope.examineeData = response.data;
            console.log($scope.examinerData);
        })
    }



    $scope.approve = function () {
        window.alert("Are you sure you want to approve selected Examinee");
        $http.post("/examiner/approveExaminee", {
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

})