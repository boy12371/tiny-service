package cn.shiroblue.core;

import cn.shiroblue.DefaultRender;

/**
 * Description:
 * <p>
 * ======================
 * by WhiteBlue
 * on 15/10/30
 */
public class RenderFactory {
    private static ResponseTransformer render = null;

    private RenderFactory() {
    }

    public static ResponseTransformer get() {
        if (render == null) {
            render = new DefaultRender();
        }
        return render;
    }

    /**
     * 设置新render
     *
     * @param responseTransformer Render impl
     */
    public static void setRennder(ResponseTransformer responseTransformer) {
        render = responseTransformer;
    }

}
