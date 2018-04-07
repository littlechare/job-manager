package com.littlehow.job.config;

import com.littlehow.job.interceptor.BusinessInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class InterceptorConfig extends WebMvcConfigurerAdapter {
    @Autowired
    private BusinessInterceptor businessInterceptor;
    /**
     * 该方法用于注册拦截器
     * 可注册多个拦截器，多个拦截器组成一个拦截器链
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(businessInterceptor).addPathPatterns("/task/api/**");
        super.addInterceptors(registry);
    }
}
