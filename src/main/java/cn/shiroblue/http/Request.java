package cn.shiroblue.http;

import cn.shiroblue.route.RouteMatch;
import cn.shiroblue.utils.TinyUtils;
import cn.shiroblue.utils.UrlUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.*;

public class Request {

    private static final Logger LOG = LoggerFactory.getLogger(Request.class);

    private static final String USER_AGENT = "user-agent";

    private Map<String, String> params;

    public HttpServletRequest servletRequest;

    // Lazy loaded stuff
    private String body = null;
    private byte[] bodyAsBytes = null;

    private Set<String> headers = null;

    public Request(HttpServletRequest servletRequest) {
        this.servletRequest = servletRequest;
    }

    public Request(RouteMatch match, HttpServletRequest request) {
        this.servletRequest = request;
        changeMatch(match);
    }

    private static Map<String, String> getParams(List<String> request, List<String> matched) {
        Map<String, String> params = new HashMap<>();
        for (int i = 0; (i < request.size()) && (i < matched.size()); i++) {
            String matchedPart = matched.get(i);
            if (TinyUtils.isParam(matchedPart)) {
                params.put(matchedPart.toLowerCase(), request.get(i));
            }
        }
        return params;
    }

    /**
     * math the request to an route
     *
     * @param match RouteMatch
     */
    private void changeMatch(RouteMatch match) {
        //match and get the url param
        List<String> requestList = UrlUtils.convertRouteToList(match.getUrl());
        List<String> matchedList = UrlUtils.convertRouteToList(match.getMatchPath());
        params = getParams(requestList, matchedList);
    }


    public void bind(RouteMatch match) {
        this.clearParam();
        this.changeMatch(match);
    }

    /**
     * return url params
     *
     * @return a map containing all route params
     */
    public Map<String, String> pathParams() {
        return Collections.unmodifiableMap(params);
    }


    /**
     * get url param
     *
     * @param param the param
     * @return null if the given param is null or not found
     */
    public String pathParam(String param) {
        if (param.startsWith(":")) {
            return params.get(param.toLowerCase()); // NOSONAR
        } else {
            return params.get(":" + param.toLowerCase()); // NOSONAR
        }
    }

    /**
     * return the http method
     *
     * @return request method e.g. GET, POST, PUT, ...
     */
    public String requestMethod() {
        return servletRequest.getMethod();
    }

    /**
     * @return the scheme
     */
    public String scheme() {
        return servletRequest.getScheme();
    }

    /**
     * @return the host
     */
    public String host() {
        return servletRequest.getHeader("host");
    }

    /**
     * @return the user-agent
     */
    public String userAgent() {
        return servletRequest.getHeader(USER_AGENT);
    }


    /**
     * @return the path info
     * Example return: "/example/foo"
     */
    public String pathInfo() {
        return servletRequest.getPathInfo();
    }

    /**
     * @return the servlet path
     */
    public String servletPath() {
        return servletRequest.getServletPath();
    }

    /**
     * @return the context path
     */
    public String contextPath() {
        return servletRequest.getContextPath();
    }

    /**
     * @return the URL string
     */
    public String url() {
        return servletRequest.getRequestURL().toString();
    }

    /**
     * @return the content type of the body
     */
    public String contentType() {
        return servletRequest.getContentType();
    }

    /**
     * @return the client's IP address
     */
    public String ip() {
        return servletRequest.getRemoteAddr();
    }

    /**
     * @return the request body sent by the client
     */
    public String body() {
        if (body == null) {
            body = new String(bodyAsBytes());
        }
        return body;
    }

    public byte[] bodyAsBytes() {
        if (bodyAsBytes == null) {
            readBodyAsBytes();
        }
        return bodyAsBytes;
    }

    private void readBodyAsBytes() {
        try {
            byte[] buffer = new byte[1024];
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            InputStream inputStream = this.servletRequest.getInputStream();
            while (-1 != inputStream.read(buffer)) {
                byteArrayOutputStream.write(buffer);
            }
            bodyAsBytes = byteArrayOutputStream.toByteArray();
        } catch (Exception e) {
            LOG.warn("Exception when reading body", e);
        }
    }

    /**
     * return the content-length
     *
     * @return int
     */
    public int contentLength() {
        return servletRequest.getContentLength();
    }

    /**
     * return a query param
     *
     * @param queryParam the query parameter
     * @return the value of the provided queryParam
     */
    public String queryParam(String queryParam) {
        return servletRequest.getParameter(queryParam);
    }


    /**
     * get all queryparam which key is same
     *
     * @param queryParam the query parameter
     * @return the values of the provided queryParam, null if it doesn't exists
     */
    public String[] queryParams(String queryParam) {
        return servletRequest.getParameterValues(queryParam);
    }

    /**
     * get header value
     *
     * @param key the header
     * @return the value of the provided header
     */
    public String header(String key) {
        return servletRequest.getHeader(key);
    }


    /**
     * get all headers
     *
     * @return all headers
     */
    public Set<String> headers() {
        if (headers == null) {
            headers = new TreeSet<>();
            Enumeration<String> enumeration = servletRequest.getHeaderNames();
            while (enumeration.hasMoreElements()) {
                headers.add(enumeration.nextElement());
            }
        }
        return headers;
    }

    /**
     * @return the query string
     */
    public String queryString() {
        return servletRequest.getQueryString();
    }

    /**
     * Sets an attribute on the request (can be fetched in filters/routes later in the chain)
     *
     * @param attribute The attribute
     * @param value     The attribute value
     */
    public void attribute(String attribute, Object value) {
        servletRequest.setAttribute(attribute, value);
    }


    /**
     * @return the raw HttpServletRequest object handed in by Jetty
     */
    public HttpServletRequest raw() {
        return servletRequest;
    }


    /**
     * @return the part of this request's URL from the protocol name up to the query string in the first line of the HTTP request.
     */
    public String uri() {
        return servletRequest.getRequestURI();
    }


    public void clearParam() {
        if (this.params != null) {
            this.params.clear();
        }
    }

}

