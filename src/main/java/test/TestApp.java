package test;

import cn.shiroblue.Action;
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
        Action.get("/", (request, response) -> "Hello world...");


        Action.get("/test", (request, response) -> {
            System.out.println("执行模式");
            return "执行";
        });

        Action.before("/*", (request, response) -> {
            System.out.println("首拦截器");

            return null;
        });

        Action.after("/*", (request, response) -> {
            System.out.println("尾拦截器");

            return null;
        });

    }

    @Override
    public void destroy() {

    }
}
