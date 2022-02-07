package org.client.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import javax.net.ssl.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;

@Service
public class SSLContextHelper {
    private SSLContext sslContext = null;

    @Autowired
    public SSLContextHelper(ResourceLoader resourceLoader) {
        try {
            this.certificateFilePath = resourceLoader.getResource("classpath:certificates/client.jks").getURI().getPath();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String certificateFilePath;

    private final String certificatePassword = "client";

    public SSLContext getSSLContext() {
        try {
            synchronized (SSLContextHelper.class) {
                if (sslContext != null) {
                    return sslContext;
                } else {
                    sslContext = SSLContext.getInstance("SSL");

                    KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance("SunX509");
                    KeyStore keyStore = KeyStore.getInstance("JKS");
                    FileInputStream fileInputStream = new FileInputStream(certificateFilePath);
                    keyStore.load(fileInputStream, certificatePassword.toCharArray());
                    fileInputStream.close();
                    keyManagerFactory.init(keyStore, certificatePassword.toCharArray());

                    KeyStore ksCACert = KeyStore.getInstance(KeyStore.getDefaultType());
                    ksCACert.load(new FileInputStream(certificateFilePath), certificatePassword.toCharArray());
                    TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance("SunX509");
                    trustManagerFactory.init(ksCACert);

                    sslContext.init(keyManagerFactory.getKeyManagers(), trustAllCerts(), new SecureRandom());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sslContext;
    }

    private static TrustManager[] trustAllCerts() {
        return new TrustManager[]{
                new X509TrustManager() {
                    public X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }

                    @Override
                    public void checkClientTrusted(X509Certificate[] arg0, String arg1) {

                    }

                    @Override
                    public void checkServerTrusted(X509Certificate[] arg0, String arg1) {

                    }
                }
        };
    }
}
