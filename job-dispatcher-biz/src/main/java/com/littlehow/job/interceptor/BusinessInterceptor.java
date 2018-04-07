package com.littlehow.job.interceptor;

import com.littlehow.job.vo.ArgumentsException;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.littlehow.job.interceptor.BusinessContext.*;

@Component
public class BusinessInterceptor implements HandlerInterceptor {
    private final static String message = "业务编号长度必须在4到32之间:";
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        String businessId = httpServletRequest.getHeader("bid");
        if (businessId == null || businessId.getBytes().length < 4 || businessId.getBytes().length > 32) {
            throw new ArgumentsException(message + businessId);
        }
        //设置业务标识的上下文
        setBusinessId(businessId);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        //删除设置的上下文
        removeBusinessId();
    }
}
