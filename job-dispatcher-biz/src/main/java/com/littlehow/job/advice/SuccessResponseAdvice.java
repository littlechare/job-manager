package com.littlehow.job.advice;

import com.littlehow.job.vo.ResponseEntity;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@ControllerAdvice("com.littlehow.job.controller")
public class SuccessResponseAdvice implements ResponseBodyAdvice<Object> {
    private String SUCCESS_CODE = "0";
    private String SUCCESS = "success";
    @Override
    public boolean supports(MethodParameter methodParameter,
                            Class<? extends HttpMessageConverter<?>> aClass) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter,
                                  MediaType mediaType,
                                  Class<? extends HttpMessageConverter<?>> aClass,
                                  ServerHttpRequest serverHttpRequest,
                                  ServerHttpResponse serverHttpResponse) {
        if (o instanceof ResponseEntity) {
            return o;
        }
        ResponseEntity responseEntity = new ResponseEntity();
        responseEntity.setCode(SUCCESS_CODE);
        responseEntity.setMessage(SUCCESS);
        responseEntity.setData(o);
        return responseEntity;
    }
}
