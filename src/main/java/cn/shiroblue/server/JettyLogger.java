package cn.shiroblue.server;

import org.eclipse.jetty.util.log.Logger;

/**
 * Description:
 * <p>
 * ======================
 * by WhiteBlue
 * on 15/11/8
 */
public class JettyLogger implements Logger {
    private org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(getClass());

    public void debug(String msg, Throwable th) {
        logger.debug(msg, th);
    }

    public Logger getLogger(String arg) {
        return this;
    }

    @Override
    public boolean isDebugEnabled() {
        return logger.isDebugEnabled();
    }

    @Override
    public void warn(String msg, Throwable th) {
        logger.warn(msg, th);
    }

    @Override
    public void debug(Throwable thrown) {
        logger.debug("", thrown);

    }

    @Override
    public void debug(String msg, Object... args) {
        StringBuffer log = new StringBuffer(msg);
        for (Object arg : args) {
            log.append(", ");
            log.append(arg);
        }
        logger.debug(log.toString());
    }

    @Override
    public String getName() {
        return "Spark Jetty Logger";
    }

    @Override
    public void ignore(Throwable ignored) {
        //
    }

    @Override
    public void info(Throwable thrown) {
        logger.info("", thrown);
    }

    @Override
    public void info(String msg, Object... args) {
        StringBuffer log = new StringBuffer(msg);
        for (Object arg : args) {
            log.append(", ");
            log.append(arg);
        }
        logger.info(log.toString());
    }

    @Override
    public void info(String msg, Throwable thrown) {
        logger.info(msg, thrown);
    }

    @Override
    public void setDebugEnabled(boolean enabled) {
        //
    }

    @Override
    public void warn(Throwable thrown) {
        logger.warn("", thrown);
    }

    @Override
    public void warn(String msg, Object... args) {
        StringBuffer log = new StringBuffer(msg);
        for (Object arg : args) {
            log.append(", ");
            log.append(arg);
        }
        logger.warn(log.toString());
    }

    @Override
    public void debug(String arg0, long arg1) {
        logger.debug(arg0, arg1);
    }

}
