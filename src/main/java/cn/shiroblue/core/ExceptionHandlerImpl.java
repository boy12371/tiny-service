package cn.shiroblue.core;

import cn.shiroblue.http.Response;

import javax.servlet.http.HttpServletRequest;

/**
 * Description:
 * <p>
 * ======================
 * by WhiteBlue
 * on 15/10/30
 */
public abstract class ExceptionHandlerImpl {
    /**
     * Holds the type of exception that this filter will handle
     */
    protected Class<? extends Exception> exceptionClass;

    /**
     * Initializes the filter with the provided exception type
     *
     * @param exceptionClass Type of exception
     */
    public ExceptionHandlerImpl(Class<? extends Exception> exceptionClass) {
        this.exceptionClass = exceptionClass;
    }

    /**
     * Returns type of exception that this filter will handle
     *
     * @return Type of exception
     */
    public Class<? extends Exception> exceptionClass() {
        return this.exceptionClass;
    }

    /**
     * Sets the type of exception that this filter will handle
     *
     * @param exceptionClass Type of exception
     */
    public void exceptionClass(Class<? extends Exception> exceptionClass) {
        this.exceptionClass = exceptionClass;
    }

    /**
     * Invoked when an exception that is mapped to this handler occurs during routing
     *
     * @param exception The exception that was thrown during routing
     * @param request   The request object providing information about the HTTP request
     * @param response  The response object providing functionality for modifying the response
     */
    public abstract void handle(Exception exception, HttpServletRequest request, Response response);
}
