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
    String matchPath;
    Object target;


    public RouteEntry(HttpMethod httpMethod, String matchUrl, Object target) {
        this.httpMethod = httpMethod;
        this.matchPath = matchUrl;
        this.target = target;
    }

    boolean matches(HttpMethod httpMethod, String path) {
        if ((httpMethod == HttpMethod.before || httpMethod == HttpMethod.after)
                && (this.httpMethod == httpMethod)
                && this.matchPath.equals(UrlUtils.ALL_PATHS)) {
            return true;
        }
        boolean match = false;
        if (this.httpMethod == httpMethod) {
            match = matchPath(path);
        }
        return match;
    }

    private boolean matchPath(String url) {
        if (!this.matchPath.endsWith("*") && ((url.endsWith("/") && !this.matchPath.endsWith("/"))
                || (this.matchPath.endsWith("/") && !url.endsWith("/")))) {
            // One and not both ends with slash
            return false;
        }

        if (this.matchPath.equals(url)) {
            return true;
        }

        List<String> thisPathList = UrlUtils.convertRouteToList(this.matchPath);
        List<String> pathList = UrlUtils.convertRouteToList(url);

        int thisPathSize = thisPathList.size();
        int pathSize = pathList.size();

        if (thisPathSize == pathSize) {
            for (int i = 0; i < thisPathSize; i++) {
                String thisPathPart = thisPathList.get(i);
                String pathPart = pathList.get(i);

                if ((i == thisPathSize - 1) && (thisPathPart.equals("*") && this.matchPath.endsWith("*"))) {
                    // wildcard match
                    return true;
                }

                if ((!thisPathPart.startsWith(":"))
                        && !thisPathPart.equals(pathPart)
                        && !thisPathPart.equals("*")) {
                    return false;
                }
            }
            // All parts matched
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
        return httpMethod.name() + ", " + matchPath + ", " + target;
    }
}
