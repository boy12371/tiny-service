package cn.shiroblue.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 * <p>
 * ======================
 * by WhiteBlue
 * on 15/10/29
 */
public class UrlUtils {
    public static final String ALL_PATHS = "/*";

    /**
     * 路由 => list
     *
     * @param route 路由
     * @return List
     */
    public static List<String> convertRouteToList(String route) {
        String[] pathArray = route.split("/");
        List<String> path = new ArrayList<>();
        for (String p : pathArray) {
            if (p.length() > 0) {
                path.add(p);
            }
        }
        return path;
    }


    /**
     * path格式化
     *
     * @return String
     */
    public static String pathFormat(String path) {
        if (path == null) {
            return "";
        }
        if (path.charAt(path.length() - 1) == '/') {
            path = path.substring(0, path.length() - 1);
        }

        return path;
    }
}
