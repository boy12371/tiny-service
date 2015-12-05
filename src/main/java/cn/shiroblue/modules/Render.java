package cn.shiroblue.modules;

import cn.shiroblue.http.Response;

/**
 * Description:
 * <p>
 * ======================
 * by WhiteBlue
 * on 15/10/30
 */
public interface Render {


    void rend(Response response, Object model) throws Exception;

}