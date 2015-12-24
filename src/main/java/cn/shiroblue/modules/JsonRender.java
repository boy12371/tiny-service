package cn.shiroblue.modules;

import cn.shiroblue.http.Response;
import com.alibaba.fastjson.JSON;


public class JsonRender implements Render {
    @Override
    public void rend(Response response, Object model) throws Exception {
        response.contentType("application/json");
        response.body(JSON.toJSONString(model));
    }
}
