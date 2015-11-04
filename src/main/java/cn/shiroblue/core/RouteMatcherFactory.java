package cn.shiroblue.core;

import cn.shiroblue.route.RouteMatcher;
import org.apache.log4j.Logger;

/**
 * Description:
 * 单例RouteMather创建
 * ======================
 * by WhiteBlue
 * on 15/10/30
 */
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
