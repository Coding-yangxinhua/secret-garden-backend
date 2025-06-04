package com.pwc.sdc.sg.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.*;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

@Configuration
public class RestTemplateConfig {

    @Bean("proxyRestTemplate")
    public RestTemplate restTemplate() throws KeyManagementException, NoSuchAlgorithmException {
        // 创建信任所有证书的TrustManager
        TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {
                    public X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }
                    public void checkClientTrusted(X509Certificate[] certs, String authType) {
                    }
                    public void checkServerTrusted(X509Certificate[] certs, String authType) {
                    }
                }
        };

        // 创建SSL上下文并初始化
        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, trustAllCerts, new java.security.SecureRandom());

        // 创建允许所有主机名验证的HostnameVerifier
        HostnameVerifier allHostsValid = (hostname, session) -> true;

        // 创建支持代理和SSL配置的请求工厂
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory() {
            @Override
            protected void prepareConnection(java.net.HttpURLConnection connection, String httpMethod) throws IOException {
                if (connection instanceof javax.net.ssl.HttpsURLConnection) {
                    javax.net.ssl.HttpsURLConnection httpsConnection = (javax.net.ssl.HttpsURLConnection) connection;
                    httpsConnection.setSSLSocketFactory(sslContext.getSocketFactory());
                    httpsConnection.setHostnameVerifier(allHostsValid);
                }
                super.prepareConnection(connection, httpMethod);
            }
        };

        // 设置代理服务器
        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("127.0.0.1", 8888));
        requestFactory.setProxy(proxy);

        return new RestTemplate(requestFactory);
    }
}    