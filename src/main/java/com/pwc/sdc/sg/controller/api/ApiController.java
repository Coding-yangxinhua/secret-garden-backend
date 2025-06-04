package com.pwc.sdc.sg.controller.api;

import com.pwc.sdc.sg.common.bean.Param;
import com.pwc.sdc.sg.service.handler.GameHandler;
import com.pwc.sdc.sg.service.handler.RequestHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

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

    @GetMapping("redirect")
    public String handleRedirect(
            HttpServletRequest request,
            @RequestHeader(name = "user-agent", required = false) String userAgent,
            @RequestHeader(name = "referer", required = false) String referer,
            @RequestParam("dev") String dev,
            @RequestParam("token") String token,
            @RequestParam("userId") String userId,
            @RequestParam("version") String version,
            @RequestParam("sign") String sign,
            @RequestParam("ti") Long ti,
            @RequestParam(value = "*", required = true) String data) {
        // 修改原请求
        List<Param> requestList = gameHandler.handle(userId, request.getRemoteAddr(), data);
        // 发送新请求
        HttpHeaders headers = new HttpHeaders();
        headers.add("user-agent", userAgent);
        headers.add("referer", referer);
        return requestHandler.request(headers, version, token, userId, requestList);
    }

}
