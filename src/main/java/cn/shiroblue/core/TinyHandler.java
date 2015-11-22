package cn.shiroblue.core;

import cn.shiroblue.http.HaltException;
import cn.shiroblue.http.Request;
import cn.shiroblue.http.Response;
import cn.shiroblue.http.ResponseWrapper;
import cn.shiroblue.modules.ExceptionHandlerImpl;
import cn.shiroblue.modules.Render;
import cn.shiroblue.modules.RouteMatcher;
import cn.shiroblue.route.FilterRoute;
import cn.shiroblue.route.HandlerRoute;
import cn.shiroblue.route.HttpMethod;
import cn.shiroblue.route.RouteMatch;
import cn.shiroblue.utils.UrlUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.FilterChain;
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
    private static final Logger LOG = LoggerFactory.getLogger(TinyHandler.class);

    private static final String HTTP_METHOD_OVERRIDE_HEADER = "X-HTTP-Method-Override";
    private static final String INTERNAL_ERROR = "<html><body><h2>500 Internal Error</h2></body></html>";
    private static final String NOT_FOUND_ERROR = "<html><body><h2>404 Not Found</h2></body></html>";

    private RouteMatcher routeMatcher;
    private ExceptionMapper exceptionMapper;
    private Render defaultRender;

    private boolean isServletContext;

    public TinyHandler(boolean isServletContext) {
        this.isServletContext = isServletContext;
        this.routeMatcher = RouteMatcherFactory.get();
        this.exceptionMapper = ExceptionMapperFactory.get();
        this.defaultRender = RenderFactory.get();
    }


    public void handle(HttpServletRequest httpRequest, HttpServletResponse httpResponse, FilterChain filterChain) throws IOException, ServletException {
        //支持REST方法
        String method = httpRequest.getHeader(HTTP_METHOD_OVERRIDE_HEADER);
        if (method == null) {
            method = httpRequest.getMethod();
        }

        //method于url格式化
        HttpMethod httpMethod = HttpMethod.valueOf(method.toUpperCase());
        String url = UrlUtils.pathFormat(httpRequest.getRequestURI());

        Object bodyContent = null;

        //匹配Router
        List<RouteMatch> listRoute = this.routeMatcher.findMatchRote(httpMethod, url);

        Response response = new Response(httpResponse);
        Request request = new Request(httpRequest);

        ResponseWrapper responseWrapper = new ResponseWrapper(response);

        LOG.debug("Request : [httpMethod:" + httpMethod + ", url: " + url + "] ");

        try {
            //search match FilterRoute and do handle
            for (RouteMatch routeMatch : listRoute) {
                if ((routeMatch.getTarget() instanceof FilterRoute) && (routeMatch.getHttpMethod() == HttpMethod.before)) {

                    request.bind(routeMatch);

                    ((FilterRoute) routeMatch.getTarget()).handle(request, responseWrapper);
                    break;
                }
            }

            for (RouteMatch routeMatch : listRoute) {
                if (routeMatch.getTarget() instanceof HandlerRoute) {
                    request.bind(routeMatch);
                    Object element = ((HandlerRoute) routeMatch.getTarget()).handle(request, responseWrapper);

                    if (routeMatch.getRender() != null) {
                        bodyContent = routeMatch.getRender().rend(element);
                    } else {
                        bodyContent = this.defaultRender.rend(element);
                    }
                    break;
                }
            }

            //search match FilterRoute and do handle(after)
            for (RouteMatch routeMatch : listRoute) {
                if ((routeMatch.getTarget() instanceof FilterRoute) && (routeMatch.getHttpMethod() == HttpMethod.after)) {

                    request.bind(routeMatch);
                    ((FilterRoute) routeMatch.getTarget()).handle(request, responseWrapper);

                    String bodyAfterFilter = response.body();
                    if (bodyAfterFilter != null) {
                        bodyContent = bodyAfterFilter;
                    }
                    break;
                }
            }

        } catch (HaltException hEx) {
            LOG.debug("halt with code {}", hEx.getStatusCode());

            httpResponse.setStatus(hEx.getStatusCode());
            if (hEx.getBody() != null) {
                bodyContent = hEx.getBody();
            } else {
                bodyContent = "";
            }

        } catch (Exception e) {
            //异常拦截处理
            ExceptionHandlerImpl handler = this.exceptionMapper.getHandler(e);
            if (handler != null) {
                request.clearParam();
                handler.handle(e, request, responseWrapper);
                String bodyAfterHandler = response.body();
                if (bodyAfterHandler != null) {
                    bodyContent = bodyAfterHandler;
                }
            } else {
                LOG.error("", e);
                httpResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                bodyContent = INTERNAL_ERROR;
            }
        }


        if (bodyContent == null) {
            httpResponse.setStatus(404);
            bodyContent = NOT_FOUND_ERROR;
        }

        //写入body content
        if (!httpResponse.isCommitted()) {
            //默认content-type
            if (httpResponse.getContentType() == null) {
                httpResponse.setContentType("text/html; charset=utf-8");
            }
            PrintWriter printWriter = httpResponse.getWriter();

            printWriter.write(bodyContent.toString());

            printWriter.flush();
        }
    }


}
