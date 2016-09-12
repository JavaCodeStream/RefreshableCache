'use strict';

myCacheApp.controller('cacheMgrControllerCacheInfo', ['cacheManagerService', function(cacheManagerService) {
    var self = this;
    self.headingTitle = "Cache Info";
    self.allCacheInfo = [];

    activate();

    function activate() {
        return cacheManagerService.fetchAllCacheInfo().then(function(data) {
            self.allCacheInfo = data;
            return self.allCacheInfo;
        });
    }
}]);

myCacheApp.controller('cacheMgrControllerCacheRefresh', ['$route','$templateCache','$interval','cacheManagerService',
    function($route, $templateCache, $interval, cacheManagerService) {
    var self = this;
    self.headingTitle = "Re-Load Cache";
    self.tglAutoRefresh = true;
    self.allCacheLoadingStatusInfo = [];

    function activate() {
        return cacheManagerService.fetchAllCacheLoadingStatusInfo().then(function(data) {
            self.allCacheLoadingStatusInfo = data;
            return self.allCacheLoadingStatusInfo;
        });
    }

    activate();

    self.reloadCache = function(cacheKey) {
        console.log("reloadCache clicked " + cacheKey);
        cacheManagerService.forceReloadCache(cacheKey);

        var currentPageTemplate = $route.current.templateUrl;
        $templateCache.remove(currentPageTemplate);
        $route.reload();
    }

    //self.headingTitle = "The Loading status cache is refreshed "+c+" time.";
    var refreshTimer = $interval(function() {
        activate();
    }, 5000);

}]);