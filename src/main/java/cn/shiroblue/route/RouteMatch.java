package cn.shiroblue.route;

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
    private String url;
    private RouteType routeType;
    private Route target;

    /**
     * @param httpMethod request method
     * @param matchPath  route match path
     * @param url        request url
     * @param target     action target
     * @param routeType  the route type
     */
    public RouteMatch(HttpMethod httpMethod, String matchPath, String url, Route target, RouteType routeType) {
        this.httpMethod = httpMethod;
        this.matchPath = matchPath;
        this.url = url;
        this.target = target;
        this.routeType = routeType;
    }


    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public String getMatchPath() {
        return matchPath;
    }

    public String getUrl() {
        return url;
    }

    public RouteType getRouteType() {
        return routeType;
    }

    public Route getTarget() {
        return target;
    }
}
