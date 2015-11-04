package cn.shiroblue.route;

import cn.shiroblue.Route;
import cn.shiroblue.RouteType;

/**
 * Description:
 * 匹配后的路径对象
 * ======================
 * by WhiteBlue
 * on 15/10/30
 */
public class RouteMatch {
    private HttpMethod httpMethod;
    private String matchPath;
    private String path;
    private RouteType routeType;
    private Route target;

    public RouteMatch(HttpMethod httpMethod, String matchPath, String path, Route target, RouteType routeType) {
        this.httpMethod = httpMethod;
        this.matchPath = matchPath;
        this.path = path;
        this.target = target;
        this.routeType = routeType;
    }


    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public String getMatchPath() {
        return matchPath;
    }

    public String getPath() {
        return path;
    }

    public RouteType getRouteType() {
        return routeType;
    }

    public Route getTarget() {
        return target;
    }
}
