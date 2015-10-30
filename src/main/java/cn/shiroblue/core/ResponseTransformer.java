package cn.shiroblue.core;

/**
 * Description:
 * <p>
 * ======================
 * by WhiteBlue
 * on 15/10/30
 */
public interface ResponseTransformer {


    String render(Object model) throws Exception;

}