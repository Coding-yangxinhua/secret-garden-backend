package com.pwc.sdc.sg.service.handler;

import com.alibaba.fastjson.JSONArray;
import com.pwc.sdc.sg.common.bean.Param;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * @author Xinhua X Yang
 */
@Service
public class RequestHandler {
    private static final String URL = "https://wx.fthformal.com/mmhy.php";

    private static final HttpHeaders DEFAULT_HEADER;

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
    }

    @Resource
    private RestTemplate restTemplate;

    public String request(HttpHeaders headers, String version, String token, String userId, List<Param> requestList) {
        // header构造
        headers.addAll(DEFAULT_HEADER);
        String response = "{}";
        for (Param param : requestList) {
            response = request(headers, version, token, userId, param);
        }
        return response;
    }

    private String request(HttpHeaders headers, String version, String token, String userId, Param requestList) {
        // url参数构造
        String url = URL + "?dev=master&*=" + requestList.getRequestArr().toJSONString() + "&token=" + token + "&userId=" + userId + "&version=" + version + "&sign=" + requestList.getSign() + "&ti=" + System.currentTimeMillis();
        // 创建请求实体，包含请求头和请求体
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);
        // 发送请求并获取响应
        ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.GET, // 根据实际需求设置HTTP方法
                requestEntity,
                String.class
        );
        return JSONArray.toJSONString(response);
    }
}
