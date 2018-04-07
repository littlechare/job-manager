package com.littlehow.job.advice;

import com.littlehow.job.manager.pojo.TaskManagerException;
import com.littlehow.job.vo.ArgumentsException;
import com.littlehow.job.vo.ResponseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 全局异常处理器
 * littlehow 2018/4/6
 */
@ControllerAdvice("com.littlehow.job.controller")
public class ExceptionAdvice {
    private final static Logger log = LoggerFactory.getLogger(ExceptionInfo.class);
    @ExceptionHandler
    @ResponseBody
    public ResponseEntity getExceptionEntity(Throwable t, HttpServletRequest request) {
        ResponseEntity responseEntity = new ResponseEntity();
        responseEntity.setMessage(t.getMessage());
        if (t instanceof TaskManagerException) {
            responseEntity.setCode(((TaskManagerException)t).getCode());
        } else if (t instanceof MethodArgumentNotValidException
                || t instanceof ArgumentsException) {
            responseEntity.setCode(ExceptionInfo.PARAM_ERROR.code);
        } else {
            responseEntity.setCode(ExceptionInfo.SYSTEM_ERROR.code);
            log.error(request.getRequestURI(), t);
        }
        return responseEntity;
    }

    enum ExceptionInfo {
        SYSTEM_ERROR("系统异常", "1"),
        PARAM_ERROR("参数异常", "2")
        ;
        public final String name;
        public final String code;
        ExceptionInfo(String name, String code) {
            this.name = name;
            this.code = code;
        }
    }
}
