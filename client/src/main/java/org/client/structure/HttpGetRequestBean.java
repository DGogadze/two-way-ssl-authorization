package org.client.structure;

public class HttpGetRequestBean extends HttpRequestBean {
    public HttpGetRequestBean(String url, int connectionTimeout, int requestTimeout) {
        super(url, connectionTimeout, requestTimeout);
    }
}
