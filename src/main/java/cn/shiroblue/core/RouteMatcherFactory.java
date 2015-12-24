package cn.shiroblue.core;

import cn.shiroblue.modules.RouteMatcher;


public class RouteMatcherFactory {

    private static RouteMatcher routeMatcher = null;


    private RouteMatcherFactory() {
    }

    public static synchronized RouteMatcher get() {
        if (routeMatcher == null) {
            routeMatcher = new RouteMatcher();
        }
        return routeMatcher;
    }
}
