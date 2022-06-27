package com.vison.cuckoocustomerapi;

import com.vison.wonfu.aop.ControllerAdvice;
import com.vison.wonfu.aop.CustomRequestBodyAdviceAdapter;
import com.vison.wonfu.configure.FilterConfigure;
import com.vison.wonfu.configure.RedisConfig;
import com.vison.wonfu.configure.WebConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.TimeZone;

@SpringBootApplication
@EnableJpaAuditing
@Import({WebConfig.class, ControllerAdvice.class, FilterConfigure.class})
public class CuckooCustomerApiApplication {

    public static void main(String[] args) {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Shanghai"));
        SpringApplication.run(CuckooCustomerApiApplication.class, args);

    }

}
