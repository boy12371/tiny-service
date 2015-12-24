package cn.shiroblue.core;

import cn.shiroblue.TinyApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TinyFilter implements Filter {
    private static final Logger LOG = LoggerFactory.getLogger(TinyFilter.class);

    // get the config in web.xml
    private static final String APPLICATION_CLASS_PARAM = "applicationClass";

    private TinyApplication tinyApplication;

    private TinyHandler tinyHandler;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.tinyApplication = getApplication(filterConfig);
        this.tinyApplication.init();
        this.tinyHandler = new TinyHandler();
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        this.handle(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        this.tinyApplication.destroy();
    }


    private TinyApplication getApplication(FilterConfig filterConfig) throws ServletException {
        try {
            String applicationClassName = filterConfig.getInitParameter(APPLICATION_CLASS_PARAM);

            LOG.info("Server : launch with {} ", applicationClassName);

            Class<?> applicationClass = Class.forName(applicationClassName);
            return (TinyApplication) applicationClass.newInstance();
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }


    private void handle(ServletRequest servletRequest, ServletResponse servletResponse) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;

        tinyHandler.handle(httpRequest, httpResponse);
    }


}
