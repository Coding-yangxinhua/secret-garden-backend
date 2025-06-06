package com.pwc.sdc.sg.service.handler;

import com.alibaba.fastjson.JSON;
import com.pwc.sdc.sg.common.SystemConstant;
import com.pwc.sdc.sg.common.bean.Param;
import com.pwc.sdc.sg.common.util.CryptUtil;
import lombok.SneakyThrows;
import org.brotli.dec.BrotliInputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
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

    private static final HttpHeaders DEFAULT_HEADER;

    static {
        DEFAULT_HEADER = new HttpHeaders();
        DEFAULT_HEADER.add("User-Agent", "Mozilla/5.0 (iPhone; CPU iPhone OS 18_5 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Mobile/15E148 MicroMessenger/8.0.59(0x18003b2e) NetType/4G Language/zh_CN");
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

        if ("br".equals(response.getHeaders().getFirst("Content-Encoding"))) {
            return decompressBrotli(response.getBody());
        }
        return JSON.toJSONString(new String(response.getBody(), StandardCharsets.UTF_8));
    }

    public String decompressBrotli(byte[] compressedData) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try (ByteArrayInputStream bis = new ByteArrayInputStream(compressedData);
             BrotliInputStream brotliIn = new BrotliInputStream(bis)) {

            byte[] buffer = new byte[1024];
            int len;
            while ((len = brotliIn.read(buffer)) != -1) {
                bos.write(buffer, 0, len);
            }
            return bos.toString("UTF-8");
        }
    }
}
