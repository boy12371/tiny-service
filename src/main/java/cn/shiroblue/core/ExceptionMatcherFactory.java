package cn.shiroblue.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Description:
 * <p>
 * ======================
 * by WhiteBlue
 * on 15/10/30
 */
public class ExceptionMatcherFactory {
    private static final Logger LOG = LoggerFactory.getLogger(ExceptionMatcherFactory.class);

    private static ExceptionMapper exceptionMapper = null;

    private ExceptionMatcherFactory() {
    }

    public static ExceptionMapper get() {
        if (exceptionMapper == null) {
            LOG.debug("create ExceptionMatcher");
            exceptionMapper = new ExceptionMapper();
        }
        return exceptionMapper;
    }
}
