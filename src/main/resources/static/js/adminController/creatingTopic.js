var app = angular.module("admin", []);
app.controller("creatNewTopic", function ($scope, $http, $window) {


    $scope.inputs = [];
    $scope.addfield = function () {
        $scope.inputs.push({})
    };

    // $scope.loadTopics = function () {
    //     $http.get("/admin/topics").then(function (response) {
    //         var keysOfData = Object.keys(response.data);
    //         console.log(response.data);
    //         console.log(keysOfData);
    //     })
    // }


    // var data = {
    //     subjectName: $scope.name,
    //     topicName: $scope.inputs
    // };
    //var tp = { topicName:$scope.name };

    $scope.newTopicEntry = function () {
        window.alert($window.sessionStorage.getItem('userId'));
        $http.post("/admin/newSubject", {
            topicName: $scope.name
        }).then(function (response) {
            console.log(response.data);


        })
    }

})