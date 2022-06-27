package com.vison.wonfu.aop;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.vison.wonfu.configure.MultiReadHttpServletRequest;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.IOUtils;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;
import org.springframework.web.util.WebUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * 记录客户端请求信息
 */

@Log4j2
public class LogClientFilter implements Filter {
    public void init(FilterConfig config) throws ServletException {
        log.info("client request log init...");
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode rootNode = mapper.createObjectNode();
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        ServletRequest requestWrapper = new ContentCachingRequestWrapper(httpServletRequest);
        rootNode.put("uri", httpServletRequest.getRequestURI());
        rootNode.put("clientIp", httpServletRequest.getRemoteAddr());
        String method = httpServletRequest.getMethod();
        rootNode.put("method", method);
        chain.doFilter(requestWrapper, response);
        String content = IOUtils.toString(requestWrapper.getInputStream());
        if (method.equals("GET") || method.equals("DELETE")) {
            rootNode.put("request", httpServletRequest.getQueryString());
        } else {
            rootNode.put("request", content);
        }
        log.info("client request..." + rootNode);


    }


}
