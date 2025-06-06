package com.pwc.sdc.sg.common.config;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import java.io.IOException;

/**
 * @author Xinhua X Yang
 */
public class AcceptEncodingInterceptor implements ClientHttpRequestInterceptor {
    @Override
    public ClientHttpResponse intercept(
            HttpRequest request,
            byte[] body,
            ClientHttpRequestExecution execution
    ) throws IOException {
        // 添加 Accept-Encoding 头
        request.getHeaders().add(HttpHeaders.ACCEPT_ENCODING, "gzip, deflate");
        return execution.execute(request, body);
    }
}