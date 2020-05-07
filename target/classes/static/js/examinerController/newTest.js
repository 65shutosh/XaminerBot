var app = angular.module("newTest", []);
app.controller("testNew", function ($scope, $http, $window) {



    ////////////////////////////////////////////
    ///////////////////////////  //On Page Load
    ////////////////////////////////////////////


    $scope.loadSessionData = function () {
        $scope.loggedUser = $window.sessionStorage.getItem('userName');
        $scope.examinerSubject = $window.sessionStorage.getItem('userSubject');
    }




    $scope.loadTopics = function () {
        console.log("begining the topic loading process");
        console.log($window.sessionStorage.getItem('userSubjectId'));
        $http.post("/examiner/subjectTopics", {
            id: $window.sessionStorage.getItem('userSubjectId')
        }).then(function (response) {
            $scope.subjectTopics = response.data;
            console.log("topic load response came");
        })

    }

    $scope.loadTopicsId = function () {
        console.log("begining the topicId loading process");
        console.log($window.sessionStorage.getItem('userSubjectId'));
        $http.post("/examiner/subjectTopicsId", {
            id: $window.sessionStorage.getItem('userSubjectId')
        }).then(function (response) {
            $scope.subjectTopicsId = response.data;
            console.log("topicId load response came");
        })

    }


    ///////////////////////////////////////////
    ////////////////////////////////////Manual
    ///////////////////////////////////////////



    /////////////////***************CheckBox For Topics *********************/
    $scope.selectedSubjectTopics = [];
    $scope.toggleSelection = function toggleSelection(t) {
        var idx = $scope.selectedSubjectTopics.indexOf(t);

        // Is currently selected
        if (idx > -1) {
            $scope.selectedSubjectTopics.splice(idx, 1);
        }

        // Is newly selected
        else {
            $scope.selectedSubjectTopics.push(t);
        }
    };

    ////////////////////////////////////////////////////////////////////////////

        /////////////////***************CheckBox For Question *********************/
    $scope.selectedQuestions = [];
    $scope.toggleQSelection = function toggleQSelection(t) {
        var idx = $scope.selectedQuestions.indexOf(t);

        // Is currently selected
        if (idx > -1) {
            $scope.selectedQuestions.splice(idx, 1);
        }

        // Is newly selected
        else {
            $scope.selectedQuestions.push(t);
        }
    };

    ////////////////////////////////////////////////////////////////////////////



    /////////////////***************creating list of Selected Topics Id's And Loading questions using function call *********************/
    $scope.selectedTopics = function () {
        $scope.selectedTopicId = [];
        for (j = 0; j < $scope.selectedSubjectTopics.length; j++) {
            for (i = 0; i < $scope.subjectTopicsId.length; i++) {
                if ($scope.subjectTopicsId[i][1] == $scope.selectedSubjectTopics[j]) {
                    $scope.selectedTopicId.push($scope.subjectTopicsId[i][0]);
                }
            }
        }
        console.log("selected Topic id");
        console.log($scope.selectedTopicId);
        $scope.selectedTopicsQuestion();
    }
    //////////////////////////////////////////////////////////////////////////

   
/////////////////***************Loading Selected Topic Questions *********************/
    $scope.selectedTopicsQuestion = function () {
       $http.post("/examiner/subjectTopicsQuestions",{id:$scope.selectedTopicId }).then(function(response){
       $scope.questionsOFSelectedTopics=response.data;
       console.log("In a function to load questions of selected topics");
       console.log(response.data);
      $scope.selectedTopicsWithName();
       })

    }
    //////////////////////////////////////////////////////////////////////////


    ///////////////////////////********** Selected topics with there name */
$scope.selectedTopicsWithName = function(){
    $scope.clientSelectedTopicNameAndId=[];
for (j = 0; j < $scope.selectedSubjectTopics.length; j++) {
            for (i = 0; i < $scope.subjectTopicsId.length; i++) {
                if ($scope.subjectTopicsId[i][1] == $scope.selectedSubjectTopics[j]) {
                    $scope.clientSelectedTopicNameAndId.push($scope.subjectTopicsId[i]);
                    
                }
            }
        }
        console.log("In function selectedTopicsWithName trying to get the topic name of selected topic Id's");
        console.log($scope.clientSelectedTopicNameAndId);
    }

    //////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////CREATING TEST
    //////////////////////////////////////////////////////////////////////////

    $scope.createTest = function(){
        if($scope.selectedQuestions<2 || $scope.noOfQuestions<2 ){
            window.alert("Minimum number of question to create a test is TWO");
        }else{
        if($scope.noOfQuestions==$scope.selectedQuestions.length){
        console.log("In create test");
        console.log($window.sessionStorage);
        console.log($scope.selectedQuestions);
        var subjectId =[$window.sessionStorage.getItem('userSubjectId')];
        var nq =[$scope.noOfQuestions];
        $http.post("/examiner/createTest",{id:$scope.selectedQuestions,topicId:subjectId,noOfQuestions:nq}).then(function(response){
        if(response.status==200)
        window.alert("craeting test is successful");    
        })
    }
    else if($scope.noOfQuestions>$scope.selectedQuestions.length){
       var difference = $scope.noOfQuestions-$scope.selectedQuestions.length;
       window.alert("Please select "+ difference +" more questions or change the number of questions to "+$scope.selectedQuestions.length);
    }
    else{
        var difference = $scope.selectedQuestions.length-$scope.noOfQuestions;
       window.alert("Please unselect "+ difference +" questions or change the number of questions to "+$scope.selectedQuestions.length);
    }
    }
    }
})