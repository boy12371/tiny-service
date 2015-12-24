package cn.shiroblue;

import cn.shiroblue.server.JettyServerFactory;


public class Tiny {

    //hidden constructor
    private Tiny() {
    }

    public static void server(String host, int port) {
        JettyServerFactory.newInstance(host, port);
    }

    public static void server() {
        JettyServerFactory.newInstance();
    }

    public static void server(String host, int port, int maxThreads, int minThreads, int threadTimeoutMillis) {
        JettyServerFactory.newInstance(host, port, maxThreads, minThreads, threadTimeoutMillis);
    }

}
