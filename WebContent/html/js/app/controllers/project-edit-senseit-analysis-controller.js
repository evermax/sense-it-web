angular.module('senseItWeb', null, null).controller('ProjectEditSenseItAnalysisCtrl', function ($scope, $state, ProjectDataService) {

    $scope.availableTransformations = {};
    for (var t in SiwSenseItSensorData.transformations) {
        if (SiwSenseItSensorData.transformations.hasOwnProperty(t)) {
            $scope.availableTransformations[t] = SiwSenseItSensorData.transformations[t].name;
        }
    }

    $scope.txForm = new SiwFormManager($scope.project.activity.profile, ['tx'], $scope.updateProfile, function () {
        $scope.txEdit.reset();
    });

    $scope.txEdit = {
        tx: function () {
            return $scope.txForm.values.tx;
        },
        open: function () {
            $scope.txForm.open();
            this.updateFormValue();
        },
        updateFormValue: function () {
            $scope.transformations.setTx(this.tx());
        },
        reset: function () {
            $scope.transformations.setTx($scope.project.activity.profile.tx);
        },
        deleteVariable: function (variable) {
            if (variable.editable) {
                var index = this.indexOf(variable);
                if (index >= 0) {
                    this.tx().splice(index, 1);
                    this.updateFormValue();
                }
            }
        },
        createVariable: function (type) {
            this.tx().push({
                id: this.newTxId(),
                name: '',
                type: type,
                inputs: []
            });
            this.updateFormValue();
        },
        indexOf: function (variable) {
            var tx = this.tx();
            for (var i = 0; i < tx.length; i++) {
                if (tx[i].id = variable.tx) {
                    return i;
                }
            }
            return -1;
        },
        newTxId: function () {
            var id = 1;
            var tx = this.tx();
            if (tx) {
                for (var i = 0; i < tx.length; i++) {
                    id = Math.max(id, 1 + parseInt(tx[i].id.substr(3)));
                }
            }
            return "tx:" + id;
        },
        editable: function (variable) {
            return $scope.txForm.isOpen() && variable.editable;
        },
        setInputVariable: function (tx, vId, index) {
            tx.inputs[index] = vId;
            this.updateFormValue();
        }
    }
})
;