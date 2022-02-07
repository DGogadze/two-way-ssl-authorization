package org.client.service;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.TrustAllStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;
import org.client.structure.HttpGetRequestBean;
import org.client.structure.HttpPostRequestBean;
import org.client.structure.HttpRequestBean;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

@Lazy
@Service
public class HttpClient {
    public String makeSyncRequest(HttpGetRequestBean httpGetRequestBean) throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException, IOException {
        HttpResponse httpResponse = getHttpResponse(httpGetRequestBean);
        return getEntity(httpResponse, httpGetRequestBean.getCharSetEncoding());
    }

    private HttpResponse getHttpResponse(HttpGetRequestBean httpGetRequestBean) throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException, IOException {
        org.apache.http.client.HttpClient httpClient = buildHttpClient(httpGetRequestBean);
        HttpUriRequest request = new HttpGet(httpGetRequestBean.getRequestURL());
        setHeaderValues(request, httpGetRequestBean.getHeader());
        return httpClient.execute(request);
    }

    public String makeSyncRequest(HttpPostRequestBean httpPostRequestBean) throws IOException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        HttpResponse httpResponse = getHttpResponse(httpPostRequestBean);
        return getEntity(httpResponse, httpPostRequestBean.getCharSetEncoding());
    }

    private HttpResponse getHttpResponse(HttpPostRequestBean httpPostRequestBean) throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException, IOException {
        org.apache.http.client.HttpClient httpClient = buildHttpClient(httpPostRequestBean);
        HttpPost request = new HttpPost(httpPostRequestBean.getRequestURL());
        setHeaderValues(request, httpPostRequestBean.getHeader());
        request.setEntity(new StringEntity(httpPostRequestBean.getBodyString()));

        return httpClient.execute(request);
    }

    private String getEntity(HttpResponse response, String charSetEncoding) throws IOException {
        String responseString;
        HttpEntity entity = response.getEntity();
        responseString = EntityUtils.toString(entity, charSetEncoding);
        return responseString;
    }

    private org.apache.http.client.HttpClient buildHttpClient(HttpRequestBean myHttpRequest) throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
        HttpClientBuilder httpClientBuilder = HttpClients
                .custom()
                .setDefaultRequestConfig(RequestConfig.custom().setConnectTimeout(myHttpRequest.getConnectionTimeout()).setConnectionRequestTimeout(myHttpRequest.getRequestTimeout()).build());

        if (myHttpRequest.getSslContext() != null) {
            httpClientBuilder.setSSLContext(myHttpRequest.getSslContext());
        }

        if (myHttpRequest.isIgnoreSSL()) {
            httpClientBuilder.setSSLContext(new SSLContextBuilder().loadTrustMaterial(null, TrustAllStrategy.INSTANCE).build())
                    .setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE);
        }
        return httpClientBuilder.build();
    }

    private void setHeaderValues(HttpUriRequest request, Map<String, String> header) {
        if (header == null) {
            return;
        }
        for (String headerName : header.keySet()) {
            request.addHeader(headerName, header.get(headerName));
        }
    }
}
