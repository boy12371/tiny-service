package com.whiteblue;

import cn.shiroblue.Route;
import cn.shiroblue.Tiny;
import cn.shiroblue.core.RenderFactory;
import cn.shiroblue.http.Request;
import cn.shiroblue.http.Response;
import cn.shiroblue.modules.JsonRender;
import cn.shiroblue.route.HandlerRoute;

import java.util.HashMap;
import java.util.Map;

/**
 * Description:
 * <p>
 * ======================
 * by WhiteBlue
 * on 15/11/8
 */
public class TestLaunch {
    public static void main(String args[]) {

        RenderFactory.setDefaultRender(new JsonRender());

        Route.get("/", new HandlerRoute() {
            @Override
            public Object handle(Request request, Response response) throws Exception {
                Map<String, String> map = new HashMap<>();
                map.put("code", "success");
                map.put("content", "poi~");
                return map;
            }
        }, new JsonRender());

        Tiny.server("localhost", 8080, 3000, 20, 30000);

    }
}
