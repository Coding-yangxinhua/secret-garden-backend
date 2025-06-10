package com.pwc.sdc.sg.controller.api;

import com.alibaba.fastjson.JSON;
import com.pwc.sdc.sg.common.SystemConstant;
import com.pwc.sdc.sg.common.bean.Param;
import com.pwc.sdc.sg.service.handler.GameHandler;
import com.pwc.sdc.sg.service.handler.RequestHandler;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.transaction.annotation.Transactional;
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
    @Transactional
    public String handleRedirect(
            @RequestHeader HttpHeaders headers,
            HttpServletRequest request,
            @RequestParam("token") String token,
            @RequestParam("userId") String userId,
            @RequestParam("version") String version,
            @RequestParam(value = "*", required = true) String data) {
        // 修改原请求
        List<Param> requestList = gameHandler.handle(userId, request.getRemoteAddr(), data);
        // 发送新请求
        return requestHandler.request(headers, version, token, userId, requestList);
    }
}
