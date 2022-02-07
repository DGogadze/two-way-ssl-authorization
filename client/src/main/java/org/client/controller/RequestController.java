package org.client.controller;

import org.client.service.HttpClient;
import org.client.service.SSLContextHelper;
import org.client.structure.HttpPostRequestBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RequestController {
    @Autowired
    public RequestController(HttpClient httpClient, SSLContextHelper helper) {
        this.httpClient = httpClient;
        this.helper = helper;
    }

    private final HttpClient httpClient;
    private final SSLContextHelper helper;

    @RequestMapping("/")
    private ResponseEntity<String> makeRequest() {
        StringBuilder responseBody = new StringBuilder();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.TEXT_HTML);
        try {
            HttpPostRequestBean httpPostRequestBean = new HttpPostRequestBean("https://localhost:443/", 10000, 10000);
            httpPostRequestBean.setSslContext(helper.getSSLContext());
            httpPostRequestBean.setBodyString("Hello");

            responseBody.append(String.format("<p style=\"color:orange\">HTTPPostRequestBean sent</p> -> %s <br>", httpPostRequestBean));
            responseBody.append(String.format("<p style=\"color:green\">HTTPResponse claimed</p> -> %s <br>", httpClient.makeSyncRequest(httpPostRequestBean)));
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(responseBody.toString(), httpHeaders, HttpStatus.OK);
    }
}
