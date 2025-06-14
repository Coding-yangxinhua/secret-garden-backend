package com.pwc.sdc.sg.controller.api;

import com.pwc.sdc.sg.common.SystemConstant;
import com.pwc.sdc.sg.common.bean.Param;
import com.pwc.sdc.sg.common.bean.ResponseEntity;
import com.pwc.sdc.sg.service.CardCodeService;
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
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

/**
 * @author Xinhua X Yang
 */
@RestController
@RequestMapping("manager")
@Slf4j
public class ManagerController {
    @Resource
    private CardCodeService cardCodeService;
    @GetMapping("cardCodeGenerator")
    public ResponseEntity<?> cardCodeGenerator(Long subscribeId, Integer validUses, Integer count) {
        List<String> generator = cardCodeService.generator(subscribeId, validUses, count);
        return ResponseEntity.ok(generator);
    }
}
