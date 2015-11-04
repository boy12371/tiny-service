package cn.shiroblue.core;


/**
 * Description:
 * <p>
 * ======================
 * by WhiteBlue
 * on 15/10/30
 */
public class ExceptionMatcherFactory {

    private static ExceptionMapper exceptionMapper = null;

    private ExceptionMatcherFactory() {
    }

    public static ExceptionMapper get() {
        if (exceptionMapper == null) {
            exceptionMapper = new ExceptionMapper();
        }
        return exceptionMapper;
    }
}
