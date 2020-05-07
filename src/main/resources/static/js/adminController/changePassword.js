var app = angular.module("changePassword", []);
app.controller("password", function ($scope, $http, $window) {

    $scope.loadSessionData = function () {
        $scope.loggedUser = $window.sessionStorage.getItem('userName');
        window.alert(loggedUser);
        window.alert($window.sessionStorage.getItem('userName'));
    }

    $scope.changePass = function () {
        window.alert($window.sessionStorage.getItem('userId'));
        if ($scope.password1 == $scope.password2) {
            $http.post("/admin/changePassword", {
                id: $window.sessionStorage.getItem('userId'),
                password: $scope.password1
            }).then(function (response) {
                    if (response.status == 200)
                        window.alert("Password change Successful");
                    else
                        window.alert(response.status);
                }

            )
        } else
            window.alert("Password do not match");
    }

})