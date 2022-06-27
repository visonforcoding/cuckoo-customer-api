package com.vison.wonfu.configure;

import com.vison.wonfu.aop.LogClientFilter;
import com.vison.wonfu.aop.LogFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
@Slf4j
public class FilterConfigure {

    @Bean
    public FilterRegistrationBean<LogFilter> logFilter() {
        FilterRegistrationBean<LogFilter> bean = new FilterRegistrationBean<LogFilter>();
        bean.setFilter(new LogFilter());
        bean.addUrlPatterns("/*");//过滤所有路径
        bean.setOrder(1);
        return bean;
    }

//    @Bean
    public OncePerRequestFilter contentCachingRequestFilter() {
        return new OncePerRequestFilter() {
            @Override
            protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response, final FilterChain filterChain)
                    throws ServletException, IOException {
                ContentCachingRequestWrapper wrappedRequest = new ContentCachingRequestWrapper(request);
                ContentCachingResponseWrapper wrappedResponse = new ContentCachingResponseWrapper(response);
                log.info("http request:{}", new String(wrappedRequest.getContentAsByteArray()));
                filterChain.doFilter(wrappedRequest, wrappedResponse);
                log.info("http response:{}", new String(wrappedResponse.getContentAsByteArray()));
                wrappedResponse.copyBodyToResponse();
            }
        };
    }

//    @Bean
    public FilterRegistrationBean<LogClientFilter> logClientFilter() {
        FilterRegistrationBean<LogClientFilter> bean = new FilterRegistrationBean<LogClientFilter>();
        bean.setFilter(new LogClientFilter());
        bean.addUrlPatterns("/*");
        bean.setOrder(3);
        return bean;
    }

    //    @Bean
    public CustomizedRequestLoggingFilter logInitFilter() {
        log.info("logInitFilter...");
        CustomizedRequestLoggingFilter filter
                = new CustomizedRequestLoggingFilter();
        filter.setIncludeClientInfo(true);
        filter.setIncludeQueryString(true);
        filter.setIncludePayload(true);
        filter.setMaxPayloadLength(2048);
        filter.setIncludeHeaders(false);
        filter.setAfterMessagePrefix("REQUEST DATA : ");
        return filter;
    }


}
