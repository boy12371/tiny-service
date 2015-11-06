package com.whiteblue;

import cn.shiroblue.Catch;
import cn.shiroblue.ExceptionHandler;
import cn.shiroblue.Route;
import cn.shiroblue.TinyApplication;
import cn.shiroblue.http.Request;
import cn.shiroblue.http.Response;
import cn.shiroblue.route.HandlerRoute;

import javax.xml.bind.ValidationException;

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
                return "hello";
            }
        });

        Route.get("/test", new HandlerRoute() {
            @Override
            public Object handle(Request request, Response response) throws Exception {
                throw new ValidationException("dsad");
            }
        });

        Catch.exception(ValidationException.class, new ExceptionHandler() {
            @Override
            public void handle(Exception exception, Request request, Response response) {
                response.body("been catched");
            }
        });


    }

    @Override
    public void destroy() {

    }
}
