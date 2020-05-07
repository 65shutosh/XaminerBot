var app = angular.module("app2", []);
app.controller("postcontroller2", function ($scope, $http, $window) {

	// $scope.getUserSubject = function () {
	// 	window.alert("in get subject");
	// 	console.log($scope.loggedUserId);
	// 	$http.post("/examiner/subject", {id: $scope.loggedUserId}).then(function (response) {
	// 		$scope.examinerSubject = response.data;
	// 		window.alert(response.data);
	// 	})
	// }

	$scope.login = function () {
		// window.alert("in signin");

		// Data to post
		var data = {
			userId: $scope.userId,
			password: $scope.password
		};
		// window.alert("url and data is set");
		if ($scope.userId == null || $scope.userId == "" ||
			$scope.password == null || $scope.password == "")
			window.alert("Please fill all required field")
		else {
			$http.post("/user/login", data).then(function (response) {
				if ((response.data != "")) {
					$window.sessionStorage.setItem('userId', response.data.id);
					$window.sessionStorage.setItem('userName', response.data.name);
					// window.alert($window.sessionStorage.getItem('userId'));
					// window.alert($window.sessionStorage.getItem('userName'));
					if ((response.data.userTag) == "admin" && (response.data.approved) == "admin") {
						window.location = "views/admin/admin.html";
					} else if ((response.data.userTag) == "examiner" && (response.data.approved) == "yes") {
						// $scope.loggedUserId = $window.sessionStorage.getItem('userId');
						// $scope.getUserSubject();
						// console.log($scope.examinerSubject);
						// window.alert("trying");
						window.location = "views/examiner/examiner.html";
					} else if ((response.data.userTag) == "examinee" && (response.data.approved) == "yes") {
						window.location = "views/examinee/examinee.html";
					} else
						window.alert("You are not yet approved");
				} else
					window.alert("Please check your login detail");
			})
		}
	}

	$scope.register = function () {
		window.location = "views/registration.html";
	}
})