package com.pwc.sdc.sg.common.filter;

import com.alibaba.fastjson.JSON;
import com.pwc.sdc.sg.common.SystemConstant;
import com.pwc.sdc.sg.common.bean.Auth;
import com.pwc.sdc.sg.common.bean.BusinessException;
import com.pwc.sdc.sg.model.dto.UserDto;
import com.pwc.sdc.sg.service.UserService;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author 42268
 */
@Component
public class LoginFilter implements Filter {
    @Resource(name = "stringRedisTemplate")
    private StringRedisTemplate redisTemplate;

    @Resource
    private UserService userService;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        // 查询请求ip
        String remoteAddr = servletRequest.getRemoteAddr();
        UserDto userDto = null;
        // 接口地址，从参数中获取openId
        if (request.getRequestURI().contains("api")) {
            String openId = request.getParameter("userId");
            if (openId != null) {
                userDto = userService.queryOrCreateUser(openId, remoteAddr);
            }
        } else {
            // 查询缓存是否有用户信息
            String userIpKey = SystemConstant.USER_IP_PREFIX + remoteAddr;
            String userStr = redisTemplate.opsForValue().get(userIpKey);
            if (userStr == null || userStr.isEmpty()) {
                throw new BusinessException(SystemConstant.LOGIN_EXPIRED);
            }
            userDto = JSON.parseObject(userStr, UserDto.class);
        }
        // 设置到本地线程中
        Auth.setUser(userDto);
        // 放行
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
