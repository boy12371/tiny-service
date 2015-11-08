package cn.shiroblue.server;

import cn.shiroblue.core.TinyHandler;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.session.SessionHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Description:
 * <p>
 * ======================
 * by WhiteBlue
 * on 15/11/8
 */
public class JettyHandler extends SessionHandler {
    private TinyHandler tinyHandler;

    public JettyHandler(TinyHandler tinyHandler) {
        this.tinyHandler = tinyHandler;
    }

    @Override
    public void doHandle(
            String target,
            Request baseRequest,
            HttpServletRequest request,
            HttpServletResponse response) throws IOException, ServletException {

        boolean consumeFlag = tinyHandler.handle(request, response, null);

        if (!consumeFlag) {
            baseRequest.setHandled(false);
        } else {
            baseRequest.setHandled(true);
        }

    }
}
