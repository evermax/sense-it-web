angular.module('senseItWeb', null, null).controller('ProjectViewCtrl', function ($scope, $state, ProjectService) {
    ProjectService.registerGet($scope, $state.params['projectId']);


    $scope.joinProject = function () {
        ProjectService.joinProject($state.params['projectId']).then(function () {
            var view;
            switch ($scope.project.type) {
                case 'challenge':
                    view = 'project-view.challenge';
                    break;
                case 'senseit':
                default:
                    view = 'project-view.data-list';
                    break;
            }
            $state.go(view);
        });
    };

    $scope.leaveProject = function () {
        ProjectService.leaveProject($state.params['projectId']);
    };

    $scope.showDataMenu = function () {
        return $scope.project.type === 'senseit' && $scope.project.open && $scope.access.member;
    };


    $scope.templates = {
        home: null
    };

    $scope.projectTemplate = function () {
        switch ($scope.project.type) {
            case 'senseit':
                return 'partials/project-view-senseit.html';
            case 'challenge':
                return 'partials/project-view-challenge.html';
        }
        return null;
    };

    $scope.commentThread = {
        type: 'project',
        id: $state.params['projectId']
    };

});

