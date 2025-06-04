package com.pwc.sdc.sg.service.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.pwc.sdc.sg.common.bean.Param;
import com.pwc.sdc.sg.common.util.CryptUtil;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.net.URLEncoder;
import java.util.*;

/**
 * @author Xinhua X Yang
 */
@Service
public class RequestHandler {
    private static final String URL = "https://wx.fthformal.com/mmhy.php";

    private static final HttpHeaders DEFAULT_HEADER;
    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);

    static {
        DEFAULT_HEADER = new HttpHeaders();
        DEFAULT_HEADER.add("Host", "wx.fthformal.com");
        DEFAULT_HEADER.add("Connection", "keep-alive");
        DEFAULT_HEADER.add("xweb_xhr", "1");
        DEFAULT_HEADER.setContentType(MediaType.APPLICATION_JSON);
        DEFAULT_HEADER.add("Accept", "*/*");
        DEFAULT_HEADER.add("Sec-Fetch-Site", "cross-site");
        DEFAULT_HEADER.add("Sec-Fetch-Mode", "cors");
        DEFAULT_HEADER.add("Sec-Fetch-Dest", "empty");
        DEFAULT_HEADER.add("Accept-Encoding", "gzip, deflate, br");
        DEFAULT_HEADER.add("Accept-Language", "zh-CN,zh;q=0.9");
        DEFAULT_HEADER.set("Accept-Charset", "UTF-8");
    }

    @Resource
    private RestTemplate restTemplate;

    public String request(HttpHeaders headers, String version, String token, String userId, List<Param> requestList) {
        // header构造
        headers.addAll(DEFAULT_HEADER);
        headers.setAccept(Arrays.asList(
                new MediaType("application", "json", java.nio.charset.StandardCharsets.UTF_8),
                new MediaType("text", "plain", java.nio.charset.StandardCharsets.UTF_8)
        ));
        String response = "{}";
        for (Param param : requestList) {
            response = request(headers, version, token, userId, param);
        }
        return response;
    }

    @SneakyThrows
    private String request(HttpHeaders headers, String version, String token, String userId, Param requestList) {
        // url参数构造
        String url = URL + "?dev=master&*=" + requestList.getRequestArr().toJSONString() +
                "&token=" + token + "&userId=" + userId + "&version=" + version + "&sign=" + requestList.getSign() + "&ti=" + System.currentTimeMillis() +
                "&key=";
        // 创建请求实体，包含请求头和请求体
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);
        log.info("请求header: {}", JSON.toJSONString(headers));
        log.info("请求url: {}", url);
        // 发送请求并获取响应
        ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                requestEntity,
                String.class
        );
        log.info("请求相应: {}", JSON.toJSONString(response));
        return JSON.toJSONString(response.getBody());
    }
}
