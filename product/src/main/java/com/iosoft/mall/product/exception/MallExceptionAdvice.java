package com.iosoft.mall.product.exception;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice(basePackages = "com.iosoft.mall.product.controller")
public class MallExceptionAdvice {

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public String argumentNotValidException(MethodArgumentNotValidException excetion) {
        Map<String, String> map = new HashMap<>();
        BindingResult result = excetion.getBindingResult();
        result.getFieldErrors().forEach((item)->{
            String message = item.getDefaultMessage();
            String field = item.getField();
            map.put(field, message);
        });
        log.error("异常信息{}",excetion);
        log.error("数据校验出现问题:{},异常类型{}",excetion.getMessage(),excetion.getClass());

        return "forward:/error";
        }

    @ExceptionHandler(Throwable.class)
    public String systemException(Exception e, HttpServletRequest request) {

        log.error("异常信息{}",e );
        Map<String,Object> myErrorAttributes = new HashMap<>();
        myErrorAttributes.put("code", "900001");
        myErrorAttributes.put("message", "data not found");
        request.setAttribute("javax.servlet.error.status_code", 500);
        request.setAttribute("myErrorAttributes", myErrorAttributes);
        return "forward:/error";

    }
}
