package cn.shiroblue.route;

import cn.shiroblue.http.Request;
import cn.shiroblue.http.Response;


public interface FilterRoute {

    void handle(Request request, Response response) throws Exception;

}
