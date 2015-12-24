package cn.shiroblue.modules;


import cn.shiroblue.route.HttpMethod;
import cn.shiroblue.route.RouteEntry;
import cn.shiroblue.route.RouteMatch;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RouteMatcher {
    private Set<RouteEntry> routes;

    public RouteMatcher() {
        this.routes = new HashSet<>();
    }

    /**
     * clear the route map
     */
    public void clearRoutes() {
        this.routes.clear();
    }

    /**
     * find the match objects
     *
     * @param httpMethod method
     * @param uri        uri
     * @return List
     */
    public List<RouteMatch> findMatchRote(HttpMethod httpMethod, String uri) {
        List<RouteMatch> matchSet = new ArrayList<>();
        for (RouteEntry entry : this.routes) {
            if (entry.matches(httpMethod, uri)) {
                RouteMatch routeMatch = new RouteMatch(entry.httpMethod, entry.matchPath, uri, entry.route, entry.render);
                matchSet.add(routeMatch);
            }
        }
        return matchSet;
    }

    /**
     * put a new Route in map
     *
     * @param httpMethod method
     * @param uri        uri
     * @param target     target
     */
    public void putRouteEntry(HttpMethod httpMethod, String uri, Object target) {
        RouteEntry routeEntry = new RouteEntry(httpMethod, uri, target);
        this.routes.add(routeEntry);
    }


    /**
     * put a new Route in map(with render)
     *
     * @param httpMethod method
     * @param url        uri
     * @param target     target
     * @param render     to render this result
     */
    public void putRouteEntry(HttpMethod httpMethod, String url, Object target, Render render) {
        RouteEntry routeEntry = new RouteEntry(httpMethod, url, target, render);
        this.routes.add(routeEntry);
    }
}
