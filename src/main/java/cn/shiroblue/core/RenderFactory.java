package cn.shiroblue.core;

import cn.shiroblue.StringRender;

/**
 * Description:
 * <p>
 * ======================
 * by WhiteBlue
 * on 15/10/30
 */
public class RenderFactory {
    private static Render render = null;

    private RenderFactory() {
    }

    public static Render get() {
        if (render == null) {
            render = new StringRender();
        }
        return render;
    }

    /**
     * 设置新render
     *
     * @param responseTransformer Render impl
     */
    public static void setRennder(Render responseTransformer) {
        render = responseTransformer;
    }

}
