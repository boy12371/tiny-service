package cn.shiroblue.server;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * Description:
 * <p>
 * ======================
 * by WhiteBlue
 * on 15/11/8
 */
public class JettyServer {
    private static Logger LOG = LoggerFactory.getLogger(JettyServer.class);

    //默认端口
    private static final int DEFAULT_PORT = 8080;

    private static Server server = null;

    private Handler handler;


    public JettyServer(Handler handler) {
        this.handler = handler;
    }


    /**
     * launch a inner jetty server
     *
     * @param host                    Server host
     * @param port                    Server listening port
     * @param maxThreads              max threads
     * @param minThreads              min threads
     * @param threadIdleTimeoutMillis thread timeout
     */
    public void start(String host, int port, int maxThreads, int minThreads, int threadIdleTimeoutMillis) {
        //Jetty日志重定向
        System.setProperty("org.mortbay.log.class", "cn.shiroblue.server.JettyLogger");

        server = new Server(new QueuedThreadPool(minThreads, maxThreads, threadIdleTimeoutMillis));

        ServerConnector connector = getConnector(server, host, port);

        server = connector.getServer();
        server.setConnectors(new Connector[]{connector});

        server.setHandler(handler);

        try {
            LOG.info("== {} has launched ...", "TinyService");
            LOG.info(">> Listening on {}:{}", host, port);

            server.start();
            server.join();
        } catch (Exception e) {
            LOG.error("Server launch failed", e);
            System.exit(100);
        }
    }


    private ServerConnector getConnector(Server server, String host, int port) {
        ServerConnector connector = new ServerConnector(server);

        connector.setIdleTimeout(TimeUnit.HOURS.toMillis(1));
        connector.setSoLingerTime(-1);
        connector.setHost(host);
        connector.setPort(port);

        return connector;
    }

}
