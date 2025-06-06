package com.pwc.sdc.sg.common.config;

import org.apache.http.HttpRequestInterceptor;
import org.apache.http.HttpResponse;
import org.apache.http.HttpResponseInterceptor;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ServiceUnavailableRetryStrategy;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.HttpContext;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;
import org.brotli.dec.BrotliInputStream;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Xinhua X Yang
 */
@Configuration
public class RestTemplateConfig {

    @Bean("proxyRestTemplate")
    public RestTemplate restTemplate() throws Exception {
        // 1. 配置 SSL（信任所有证书）
        SSLContext sslContext = SSLContextBuilder
                .create()
                .loadTrustMaterial(new TrustSelfSignedStrategy())
                .build();

        // 2. 创建支持 GZIP 和 HTTPS 的 HttpClient
        CloseableHttpClient httpClient = HttpClients.custom()
                // 支持 HTTPS
                .setSSLContext(sslContext)
                // 忽略主机名验证
                .setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE)
                .build();
        // 3. 使用 HttpComponentsClientHttpRequestFactory（支持自动解压）
        HttpComponentsClientHttpRequestFactory factory =
                new HttpComponentsClientHttpRequestFactory(httpClient);
        // 4. 创建 RestTemplate
        RestTemplate restTemplate = new RestTemplate(factory);
        // 5. 添加 Accept-Encoding 拦截器（支持 GZIP/DEFLATE）
        restTemplate.setInterceptors(
                Collections.singletonList(new AcceptEncodingInterceptor())
        );
        return restTemplate;
    }
}    