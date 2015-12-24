package cn.shiroblue.core;

import cn.shiroblue.modules.ExceptionHandlerImpl;

import java.util.HashMap;
import java.util.Map;

public class ExceptionMapper {

    //the match map
    private Map<Class<? extends Exception>, ExceptionHandlerImpl> exceptionMap;

    public ExceptionMapper() {
        this.exceptionMap = new HashMap<>();
    }

    /**
     * add a new map link an Exception
     *
     * @param exceptionClass exceptionClass
     * @param handler        ExceptionHandlerImpl
     */
    public void map(Class<? extends Exception> exceptionClass, ExceptionHandlerImpl handler) {
        this.exceptionMap.put(exceptionClass, handler);
    }

    public ExceptionHandlerImpl getHandler(Class<? extends Exception> exceptionClass) {
        if (!this.exceptionMap.containsKey(exceptionClass)) {

            Class superclass = exceptionClass.getSuperclass();

            while (superclass != null) {
                if (this.exceptionMap.containsKey(superclass)) {
                    ExceptionHandlerImpl handler = this.exceptionMap.get(superclass);
                    this.exceptionMap.put(exceptionClass, handler);
                    return handler;
                }

                superclass = superclass.getSuperclass();
            }

            this.exceptionMap.put(exceptionClass, null);
            return null;
        }

        return this.exceptionMap.get(exceptionClass);
    }


    public ExceptionHandlerImpl getHandler(Exception exception) {
        return this.getHandler(exception.getClass());
    }
}
