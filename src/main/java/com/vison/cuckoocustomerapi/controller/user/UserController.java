package com.vison.cuckoocustomerapi.controller.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.xml.bind.v2.schemagen.xmlschema.Appinfo;
import com.vison.cuckoocustomerapi.controller.login.LoginRequest;
import com.vison.cuckoocustomerapi.controller.login.LoginResponse;
import com.vison.cuckoocustomerapi.entity.User;
import com.vison.cuckoocustomerapi.repository.UserRepository;
import com.vison.cuckoocustomerapi.utils.Security;
import com.vison.wonfu.App;
import com.vison.wonfu.Response;
import com.vison.wonfu.ResponseCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Value("${app.security.salt}")
    private String salt;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @GetMapping(path = "/login-out")
    public Response loginOut(HttpSession session) {
        return new Response(0, "");
    }

    @PostMapping(path = "/register")
    public Response register(@Valid @RequestBody User user) {
        user.setPassword(Security.encrypt(user.getPassword(), salt));
        UUID tokenUuid = UUID.randomUUID();
        user.setChatId(tokenUuid.toString());
        userRepository.save(user);
        return new Response(ResponseCode.success, "注册成功");
    }

    @GetMapping(path = "/detail")
    public Response detail(HttpServletRequest request) {
        String token = request.getParameter("token");
        String userKey = App.userTokenPrefix+token;
        String userJson = redisTemplate.opsForValue().get(userKey);
        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();
        User user = gson.fromJson(userJson,User.class);
        if (user != null) {
            return new Response(ResponseCode.success, "获取成功", user);

        }

        return new Response(ResponseCode.noDataFound, "获取失败");
    }

}
