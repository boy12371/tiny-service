package cn.shiroblue.http;


import javax.servlet.http.HttpServletResponse;

public class Response {
    private HttpServletResponse response;
    private String body;

    //For wrapper
    protected Response() {
    }

    public Response(HttpServletResponse response) {
        this.response = response;
    }

    /**
     * set http code
     *
     * @param statusCode code
     */
    public void status(int statusCode) {
        response.setStatus(statusCode);
    }

    /**
     * 设置content-typpe
     *
     * @param contentType content-type
     */
    public void contentType(String contentType) {
        response.setContentType(contentType);
    }

    /**
     * set response body(string)
     *
     * @param body string
     */
    public void body(String body) {
        this.body = body;
    }

    /**
     * get the body
     *
     * @return the body
     */
    public String body() {
        return this.body;
    }

    /**
     * @return the raw response object handed in by Jetty
     */
    public HttpServletResponse raw() {
        return response;
    }


    /**
     * set a http header
     *
     * @param key   the header
     * @param value the value
     */
    public void header(String key, String value) {
        response.addHeader(key, value);
    }


    /**
     * get a http header
     *
     * @return String
     */
    public String header(String key) {
        return response.getHeader(key);
    }

}
