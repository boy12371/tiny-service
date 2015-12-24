package cn.shiroblue.core;

import cn.shiroblue.modules.Render;
import cn.shiroblue.modules.RouteMatcher;


public class ExceptionMapperFactory {

    private static ExceptionMapper exceptionMapper = null;

    private ExceptionMapperFactory() {
    }

    public static ExceptionMapper get() {
        if (exceptionMapper == null) {
            exceptionMapper = new ExceptionMapper();
        }
        return exceptionMapper;
    }
}
