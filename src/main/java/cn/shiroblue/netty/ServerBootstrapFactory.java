package cn.shiroblue.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Description:
 * <p>
 * ======================
 * by WhiteBlue
 * on 15/12/23
 */
public class ServerBootstrapFactory {
    private static Logger LOG = LoggerFactory.getLogger(ServerBootstrapFactory.class);

    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;

    public ServerBootstrap getServerBootstrap(int acceptor, int ioThread) {
        if (Epoll.isAvailable()) {
            LOG.info("Platform is {}, use EpollEventLoopGroup", System.getProperties().getProperty("os.name"));
            bossGroup = new EpollEventLoopGroup(acceptor);
            workerGroup = new EpollEventLoopGroup(ioThread);
            return new ServerBootstrap().group(bossGroup, workerGroup).channel(EpollServerSocketChannel.class);
        }
        LOG.info("Platform is {}, use NioEventLoopGroup", System.getProperties().getProperty("os.name"));
        bossGroup = new NioEventLoopGroup(acceptor);
        workerGroup = new NioEventLoopGroup(ioThread);
        return new ServerBootstrap().group(bossGroup, workerGroup).channel(NioServerSocketChannel.class);
    }


    public void shutdown() {
        if (workerGroup != null)
            workerGroup.shutdownGracefully();
        if (bossGroup != null)
            bossGroup.shutdownGracefully();
    }

}
