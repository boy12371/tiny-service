package cn.shiroblue.route;

import cn.shiroblue.modules.Render;
import cn.shiroblue.utils.UrlUtils;

import java.util.List;

public class RouteEntry {
    public HttpMethod httpMethod;

    //match uri
    public String matchPath;

    //target Object
    public Object route;

    //bind render
    public Render render;

    public RouteEntry(HttpMethod httpMethod, String matchUrl, Object route) {
        this.httpMethod = httpMethod;
        this.matchPath = UrlUtils.pathFormat(matchUrl);
        this.route = route;
        this.render = null;
    }

    public RouteEntry(HttpMethod httpMethod, String matchUrl, Object route, Render render) {
        this.httpMethod = httpMethod;
        this.matchPath = UrlUtils.pathFormat(matchUrl);
        this.route = route;
        this.render = render;
    }

    /**
     * try match a url
     *
     * @param requestMethod request httpMethod
     * @param path          clean url
     * @return boolean
     */
    public boolean matches(HttpMethod requestMethod, String path) {
        boolean match = matchPath(path);

        if (match) {
            if (!((this.httpMethod == HttpMethod.before || this.httpMethod == HttpMethod.after) || (this.httpMethod == requestMethod))) {
                match = false;
            }
        }

        return match;
    }


    private boolean matchPath(String url) {
        //absolutely math
        if (this.matchPath.equals(url)) {
            return true;
        }
        //split
        List<String> thisPathList = UrlUtils.convertRouteToList(this.matchPath);
        List<String> pathList = UrlUtils.convertRouteToList(url);

        int thisPathSize = thisPathList.size();
        int pathSize = pathList.size();
        //same size
        if (thisPathSize == pathSize) {
            for (int i = 0; i < thisPathSize; i++) {
                //=>mathPath
                String thisPathPart = thisPathList.get(i);
                //=>url
                String pathPart = pathList.get(i);

                if ((i == thisPathSize - 1) && (thisPathPart.equals("*") && this.matchPath.endsWith("*"))) {
                    return true;
                }

                if ((!thisPathPart.startsWith(":")) && !thisPathPart.equals(pathPart) && !thisPathPart.equals("*")) {
                    return false;
                }
            }
            return true;
        } else {
            if (this.matchPath.endsWith("*")) {
                if (thisPathSize < pathSize) {
                    for (int i = 0; i < thisPathSize; i++) {
                        String thisPathPart = thisPathList.get(i);
                        String pathPart = pathList.get(i);

                        if ((i == thisPathSize - 1) && thisPathPart.equals("*") && this.matchPath.endsWith("*")) {
                            return true;
                        }

                        if (!thisPathPart.startsWith(":") && !thisPathPart.equals(pathPart) && !thisPathPart.equals("*")) {
                            return false;
                        }
                    }
                    return true;
                }
            }
            return false;
        }
    }

    public String toString() {
        return httpMethod.name() + ", " + matchPath + ", " + route;
    }
}
