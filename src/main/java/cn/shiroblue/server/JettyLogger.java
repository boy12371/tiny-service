package cn.shiroblue.server;

import org.eclipse.jetty.util.log.Logger;
import org.slf4j.LoggerFactory;

/**
 * Description:
 * <p>
 * ======================
 * by WhiteBlue
 * on 15/11/8
 */
public class JettyLogger implements Logger {
    private static org.slf4j.Logger LOG = LoggerFactory.getLogger(JettyLogger.class);

    public void debug(String msg, Throwable th) {
        LOG.debug(msg, th);
    }

    public Logger getLogger(String arg) {
        return this;
    }

    @Override
    public boolean isDebugEnabled() {
        return LOG.isDebugEnabled();
    }

    @Override
    public void warn(String msg, Throwable th) {
        LOG.warn(msg, th);
    }

    @Override
    public void debug(Throwable thrown) {
        LOG.debug("", thrown);
    }

    @Override
    public void debug(String msg, Object... args) {
        StringBuffer log = new StringBuffer(msg);
        for (Object arg : args) {
            log.append(", ");
            log.append(arg);
        }
        LOG.debug(log.toString());
    }

    @Override
    public String getName() {
        return "Tiny Jetty Logger";
    }

    @Override
    public void ignore(Throwable ignored) {
        //
    }

    @Override
    public void info(Throwable thrown) {
        LOG.info("", thrown);
    }

    @Override
    public void info(String msg, Object... args) {
        StringBuffer log = new StringBuffer(msg);
        for (Object arg : args) {
            log.append(", ");
            log.append(arg);
        }
        LOG.info(log.toString());
    }

    @Override
    public void info(String msg, Throwable thrown) {
        LOG.info(msg, thrown);
    }

    @Override
    public void setDebugEnabled(boolean enabled) {
        //
    }

    @Override
    public void warn(Throwable thrown) {
        LOG.warn("", thrown);
    }

    @Override
    public void warn(String msg, Object... args) {
        StringBuffer log = new StringBuffer(msg);
        for (Object arg : args) {
            log.append(", ");
            log.append(arg);
        }
        LOG.warn(log.toString());
    }

    @Override
    public void debug(String arg0, long arg1) {
        LOG.debug(arg0, arg1);
    }

}
