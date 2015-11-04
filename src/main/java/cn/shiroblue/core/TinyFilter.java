package cn.shiroblue.core;

import cn.shiroblue.TinyApplication;
import cn.shiroblue.http.Request;
import cn.shiroblue.http.Response;
import cn.shiroblue.http.ResponseWrapper;
import cn.shiroblue.route.HttpMethod;
import cn.shiroblue.route.RouteMatch;
import cn.shiroblue.route.RouteMatcher;
import cn.shiroblue.route.RouteType;
import cn.shiroblue.utils.UrlUtils;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * Description:
 * <p>
 * ======================
 * by WhiteBlue
 * on 15/10/25
 */
public class TinyFilter implements Filter {
    private static final Logger LOG = Logger.getLogger(TinyFilter.class);

    // web.xml 配置参数
    private static final String APPLICATION_CLASS_PARAM = "applicationClass";

    //请求参数
    private static final String HTTP_METHOD_OVERRIDE_HEADER = "X-HTTP-Method-Override";

    private TinyApplication tinyApplication;

    private RouteMatcher routeMatcher;
    private ExceptionMapper exceptionMapper;

    private Render mainRender;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

        //默认日志
        BasicConfigurator.configure();

        this.tinyApplication = getApplication(filterConfig);

        //初始化路径映射
        this.tinyApplication.init();

        this.routeMatcher = RouteMatcherFactory.get();
        this.exceptionMapper = ExceptionMatcherFactory.get();

        this.mainRender = RenderFactory.get();
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        this.handle(servletRequest, servletResponse, filterChain);
    }

    @Override
    public void destroy() {
        this.tinyApplication.destroy();
    }


    private TinyApplication getApplication(FilterConfig filterConfig) throws ServletException {
        try {
            String applicationClassName = filterConfig.getInitParameter(APPLICATION_CLASS_PARAM);
            Class<?> applicationClass = Class.forName(applicationClassName);
            return (TinyApplication) applicationClass.newInstance();
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }


    private void handle(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;

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
        ResponseWrapper responseWrapper = new ResponseWrapper(response);

        LOG.debug("Request : [httpMethod:" + httpMethod + ", url: " + url + "] ");

        try {
            //首拦截器执行
            for (RouteMatch routeMatch : listRoute) {
                if ((routeMatch.getRouteType() == RouteType.FILTER) && (routeMatch.getHttpMethod() == HttpMethod.before)) {

                    LOG.debug("Action : [actionType:" + routeMatch.getRouteType().name() + ", url: " + routeMatch.getMatchPath() + "] ");

                    Request request = new Request(routeMatch, httpRequest);
                    routeMatch.getTarget().handle(request, responseWrapper);
                }
            }

            RouteMatch match = null;

            //方法映射查找
            for (RouteMatch routeMatch : listRoute) {
                if (routeMatch.getRouteType() == RouteType.ACTION) {
                    match = routeMatch;
                }
            }

            //执行
            if (match != null) {

                LOG.debug("Action : [actionType:" + match.getRouteType().name() + ", url: " + match.getMatchPath() + "] ");

                Request request = new Request(match, httpRequest);
                Object element = match.getTarget().handle(request, responseWrapper);

                //映射的方法对视图文件进行渲染
                Object result = this.mainRender.rend(element);

                if (result != null) {
                    bodyContent = result;
                }
            }

            //尾拦截器执行
            for (RouteMatch routeMatch : listRoute) {
                if ((routeMatch.getRouteType() == RouteType.FILTER) && (routeMatch.getHttpMethod() == HttpMethod.after)) {

                    LOG.debug("Action : [actionType:" + routeMatch.getRouteType().name() + ", url: " + routeMatch.getMatchPath() + "] ");

                    Request request = new Request(routeMatch, httpRequest);
                    routeMatch.getTarget().handle(request, responseWrapper);

                    String bodyAfterFilter = response.body();
                    if (bodyAfterFilter != null) {
                        bodyContent = bodyAfterFilter;
                    }
                }
            }

        } catch (HaltException hEx) {
            LOG.debug("halt...");
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
                handler.handle(e, httpRequest, responseWrapper);
                String bodyAfterHandler = response.body();
                if (bodyAfterHandler != null) {
                    bodyContent = bodyAfterHandler;
                }
            }
        }

        //是否执行
        boolean consumed = bodyContent != null;

        if (consumed) {
            //写入body content
            if (!httpResponse.isCommitted()) {
                //默认content-type
                if (httpResponse.getContentType() == null) {
                    httpResponse.setContentType("text/html; charset=utf-8");
                }
                OutputStream outputStream = httpResponse.getOutputStream();

                IOUtils.write(bodyContent.toString(), outputStream);

                outputStream.flush();
            }
        } else if (filterChain != null) {
            filterChain.doFilter(httpRequest, httpResponse);
        }
    }


}
