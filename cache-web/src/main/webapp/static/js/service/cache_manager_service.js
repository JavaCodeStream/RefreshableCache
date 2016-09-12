'use strict';

myCacheApp.factory('cacheManagerService',['$http', '$q', function($http, $q){
    return {
        fetchAllCacheInfo : function() {
            return $http.get('http://localhost:8081/cache-web/cacheEntries/')
                .then(
                    function(response) {
                        return response.data;
                    },
                    function (errResponse) {
                        console.error('Error while fetching AllCacheInfo');
                        return $q.reject(errResponse);
                    }
                );
        },
        fetchAllCacheLoadingStatusInfo : function() {
            return $http.get('http://localhost:8081/cache-web/cacheEntries/loadingStatus/')
                .then(
                    function(response) {
                        return response.data;
                    },
                    function (errResponse) {
                        console.error('Error while fetching AllCacheLoadingStatusInfo');
                        return $q.reject(errResponse);
                    }
                );
        },
        forceReloadCache: function(cacheKey) {
            console.log("reloadCache service " + cacheKey);
            $http.get('http://localhost:8081/cache-web/reloadCache/'+cacheKey+'/')
                .then(
                    function(response) {
                        self.status = response.status;
                    },
                    function(response) {
                        self.status = response.status;
                    }
                );
        }
    };
}]);