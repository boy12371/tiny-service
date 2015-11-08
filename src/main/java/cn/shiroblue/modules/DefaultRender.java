package cn.shiroblue.modules;

import cn.shiroblue.modules.Render;

/**
 * Description:
 * 默认render(返回toString)
 * ======================
 * by WhiteBlue
 * on 15/10/30
 */
public class DefaultRender implements Render {
    @Override
    public String rend(Object model) throws Exception {
        return model.toString();
    }
}
