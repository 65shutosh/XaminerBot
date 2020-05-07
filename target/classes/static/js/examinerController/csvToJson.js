var app = angular.module("myngCsv", []);

app.controller("ngcsvCtrl", [
    "$scope",
    "$http",
    "readFileData",
    function ($scope, $http, readFileData) {
        $scope.fileDataObj = {};

        $scope.uploadFile = function () {
            if ($scope.fileContent) {
                $scope.fileDataObj = readFileData.processData($scope.fileContent);
                $scope.fileData = JSON.stringify($scope.fileDataObj);

            }



        }
        $scope.jsonUpload = function () {
            window.alert("Trying for server now ");
            $http.post("/examiner/topicBulkUpload", $scope.fileData).then(
                function (response) {
                    $scope.message = response.data;
                    if (response.status == 200)
                       {
                             window.alert("response came");
                             console.log(response.data);

                       }

                   else
                   window.alert("something wrong");
                }

            )
        }
    }

]);

app.directive('fileReaderDirective', function () {
    return {
        restrict: "A",
        scope: {
            fileReaderDirective: "=",
        },
        link: function (scope, element) {
            $(element).on('change', function (changeEvent) {
                var files = changeEvent.target.files;
                if (files.length) {
                    var r = new FileReader();
                    r.onload = function (e) {
                        var contents = e.target.result;
                        scope.$apply(function () {
                            scope.fileReaderDirective = contents;
                        });
                    };
                    r.readAsText(files[0]);
                }
            });
        }
    };
});

app.factory('readFileData', function () {
    return {
        processData: function (csv_data) {
            var record = csv_data.split(/\r\n|\n/);
            var headers = record[0].split(',');
            var lines = [];
            var json = {};

            for (var i = 0; i < record.length; i++) {
                var data = record[i].split(',');
                if (data.length == headers.length) {
                    var tarr = [];
                    for (var j = 0; j < headers.length; j++) {
                        tarr.push(data[j]);
                    }
                    lines.push(tarr);
                }
            }

            for (var k = 0; k < lines.length; ++k) {
                json[k] = lines[k];
            }
            return json;
        }
    };
});