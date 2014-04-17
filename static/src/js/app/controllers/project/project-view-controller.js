angular.module('senseItWeb', null, null).controller('ProjectViewCtrl', function ($scope, $state, ProjectService) {

    ProjectService.watchProject($scope, $state.params['projectId']);

    $scope.joinProject = function () {
        $scope.projectWatcher.joinProject().then(function () {
            var view;
            switch ($scope.projectData.project.type) {
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
        $scope.projectWatcher.leaveProject();
    };

    $scope.showProjectViewMenu = function() {
        return $scope.status.logged && $scope.projectData.project.open && $scope.projectData.access.member;
    };

    $scope.projectViewMenuIsActive = function(state) {
        return $state.current.name.indexOf('project.view.' + state) === 0;
    };


    $scope.templates = {
        home: null
    };

    $scope.projectTemplate = function () {
        if ($scope.projectData.project) {
            switch ($scope.projectData.project.type) {
                case 'senseit':
                    return 'partials/project/view/home/project-view-senseit.html';
                case 'challenge':
                    return 'partials/project/view/home/project-view-challenge.html';
            }
        }
        return null;
    };

    $scope.commentThread = {
        type: 'project',
        id: $state.params['projectId']
    };

});
