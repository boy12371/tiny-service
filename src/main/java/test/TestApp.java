package test;

import cn.shiroblue.Route;
import cn.shiroblue.TinyApplication;

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
        Route.get("/", (request, response) -> "Hello world...");


        Route.get("/test/:param", (request, response) -> "Test" + request.pathParam("param"));

        Route.before("/*", (request, response) -> {
            System.out.println("Before Filter");
        });

        Route.after("/*", (request, response) -> {
            System.out.println("After Filter");

        });

    }

    @Override
    public void destroy() {

    }
}
