package cn.shiroblue.route;

/**
 * Description:
 * 匹配后的路径对象
 * ======================
 * by WhiteBlue
 * on 15/10/30
 */
public class RouteMatch {
    HttpMethod httpMethod;
    String matchPath;
    String path;
    Object target;

    public RouteMatch(HttpMethod httpMethod, String matchPath, String path, Object target) {
        this.httpMethod = httpMethod;
        this.matchPath = matchPath;
        this.path = path;
        this.target = target;
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

    public Object getTarget() {
        return target;
    }
}
