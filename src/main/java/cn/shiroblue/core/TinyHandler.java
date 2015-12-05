package cn.shiroblue.core;

import cn.shiroblue.http.HaltException;
import cn.shiroblue.http.Request;
import cn.shiroblue.http.Response;
import cn.shiroblue.modules.ExceptionHandlerImpl;
import cn.shiroblue.modules.Render;
import cn.shiroblue.modules.RouteMatcher;
import cn.shiroblue.route.FilterRoute;
import cn.shiroblue.route.HandlerRoute;
import cn.shiroblue.route.HttpMethod;
import cn.shiroblue.route.RouteMatch;
import cn.shiroblue.utils.UrlUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Description:
 * <p>
 * ======================
 * by WhiteBlue
 * on 15/11/8
 */
public class TinyHandler {
    private static final String HTTP_METHOD_OVERRIDE_HEADER = "X-HTTP-Method-Override";
    private static final String INTERNAL_ERROR = "<html><body><h2>500 Internal Error</h2></body></html>";
    private static final String NOT_FOUND_ERROR = "<html><body><h2>404 Not Found</h2></body></html>";

    private RouteMatcher routeMatcher;
    private ExceptionMapper exceptionMapper;
    private Render defaultRender;

    public TinyHandler() {
        this.routeMatcher = RouteMatcherFactory.get();
        this.exceptionMapper = ExceptionMapperFactory.get();
        this.defaultRender = RenderFactory.get();
    }

    public void handle(HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws IOException, ServletException {
        boolean handled = false;

        String method = httpRequest.getHeader(HTTP_METHOD_OVERRIDE_HEADER);
        if (method == null) {
            method = httpRequest.getMethod();
        }

        HttpMethod httpMethod = HttpMethod.valueOf(method.toUpperCase());
        String url = UrlUtils.pathFormat(httpRequest.getRequestURI());

        List<RouteMatch> listRoute = this.routeMatcher.findMatchRote(httpMethod, url);

        Response response = new Response(httpResponse);
        Request request = new Request(httpRequest);

        try {
            //search match FilterRoute and do handle
            for (RouteMatch routeMatch : listRoute) {
                if (routeMatch.getHttpMethod() == HttpMethod.before) {
                    request.bind(routeMatch);
                    ((FilterRoute) routeMatch.getTarget()).handle(request, response);
                }
            }

            for (RouteMatch routeMatch : listRoute) {
                if (routeMatch.getTarget() instanceof HandlerRoute) {
                    request.bind(routeMatch);
                    Object element = ((HandlerRoute) routeMatch.getTarget()).handle(request, response);

                    if (routeMatch.getRender() != null) {
                        routeMatch.getRender().rend(response, element);
                    } else {
                        this.defaultRender.rend(response, element);
                    }
                    handled = true;
                }
            }

            //search match FilterRoute and do handle(after)
            for (RouteMatch routeMatch : listRoute) {
                if (routeMatch.getHttpMethod() == HttpMethod.after) {
                    request.bind(routeMatch);
                    ((FilterRoute) routeMatch.getTarget()).handle(request, response);
                }
            }

        } catch (HaltException hEx) {
            httpResponse.setStatus(hEx.getStatusCode());
            handled = true;
        } catch (Exception e) {
            //异常拦截处理
            ExceptionHandlerImpl handler = this.exceptionMapper.getHandler(e);
            if (handler != null) {
                request.clearParam();
                handler.handle(e, request, response);
                handled = true;
            } else {
                e.printStackTrace();
                httpResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.body(INTERNAL_ERROR);
            }
        }

        if (!handled) {
            httpResponse.setStatus(404);
            response.body(NOT_FOUND_ERROR);
        }

        //写入body content
        if (!httpResponse.isCommitted()) {
            //默认content-type
            if (httpResponse.getContentType() == null) {
                httpResponse.setContentType("text/html; charset=utf-8");
            }
            String bodyContent = response.body();
            if (bodyContent != null) {
                PrintWriter printWriter = httpResponse.getWriter();
                printWriter.write(bodyContent);
                printWriter.flush();
                printWriter.close();
            }
        }
    }


}
