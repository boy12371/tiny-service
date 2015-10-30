package cn.shiroblue;

import cn.shiroblue.core.RouteMatcherFactory;
import cn.shiroblue.route.HttpMethod;

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
        RouteMatcherFactory.get().putRouteEntry(HttpMethod.GET, path, route);
    }

    public static void post(String path, Route route) {
        RouteMatcherFactory.get().putRouteEntry(HttpMethod.POST, path, route);
    }

    public static void put(String path, Route route) {
        RouteMatcherFactory.get().putRouteEntry(HttpMethod.PUT, path, route);
    }

    public static void patch(String path, Route route) {
        RouteMatcherFactory.get().putRouteEntry(HttpMethod.PATCH, path, route);
    }

    public static void delete(String path, Route route) {
        RouteMatcherFactory.get().putRouteEntry(HttpMethod.DELETE, path, route);
    }

    public static void before(String path, TinyFilter filter) {
        RouteMatcherFactory.get().putRouteEntry(HttpMethod.before, path, filter);
    }

    public static void after(String path, TinyFilter filter) {
        RouteMatcherFactory.get().putRouteEntry(HttpMethod.after, path, filter);
    }

}
