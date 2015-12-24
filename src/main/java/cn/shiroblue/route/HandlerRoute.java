package cn.shiroblue.route;

import cn.shiroblue.http.Request;
import cn.shiroblue.http.Response;

//the handler impl
public interface HandlerRoute {

    Object handle(Request request, Response response) throws Exception;

}
