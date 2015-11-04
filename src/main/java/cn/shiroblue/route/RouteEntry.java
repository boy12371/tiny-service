package cn.shiroblue.route;

import cn.shiroblue.utils.UrlUtils;

import java.util.List;

/**
 * Description:
 * <p>
 * ======================
 * by WhiteBlue
 * on 15/10/29
 */
public class RouteEntry {
    HttpMethod httpMethod;

    //匹配路径
    String matchPath;

    //目标Route对象
    Route route;


    RouteType routeType;

    /**
     * 构造函数(执行格式化)
     *
     * @param httpMethod httpMethod
     * @param matchUrl   未处理url
     * @param route      Route
     */
    public RouteEntry(HttpMethod httpMethod, String matchUrl, Route route, RouteType routeType) {
        this.httpMethod = httpMethod;
        //路径格式化
        this.matchPath = UrlUtils.pathFormat(matchUrl);
        this.route = route;
        this.routeType = routeType;
    }

    /**
     * 路径匹配
     *
     * @param requestMethod request httpMethod
     * @param path          clean url
     * @return boolean
     */
    boolean matches(HttpMethod requestMethod, String path) {
        boolean match = matchPath(path);

        if (match) {
            if (!((this.httpMethod == HttpMethod.before || this.httpMethod == HttpMethod.after) || (this.httpMethod == requestMethod))) {
                match = false;
            }
        }

        return match;
    }

    /**
     * 路径组件匹配
     *
     * @param url 处理过的url(去掉slash)
     * @return boolean
     */
    private boolean matchPath(String url) {

        //完全一致则返回
        if (this.matchPath.equals(url)) {
            return true;
        }

        //路径分割
        List<String> thisPathList = UrlUtils.convertRouteToList(this.matchPath);
        List<String> pathList = UrlUtils.convertRouteToList(url);

        int thisPathSize = thisPathList.size();
        int pathSize = pathList.size();

        //执行完全匹配
        if (thisPathSize == pathSize) {
            for (int i = 0; i < thisPathSize; i++) {
                String thisPathPart = thisPathList.get(i);
                String pathPart = pathList.get(i);

                //*匹配
                if ((i == thisPathSize - 1) && (thisPathPart.equals("*") && this.matchPath.endsWith("*"))) {
                    return true;
                }

                //pathParam
                if ((!thisPathPart.startsWith(":"))
                        && !thisPathPart.equals(pathPart)
                        && !thisPathPart.equals("*")) {
                    return false;
                }
            }
            return true;
        } else {
            // * 宽匹配
            if (this.matchPath.endsWith("*")) {
                if (pathSize == (thisPathSize - 1) && (url.endsWith("/"))) {
                    // Hack for making wildcards work with trailing slash
                    pathList.add("");
                    pathList.add("");
                    pathSize += 2;
                }

                if (thisPathSize < pathSize) {
                    for (int i = 0; i < thisPathSize; i++) {
                        String thisPathPart = thisPathList.get(i);
                        String pathPart = pathList.get(i);
                        if (thisPathPart.equals("*") && (i == thisPathSize - 1) && this.matchPath.endsWith("*")) {
                            // wildcard match
                            return true;
                        }
                        if (!thisPathPart.startsWith(":")
                                && !thisPathPart.equals(pathPart)
                                && !thisPathPart.equals("*")) {
                            return false;
                        }
                    }
                    // All parts matched
                    return true;
                }
                // End check wild card
            }
            return false;
        }
    }

    public String toString() {
        return httpMethod.name() + ", " + matchPath + ", " + route;
    }
}
