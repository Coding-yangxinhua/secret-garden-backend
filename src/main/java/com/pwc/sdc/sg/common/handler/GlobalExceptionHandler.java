package com.pwc.sdc.sg.common.handler;

import com.pwc.sdc.sg.common.bean.BusinessException;
import com.pwc.sdc.sg.common.bean.ResponseEntity;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
/**
 * 全局统一的异常处理，简单的配置下，根据自己的业务要求详细配置
 * @author Xinhua X Yang
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {


    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<?> handleBusinessException(final BusinessException e) {
        return new ResponseEntity<>(HttpStatus.FORBIDDEN, e.getMessage());
    }


}