package com.pwc.sdc.sg.controller.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Xinhua X Yang
 */
@RestController
@RequestMapping("api")
@Slf4j
public class ApiController {
    @Resource
    private RestTemplate restTemplate;

    @GetMapping("redirect")
    public String handleRedirect( @RequestParam("dev") String dev,
                                  @RequestParam("token") String token,
                                  @RequestParam("userId") Long userId, // 数值型参数建议用 Long 接收
                                  @RequestParam("version") String version,
                                  @RequestParam("sign") String sign,
                                  @RequestParam("ti") Long ti,
                                  @RequestParam(value = "*", required = true) String encodedJson) {
        // 创建请求头
        HttpHeaders headers = new HttpHeaders();
        headers.add("Host", "wx.fthformal.com");
        headers.add("Connection", "keep-alive");
        headers.add("xweb_xhr", "1");
        headers.add("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36 MicroMessenger/7.0.20.1781(0x6700143B) NetType/WIFI MiniProgramEnv/Windows WindowsWechat/WMPF WindowsWechat(0x63090c33)XWEB/13639");
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Accept", "*/*");
        headers.add("Sec-Fetch-Site", "cross-site");
        headers.add("Sec-Fetch-Mode", "cors");
        headers.add("Sec-Fetch-Dest", "empty");
        headers.add("Referer", "https://servicewechat.com/wx8479593d9223cb29/234/page-frame.html");
        headers.add("Accept-Encoding", "gzip, deflate, br");
        headers.add("Accept-Language", "zh-CN,zh;q=0.9");

        // 创建请求实体，包含请求头和请求体（如果有）
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        // 发送请求并获取响应
        String url = "https://wx.fthformal.com/mmhy.php?"; // 替换为实际接口URL
        ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.GET, // 根据实际需求设置HTTP方法
                requestEntity,
                String.class
        );
        return "";
    }

}
