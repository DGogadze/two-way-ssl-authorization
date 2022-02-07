package org.client.structure;

public class HttpPostRequestBean extends HttpRequestBean {
    private String bodyString;

    public HttpPostRequestBean(String url, int connectionTimeout, int requestTimeout) {
        super(url, connectionTimeout, requestTimeout);
    }

    public String getBodyString() {
        return bodyString;
    }

    public void setBodyString(String bodyString) {
        this.bodyString = bodyString;
    }

    @Override
    public String toString() {
        return "BaseUrl           : " + getBaseUrl() +
                "\nRequestParams     : " + concatenateParams() +
                "\nConnectionTimeout : " + getConnectionTimeout() +
                "\nRequestTimeout    : " + getRequestTimeout() +
                "\nIgnoreSSL         : " + isIgnoreSSL() +
                "\nCharSetEncoding   : " + getCharSetEncoding() +
                "\nHeaders           : " + getHeader() +
                "\nBody              : " + bodyString;
    }
}
