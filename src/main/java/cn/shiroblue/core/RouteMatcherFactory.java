package cn.shiroblue.core;

import cn.shiroblue.route.RouteMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Description:
 * 单例RouteMather创建
 * ======================
 * by WhiteBlue
 * on 15/10/30
 */
public class RouteMatcherFactory {
    private static final Logger LOG = LoggerFactory.getLogger(RouteMatcherFactory.class);

    private static RouteMatcher routeMatcher = null;

    private RouteMatcherFactory() {
    }

    public static synchronized RouteMatcher get() {
        if (routeMatcher == null) {
            LOG.debug("create RouteMatcher");
            routeMatcher = new RouteMatcher();
        }
        return routeMatcher;
    }
}
