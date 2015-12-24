package cn.shiroblue.http;

import javax.servlet.http.HttpServletResponse;

/**
 * Description:
 * <p>
 * ======================
 * by WhiteBlue
 * on 15/10/30
 */
public class ResponseWrapper extends Response {

    private Response delegate;


    public ResponseWrapper(Response response) {
        this.delegate = response;
    }

    Response getDelegate() {
        return delegate;
    }

    public void setDelegate(Response delegate) {
        this.delegate = delegate;
    }

    @Override
    public void status(int statusCode) {
        delegate.status(statusCode);
    }

    @Override
    public void body(String body) {
        delegate.body(body);
    }

    @Override
    public String body() {
        return delegate.body();
    }

    @Override
    public boolean equals(Object obj) {
        return delegate.equals(obj);
    }

    @Override
    public int hashCode() {
        return delegate.hashCode();
    }

    @Override
    public HttpServletResponse raw() {
        return delegate.raw();
    }


    @Override
    public void header(String header, String value) {
        delegate.header(header, value);
    }

    @Override
    public String toString() {
        return delegate.toString();
    }

    @Override
    public void contentType(String contentType) {
        delegate.contentType(contentType);
    }

}
