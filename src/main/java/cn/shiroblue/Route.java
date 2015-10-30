package cn.shiroblue;

/**
 * Description:
 * <p>
 * ======================
 * by WhiteBlue
 * on 15/10/30
 */
public interface Route {

    Object handle(Request request, Response response) throws Exception;

}
