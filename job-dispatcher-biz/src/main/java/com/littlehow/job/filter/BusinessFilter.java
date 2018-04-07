package com.littlehow.job.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter(filterName = "businessFilter", urlPatterns = "/task/manager/**")
public class BusinessFilter implements Filter {
    private String businessUriPrefix = "/task/manager";
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        //do init
        System.out.println("=================>init");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        String uri = request.getRequestURI();
        if (uri.startsWith(businessUriPrefix)) {
            System.out.println(uri);
            //dofilter
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        //do destroy
    }
}
