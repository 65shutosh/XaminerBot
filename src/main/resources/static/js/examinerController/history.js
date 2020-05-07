var app = angular.module("history", []);
app.controller("testHistory", function ($scope, $http, $window) {

    $scope.getUserSubject = function () {
        console.log($window.sessionStorage.getItem('userId'));
        $http.post("/examiner/subject", {
            id: $window.sessionStorage.getItem('userId')
        }).then(function (response) {
            $scope.examinerSubject = response.data[0][1];
            $scope.examinerSubjectId = response.data[0][0];
            $window.sessionStorage.setItem('userSubject', $scope.examinerSubject);
            $window.sessionStorage.setItem('userSubjectId', $scope.examinerSubjectId);
            console.log($scope.examinerSubjectId);
        })
    }

    $scope.loadSessionData = function () {
        $scope.loggedUser = $window.sessionStorage.getItem('userName');
        $scope.subject = $window.sessionStorage.getItem('userSubject');
        //window.alert($window.sessionStorage.getItem('userName'));
    }

    $scope.loadAvailableTests = function () {
        $http.post("/examiner/availableTestsToExaminee", {
            subjectId: $window.sessionStorage.getItem('userSubjectId')
        }).then(function (response) {
            $scope.availableTestsTE = response.data;
            console.log($scope.availableTests);
        })
    }



    $scope.approve = function () {
        window.alert("Are you sure you want to disable this test for examinee");
        $http.post("/examiner/makeTheTestUnAvailable", {
            "id": $scope.testToSet
        }).then(function (response) {
            if (response.status == 200)
                window.alert("Disabled for examinee");
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



    $scope.testToSet;
    $scope.takeId = function (a) {
        $scope.testToSet = a.exdata[0];
        console.log($scope.testToSet);
    };


})