package com.vison.cuckoocustomerapi.controller.login;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vison.cuckoocustomerapi.entity.User;
import com.vison.cuckoocustomerapi.repository.UserRepository;
import com.vison.cuckoocustomerapi.utils.Security;
import com.vison.wonfu.App;
import com.vison.wonfu.Response;
import com.vison.wonfu.ResponseCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.Duration;
import java.util.Locale;
import java.util.UUID;

@RestController
public class LoginController {

    @Value("${app.security.salt}")
    private String salt;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LoginService loginService;

    @RequestMapping(path = "/hand-login", method = RequestMethod.POST)
    public Response loginAction(@Valid @RequestBody LoginRequest loginRequest) {
        LoginType loginType = LoginType.valueOf(loginRequest.getType().toUpperCase(Locale.ROOT));
        try {
            LoginResponse loginResponse = loginService.handLogin(loginType, loginRequest);
            return new Response(ResponseCode.success, "登录成功", loginResponse);
        } catch (LoginFailException e) {
            return new Response(ResponseCode.loginFail, e.getMessage());
        }
    }
}
