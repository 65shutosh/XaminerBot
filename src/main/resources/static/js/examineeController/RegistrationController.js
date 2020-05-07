var app = angular.module("Registration", []);

app.controller('RegistraionController', function ($scope, $http) {

    $scope.passmatch = false;
    $scope.message = "";
    $scope.confirmPassword = function () {
        if ($scope.password == $scope.cpassword)
            $scope.passmatch = true;
        else
            $scope.passmatch = false;
    }

    // be used to decide for showing PostResults
    $scope.postDivAvailable = false;
    // be used for decide for showing GetResults
    // $scope.getDivAvailable = false;

    $scope.register = function () {
        //  window.alert("in register function");
        if ($scope.passmatch == true) {
            // post URL
            // prepare headers for posting

            // prepare data for post messages
            var data = {
                userName: $scope.user_name,
                name: $scope.name,
                userEmail: $scope.email,
                password: $scope.password,
                userTag: $scope.user_tag
            };

            // do posting
            if ($scope.user_name == null || $scope.user_name == "" ||
                $scope.name == null || $scope.name == "" ||
                $scope.email == null || $scope.email == "" ||
                $scope.password == null || $scope.password == "" ||
                $scope.user_tag == null || $scope.user_tag == "")
                window.alert("Please fill all required field")
            else {
                $http.post("/user/save", data).then(
                    function (response) {
                        $scope.postDivAvailable = true;
                        $scope.postCust = response.data;
                    },
                    function error(response) {
                        $scope.postResultMessage = "Error Status: " +
                            response.statusText;
                    });


                // reset data of form after posting
                $scope.name = '';
                $scope.email = '';
                $scope.user_tag = '';
                $scope.user_name = '';
                $scope.password = '';
                $scope.cpassword = '';
                $scope.postcode = '';

                if ($scope.postDivAvailable == true)
                    $scope.message = "Registration Successful";

            }
        } else
            window.alert("Passwords do not match");
    }
});