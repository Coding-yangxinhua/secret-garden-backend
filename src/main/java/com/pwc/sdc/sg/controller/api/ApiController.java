package com.pwc.sdc.sg.controller.api;

import com.alibaba.fastjson.JSON;
import com.pwc.sdc.sg.common.SystemConstant;
import com.pwc.sdc.sg.common.bean.Param;
import com.pwc.sdc.sg.service.handler.GameHandler;
import com.pwc.sdc.sg.service.handler.RequestHandler;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.IOException;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author Xinhua X Yang
 */
@RestController
@RequestMapping("api")
@Slf4j
public class ApiController {
    @Resource
    private GameHandler gameHandler;

    @Resource
    private RequestHandler requestHandler;

    @GetMapping("modify")
    public String handleRedirect(
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam Map<String, String> allParams,
            @RequestHeader(name = "user-agent", required = false) String userAgent,
            @RequestHeader(name = "referer", required = false) String referer,
            @RequestParam("token") String token,
            @RequestParam("userId") String userId,
            @RequestParam("version") String version,
            @RequestParam(value = "*", required = true) String data) {
        // 修改原请求
        List<Param> requestList = gameHandler.handle(userId, request.getRemoteAddr(), data);
        // 发送新请求
        HttpHeaders headers = new HttpHeaders();
        headers.add("User-Agent", userAgent);
        headers.add("referer", Optional.ofNullable(referer).orElse("https://servicewechat.com/wx8479593d9223cb29/234/page-frame.html"));
        return requestHandler.request(headers, version, token, userId, requestList);
    }

    @SneakyThrows
    public boolean redirect(String url, @RequestParam Map<String, String> allParams, HttpServletRequest request, HttpServletResponse response) {
        if (url.contains("harvestNew")) {
            return false;
        }
        // 构造目标 URL
        StringBuilder targetUrl = new StringBuilder(SystemConstant.URL);
        if (!allParams.isEmpty()) {
            targetUrl.append("?");
            allParams.forEach((key, value) -> targetUrl.append(key).append("=").append(value).append("&"));
            targetUrl.setLength(targetUrl.length() - 1); // 去掉最后一个 "&"
        }
        // 包装响应对象
        HttpServletResponseWrapper responseWrapper = new HttpServletResponseWrapper(response);
        // 添加 Header
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String name = headerNames.nextElement();
            responseWrapper.addHeader(name, request.getHeader(name));
        }
        // 重定向
        responseWrapper.sendRedirect(targetUrl.toString());
        return true;
    }
}
