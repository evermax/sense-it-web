angular.module('senseItWeb', null, null).controller('MainCtrl', function ($scope, $translate, OpenIdService, RestService) {
    OpenIdService.registerWatcher($scope);

    RestService.get('api/text').then(function(data) {
        $scope.txt = data;
    });

    $translate('ADMIN.YES').then(function(translation){
        $scope.yes = translation;
    });
    $translate('ADMIN.NO').then(function(translation){
        $scope.no = translation;
    });

    $scope.changeLanguage = function() {
        var newLang = $translate.use() === "en" ? "fr" : "en";
         setCookie("lang", newLang, 30);
        $translate.use(newLang);
    }
    function setCookie(cname, cvalue, exdays) {
        var d = new Date();
        d.setTime(d.getTime() + (exdays*24*60*60*1000));
        var expires = "expires="+d.toUTCString();
        document.cookie = cname + "=" + cvalue + "; " + expires;
    }
});
