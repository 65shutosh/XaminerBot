var app = angular.module("instruction", []);
app.controller("instr", function ($scope, $http, $window) {

    $scope.loadSessionData = function () {
        console.log("loading session data");
        $scope.loggedUser = $window.sessionStorage.getItem('userName');
        $scope.loggedUserId = $window.sessionStorage.getItem('userId');
        $scope.testId = $window.sessionStorage.getItem('selectedTestId');
        //window.alert($window.sessionStorage.getItem('userName'));
    }

    $scope.loadTestDetails = function () {
        console.log("Question loading process bigins");
        $http.post("/examinee/testDetails", {
            id: $scope.testId
        }).then(function (response) {
            if (response.status == 200) {
                $window.sessionStorage.setItem('test', response.data);
                $scope.questionObject = response.data.testDetails;
                console.log(response.data);
                console.log("testDetail uploaded sussesfully");
                $scope.creatingQuestionArray();
                $scope.loadQuestionPaper();
            }
        })
    }

    $scope.creatingQuestionArray = function () {
        var p = $scope.questionObject;
        $scope.questionList = [];
        for (var key in p) {
            if (p.hasOwnProperty(key)) {
                $scope.questionList.push(p[key].id);
            }
        }
        console.log("creating question list which is");
        console.log($scope.questionList);
    }

    $scope.loadQuestionPaper = function () {
        console.log("Question loading process bigins");
        $http.post("/examinee/loadQuestionP", {
            id: $scope.questionList
        }).then(function (response) {
            if (response.status == 200) {
                $scope.questionWithAnswer = response.data;
                console.log($scope.questionWithAnswer);
                console.log(response.data);
                console.log("question uploaded sussesfully");
                $scope.first();
            }
        })
    }


    $scope.first = function () {
        $scope.AnswerText = [];
        $scope.index = $window.sessionStorage.getItem('ref');
        $scope.questionTest = $scope.questionWithAnswer[$scope.index].questionText;
        $scope.AnswerText.push($scope.questionWithAnswer[$scope.index].answers);
        $scope.len = $scope.questionWithAnswer.length;
    }


    $scope.submit = function () {
        window.alert("Thank You For writing , You will get your result on email ");
    }

    $scope.pre = function () {

        if ($scope.questionWithAnswer[0] == $scope.questionTest) {

            $scope.index = $scope.len - 1;

        } else {
            $scope.index = $scope.index - 1;
        }
        $window.sessionStorage.getItem('ref', $scope.index);
        window.location.reload();
    }

    $scope.next = function () {

        var len = $scope.questionWithAnswer.length;
        if ($scope.questionWithAnswer[len - 1] == $scope.questionTest) {
            $scope.index = 0;
        } else {
            $scope.index = $scope.index + 1;
        }
        $window.sessionStorage.getItem('ref', $scope.index);
        window.location.reload();

    }


})