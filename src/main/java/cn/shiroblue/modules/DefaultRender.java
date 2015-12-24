package cn.shiroblue.modules;

import cn.shiroblue.http.Response;


public class DefaultRender implements Render {
    @Override
    public void rend(Response response, Object model) throws Exception {
        response.body(model.toString());
    }
}
