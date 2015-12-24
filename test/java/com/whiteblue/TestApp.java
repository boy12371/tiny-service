package com.whiteblue;

import cn.shiroblue.Route;
import cn.shiroblue.TinyApplication;
import cn.shiroblue.http.Request;
import cn.shiroblue.http.Response;
import cn.shiroblue.route.HandlerRoute;

import java.util.HashMap;
import java.util.Map;


/**
 * Description:
 * <p>
 * ======================
 * by WhiteBlue
 * on 15/11/3
 */
public class TestApp implements TinyApplication {
    @Override
    public void init() {

        Route.get("/", new HandlerRoute() {
            @Override
            public Object handle(Request request, Response response) throws Exception {
                response.contentType("application/json");

                Map<String, String> map = new HashMap<>();
                map.put("code", "success");
                map.put("content", "poi~");
                return map;
            }
        });

    }

    @Override
    public void destroy() {

    }
}
