'use strict';

var myCacheApp = angular.module('myCacheApp', ['ngRoute']);

myCacheApp.config(['$routeProvider', function($routeProvider){
    $routeProvider
        .when('/CacheInfo', {
            templateUrl: 'static/view/cache_manager_info.html'
        })
        .when('/CacheRefresh',{
            templateUrl: 'static/view/cache_manager_refresh.html'
        });
}]);