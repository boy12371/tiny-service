package test;

import cn.shiroblue.*;

/**
 * Description:
 * <p>
 * ======================
 * by WhiteBlue
 * on 15/10/30
 */
public class TestApp implements TinyApplication {

    @Override
    public void init() {
        Action.get("/", new Route() {
            @Override
            public Object handle(Request request, Response response) throws Exception {
                return "hello world!";
            }
        });
    }

    @Override
    public void destroy() {

    }

}
