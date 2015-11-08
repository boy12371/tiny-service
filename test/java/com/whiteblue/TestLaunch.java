package com.whiteblue;

import cn.shiroblue.Route;
import cn.shiroblue.Tiny;
import cn.shiroblue.http.Request;
import cn.shiroblue.http.Response;
import cn.shiroblue.route.HandlerRoute;

/**
 * Description:
 * <p>
 * ======================
 * by WhiteBlue
 * on 15/11/8
 */
public class TestLaunch {

    public static void main(String args[]) {

        Route.get("/", new HandlerRoute() {
            @Override
            public Object handle(Request request, Response response) throws Exception {
                return "hello";
            }
        });

        Route.get("/test", new HandlerRoute() {
            @Override
            public Object handle(Request request, Response response) throws Exception {
                return "hello also";
            }
        });

        Tiny.server();

    }

}
