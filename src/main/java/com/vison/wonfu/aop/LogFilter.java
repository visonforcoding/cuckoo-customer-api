package com.vison.wonfu.aop;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.vison.wonfu.App;
import com.vison.wonfu.configure.ContentCachingRequestWrapper;
import com.vison.wonfu.configure.MultiReadHttpServletRequest;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.IOUtils;
import org.slf4j.MDC;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.UUID;

@Log4j2
public class LogFilter implements Filter {
    public void init(FilterConfig config) throws ServletException {
        log.info("log filter...");
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        App.count.set(0);
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode rootNode = mapper.createObjectNode();
        String uuid = UUID.randomUUID().toString();
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        MDC.put("trace-id",uuid);
        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper((HttpServletRequest) request);
        String body = IOUtils.toString(requestWrapper.getBody(),request.getCharacterEncoding());
        rootNode.put("uri", httpServletRequest.getRequestURI());
        rootNode.put("clientIp", httpServletRequest.getRemoteAddr());
        String method = httpServletRequest.getMethod();
        if (method.equals("GET") || method.equals("DELETE")) {
            rootNode.put("request", httpServletRequest.getQueryString());
        } else {
            rootNode.put("request",body);
        }
        log.info(rootNode.toString());
        chain.doFilter(requestWrapper, response);
    }
}
