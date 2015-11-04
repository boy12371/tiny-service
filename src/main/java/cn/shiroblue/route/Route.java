package cn.shiroblue.route;

import cn.shiroblue.http.Request;
import cn.shiroblue.http.Response;

/**
 * Description:
 * <p>
 * ======================
 * by WhiteBlue
 * on 15/10/30
 */
public interface Route {

    Object handle(Request request, Response response) throws Exception;

}
