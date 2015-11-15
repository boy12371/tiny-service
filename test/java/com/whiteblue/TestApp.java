package com.whiteblue;

import cn.shiroblue.Route;
import cn.shiroblue.TinyApplication;
import cn.shiroblue.http.Request;
import cn.shiroblue.http.Response;
import cn.shiroblue.route.HandlerRoute;


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
                return "poi~";
            }
        });

    }

    @Override
    public void destroy() {

    }
}
