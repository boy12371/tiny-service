package cn.shiroblue.modules;

import cn.shiroblue.http.Response;

/**
 * Description:
 * 默认render(返回toString)
 * ======================
 * by WhiteBlue
 * on 15/10/30
 */
public class DefaultRender implements Render {
    @Override
    public void rend(Response response, Object model) throws Exception {
        response.body(model.toString());
    }
}
