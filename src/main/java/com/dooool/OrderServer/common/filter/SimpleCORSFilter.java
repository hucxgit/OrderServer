package com.dooool.OrderServer.common.filter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 支持AJAX跨域请求
 * @author
 * @time 2016年12月17日
 */
@Component
public class SimpleCORSFilter implements Filter {
    private static Log logger = LogFactory.getLog(SimpleCORSFilter.class);

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        logger.info("SimpleCORSFilter doFilter");
        HttpServletResponse response = (HttpServletResponse) res;
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");//支持post get options delete
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with");
        chain.doFilter(req, res);
    }
    public void init(FilterConfig filterConfig) {
        logger.info("SimpleCORSFilter doFilter init");
    }
    public void destroy() {
        logger.info("SimpleCORSFilter doFilter destroy");
    }
}