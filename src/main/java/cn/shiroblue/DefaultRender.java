package cn.shiroblue;

import cn.shiroblue.core.ResponseTransformer;

/**
 * Description:
 * 默认render(返回toString)
 * ======================
 * by WhiteBlue
 * on 15/10/30
 */
public class DefaultRender implements ResponseTransformer {
    @Override
    public String render(Object model) throws Exception {
        return model.toString();
    }
}
