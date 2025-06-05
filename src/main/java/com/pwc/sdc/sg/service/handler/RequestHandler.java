package com.pwc.sdc.sg.service.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.pwc.sdc.sg.common.SystemConstant;
import com.pwc.sdc.sg.common.bean.Param;
import com.pwc.sdc.sg.common.util.CryptUtil;
import lombok.SneakyThrows;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * @author Xinhua X Yang
 */
@Service
public class RequestHandler {
    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);

    @Resource(name = "proxyRestTemplate")
    private RestTemplate restTemplate;

    public String request(HttpHeaders headers, String version, String token, String userId, List<Param> requestList) {
        headers.set("Host", "wx.fthformal.com");
        String response = "{}";
        for (Param param : requestList) {
            response = request(headers, version, token, userId, param);
        }
        return response;
    }

    @SneakyThrows
    private String request(HttpHeaders headers, String version, String token, String userId, Param requestList) {
        // url参数构造
        String url = SystemConstant.URL + "?dev=master&*=" + requestList.getRequestArr().toJSONString() +
                "&token=" + token + "&userId=" + userId + "&version=" + version + "&sign=" + requestList.getSign() + "&ti=" + System.currentTimeMillis() ;
        log.info("请求url: {}", SystemConstant.URL + "?dev=master&*=" + CryptUtil.urlEncode(requestList.getRequestArr().toJSONString()) +
                "&token=" + token + "&userId=" + userId + "&version=" + version + "&sign=" + requestList.getSign() + "&ti=" + System.currentTimeMillis());

        // 创建请求实体，包含请求头和请求体
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);
        log.info("请求header: {}", JSON.toJSONString(headers));
        // 发送请求并获取响应
        ResponseEntity<byte[]> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                requestEntity,
                byte[].class
        );

        String responseBody = new String(response.getBody(), StandardCharsets.UTF_8);
        log.info("请求相应: {}", responseBody);
        return JSON.toJSONString(responseBody);
    }

//    @SneakyThrows
//    private String request(HttpHeaders headers, String version, String token, String userId, Param requestList) {
//        // url参数构造
//        String url = SystemConstant.URL + "?dev=master&*=" + CryptUtil.urlEncode(requestList.getRequestArr().toJSONString()) +
//                "&token=" + token + "&userId=" + userId + "&version=" + version + "&sign=" + requestList.getSign() + "&ti=" + System.currentTimeMillis();
//
//        // 创建HttpClient实例
//        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
//            // 创建HttpGet实例
//            HttpGet httpGet = new HttpGet(url);
//
//            // 设置请求头
//            if (headers != null) {
//                for (Map.Entry<String, List<String>> entry : headers.entrySet()) {
//                    String key = entry.getKey();
//                    List<String> values = entry.getValue();
//                    for (String value : values) {
//                        httpGet.setHeader(key, value);
//                    }
//                }
//            }
//            httpGet.setHeader("Accept", "application/json");
//            // 发送请求并获取响应
//            try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
//                // 获取响应状态码
//                int statusCode = response.getStatusLine().getStatusCode();
//                log.info("GET Response Code :: " + statusCode);
//                // 成功
//                if (statusCode == 200) {
//                    // 获取响应内容，显式指定编码为UTF-8
//                    String responseBody = EntityUtils.toString(response.getEntity(), "UTF-8");
//
//                    // 获取Content-Type
//                    String contentType = response.getFirstHeader("Content-Type").getValue();
//                    log.info("Content-Type: {}", contentType);
//
//                    // 根据Content-Type处理响应内容
//                    if (contentType.contains("application/json")) {
//                        // 解析为JSON对象
//                        // 示例：使用Jackson或Gson解析JSON
//                        log.info("JSON Response: {}", responseBody);
//                    } else if (contentType.contains("text/plain")) {
//                        // 直接作为字符串处理
//                        log.info("Text Response: {}", responseBody);
//                    }
//
//                    return responseBody;
//                } else {
//                    log.error("GET request not worked");
//                    return null;
//                }
//            }
//        } catch (IOException e) {
//            log.error("Error occurred during HTTP request", e);
//            throw e;
//        }
//    }
}
