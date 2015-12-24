package cn.shiroblue.netty;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.multipart.DefaultHttpDataFactory;
import io.netty.handler.codec.http.multipart.HttpDataFactory;
import io.netty.handler.codec.http.multipart.HttpPostRequestDecoder;
import io.netty.handler.codec.http.multipart.InterfaceHttpData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.util.List;
import java.util.Map;

/**
 * Description:
 * <p>
 * ======================
 * by WhiteBlue
 * on 15/12/24
 */
public class DummyServletHandler extends ChannelHandlerAdapter {
    private static final Logger LOG = LoggerFactory.getLogger(DummyServletHandler.class);

    private static final HttpDataFactory factory = new DefaultHttpDataFactory(DefaultHttpDataFactory.MINSIZE); //Disk

    private HttpPostRequestDecoder decoder;


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof HttpRequest) {
            HttpRequest request = (HttpRequest) msg;
            URI uri = new URI(request.uri());
            System.err.println("URI:" + uri.getPath());

            if (uri.getPath().equals("/")) {
                return;
            }

            for (Map.Entry<CharSequence, CharSequence> entry : request.headers()) {
                System.out.println("Header:" + entry.getKey().toString() + ":" + entry.getValue().toString());
            }

            QueryStringDecoder decoderQuery = new QueryStringDecoder(request.uri());
            Map<String, List<String>> uriAttributes = decoderQuery.parameters();
            for (Map.Entry<String, List<String>> attr : uriAttributes.entrySet()) {
                for (String attrVal : attr.getValue()) {
                    System.out.println("Query param: " + attr.getKey() + ":" + attrVal);
                }
            }

            //判断request请求是否是post请求
            if (request.method() == HttpMethod.POST) {
                try {
                    decoder = new HttpPostRequestDecoder(factory, request);
                } catch (HttpPostRequestDecoder.ErrorDataDecoderException e1) {
                    e1.printStackTrace();
                    ctx.channel().close();
                    return;
                }
            }
        }

        if (decoder != null) {
            if (msg instanceof HttpContent) {

            }
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        super.channelReadComplete(ctx);
    }
}
