package cn.shiroblue.server;

import cn.shiroblue.core.TinyHandler;

/**
 * Description:
 * <p>
 * ======================
 * by WhiteBlue
 * on 15/11/8
 */
public class JettyServerFactory {

    private static String DEFAULT_HOST = "127.0.0.1";
    private static int DEFAULT_PORT = 8080;

    private static int DEFAULT_MIN_THREAD = 8;
    private static int DEFAULT_MAX_THREAD = 200;
    private static int DEFAULT_THREAD_TIMEOUT = 60000;

    public static void newInstance() {
        newInstance(DEFAULT_HOST, DEFAULT_PORT, DEFAULT_MAX_THREAD, DEFAULT_MIN_THREAD, DEFAULT_THREAD_TIMEOUT);
    }

    public static void newInstance(String host, int port) {
        newInstance(host, port, DEFAULT_MAX_THREAD, DEFAULT_MIN_THREAD, DEFAULT_THREAD_TIMEOUT);
    }

    public static void newInstance(String host, int port, int maxThreads, int minThreads) {
        newInstance(host, port, maxThreads, minThreads, DEFAULT_THREAD_TIMEOUT);
    }

    public static void newInstance(String host, int port, int maxThreads, int minThreads, int threadTimeoutMillis) {
        TinyHandler tinyHandler = new TinyHandler(false);
        JettyHandler jettyHandler = new JettyHandler(tinyHandler);
        JettyServer jettyServer = new JettyServer(jettyHandler);
        jettyServer.start(host, port, maxThreads, minThreads, threadTimeoutMillis);
    }

}