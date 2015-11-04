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


        Action.get("/test", (request, response) -> "诶嘿");
    }

    @Override
    public void destroy() {

    }
}
