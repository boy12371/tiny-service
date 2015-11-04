package cn.shiroblue;

import cn.shiroblue.core.RouteMatcherFactory;
import cn.shiroblue.route.HttpMethod;
import cn.shiroblue.route.Route;
import cn.shiroblue.route.RouteType;

/**
 * Description:
 * <p>
 * ======================
 * by WhiteBlue
 * on 15/10/30
 */
public class Action {

    private Action() {
    }

    public static void get(String path, Route route) {
        RouteMatcherFactory.get().putRouteEntry(HttpMethod.GET, path, route, RouteType.ACTION);
    }

    public static void post(String path, Route route) {
        RouteMatcherFactory.get().putRouteEntry(HttpMethod.POST, path, route, RouteType.ACTION);
    }

    public static void put(String path, Route route) {
        RouteMatcherFactory.get().putRouteEntry(HttpMethod.PUT, path, route, RouteType.ACTION);
    }

    public static void patch(String path, Route route) {
        RouteMatcherFactory.get().putRouteEntry(HttpMethod.PATCH, path, route, RouteType.ACTION);
    }

    public static void delete(String path, Route route) {
        RouteMatcherFactory.get().putRouteEntry(HttpMethod.DELETE, path, route, RouteType.ACTION);
    }

    public static void before(String path, Route route) {
        RouteMatcherFactory.get().putRouteEntry(HttpMethod.before, path, route, RouteType.FILTER);
    }

    public static void after(String path, Route route) {
        RouteMatcherFactory.get().putRouteEntry(HttpMethod.after, path, route, RouteType.FILTER);
    }

}
