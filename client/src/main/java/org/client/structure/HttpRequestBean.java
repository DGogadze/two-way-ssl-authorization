package org.client.structure;

import org.apache.http.conn.ssl.SSLConnectionSocketFactory;

import javax.net.ssl.SSLContext;
import java.util.List;
import java.util.Map;

public class HttpRequestBean {
    private final String baseUrl;
    private int connectionTimeout;
    private int requestTimeout;
    private boolean ignoreSSL;
    private String charSetEncoding = "UTF-8";
    private Map<String, String> header;
    private List<RequestParametersBean> requestParams;
    private SSLConnectionSocketFactory sslConnectionSocketFactory;
    private SSLContext sslContext;

    public HttpRequestBean(String url, int connectionTimeout, int requestTimeout) {
        this.baseUrl = url;
        this.connectionTimeout = connectionTimeout;
        this.requestTimeout = requestTimeout;
    }


    public int getConnectionTimeout() {
        return connectionTimeout;
    }

    public void setConnectionTimeout(int connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }


    public int getRequestTimeout() {
        return requestTimeout;
    }

    public void setRequestTimeout(int requestTimeout) {
        this.requestTimeout = requestTimeout;
    }


    public boolean isIgnoreSSL() {
        return ignoreSSL;
    }

    public void setIgnoreSSL(boolean ignoreSSL) {
        this.ignoreSSL = ignoreSSL;
    }


    public Map<String, String> getHeader() {
        return header;
    }

    public void setHeader(Map<String, String> header) {
        this.header = header;
    }

    public void setRequestParams(List<RequestParametersBean> requestParams) {
        this.requestParams = requestParams;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public String getRequestURL() {
        if (requestParams == null)
            return baseUrl;

        return baseUrl + "?" + concatenateParams();
    }

    public String getCharSetEncoding() {
        return charSetEncoding;
    }

    public void setCharSetEncoding(String charSetEncoding) {
        this.charSetEncoding = charSetEncoding;
    }

    protected String concatenateParams() {
        if (requestParams == null)
            return null;

        StringBuilder stringBuilder = new StringBuilder();

        for (RequestParametersBean currentParameter : requestParams) {
            stringBuilder
                    .append(currentParameter.getParameterName())
                    .append("=")
                    .append(currentParameter.getParameterValue())
                    .append("&");
        }
        return stringBuilder.substring(0, stringBuilder.length() - 1);
    }

    public String parametersToString() {
        if (requestParams == null)
            return null;
        StringBuilder stringBuilder = new StringBuilder();

        for (RequestParametersBean currentParameter : requestParams) {
            stringBuilder.append(currentParameter.getParameterName())
                    .append(":")
                    .append(" ")
                    .append(currentParameter.getParameterValue())
                    .append(";")
                    .append(" ");
        }
        return stringBuilder.substring(0, stringBuilder.length() - 1);
    }

    @Override
    public String toString() {
        return "BaseUrl           : " + baseUrl +
                "\nRequestParams     : " + concatenateParams() +
                "\nConnectionTimeout : " + connectionTimeout +
                "\nRequestTimeout    : " + requestTimeout +
                "\nIgnoreSSL : " + ignoreSSL +
                "\nCharSetEncoding   : " + charSetEncoding +
                "\nHeaders           : " + header +
                "\nSSLCertificateUsed   : " + (getSslConnectionSocketFactory() != null);
    }

    public SSLConnectionSocketFactory getSslConnectionSocketFactory() {
        return sslConnectionSocketFactory;
    }

    public void setSslConnectionSocketFactory(SSLConnectionSocketFactory sslConnectionSocketFactory) {
        this.sslConnectionSocketFactory = sslConnectionSocketFactory;
    }

    public SSLContext getSslContext() {
        return sslContext;
    }

    public void setSslContext(SSLContext sslContext) {
        this.sslContext = sslContext;
    }
}
