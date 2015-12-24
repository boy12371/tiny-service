package cn.shiroblue.modules;

import cn.shiroblue.http.Response;


public interface Render {


    void rend(Response response, Object model) throws Exception;

}