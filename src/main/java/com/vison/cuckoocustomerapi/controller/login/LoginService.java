package com.vison.cuckoocustomerapi.controller.login;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.vison.cuckoocustomerapi.entity.Operator;
import com.vison.cuckoocustomerapi.entity.User;
import com.vison.cuckoocustomerapi.repository.OperatorRepository;
import com.vison.cuckoocustomerapi.repository.UserRepository;
import com.vison.cuckoocustomerapi.utils.Security;
import com.vison.wonfu.App;
import com.vison.wonfu.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.UUID;

@Service
public class LoginService {

    @Value("${app.security.salt}")
    private String salt;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OperatorRepository operatorRepository;

    @Autowired
    private StringRedisTemplate redisTemplate;

    public LoginResponse handLogin(LoginType loginType, LoginRequest loginRequest) throws LoginFailException {
        String pwd = loginRequest.getPwd();
        String password = "";
        LoginResponse loginResponse = new LoginResponse();
        Object loginObj = null;
        switch (loginType) {
            case USER:
                User user = userRepository.findByUsername(loginRequest.getUsername());
                if (user == null) {
                    throw new LoginFailException("账号不存在");
                }
                password = user.getPassword();
                loginResponse.setCover(user.getCover());
                loginResponse.setId(user.getId());
                loginResponse.setUsername(user.getUsername());
                loginResponse.setName(user.getNickname());
                loginObj = user;
                break;
            case OPERATOR:
                Operator operator = operatorRepository.findByUsername(loginRequest.getUsername());
                if (operator == null) {
                    throw new LoginFailException("账号不存在");
                }
                loginObj = operator;
                password = operator.getPassword();
                loginResponse.setCover(operator.getCover());
                loginResponse.setId(operator.getId());
                loginResponse.setUsername(operator.getUsername());
                loginResponse.setName(operator.getNickname());
                break;
        }
        if (Security.verifyPassword(password, pwd, this.salt)) {
            UUID tokenUuid = UUID.randomUUID();
            loginResponse.setToken(tokenUuid.toString());
            Gson gson = new GsonBuilder()
                    .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                    .create();
            Duration timeout = Duration.ofDays(30);
            String prefix = App.operatorPrefix;
            if (loginType.equals(LoginType.USER)) {
                prefix = App.userTokenPrefix;
            }
            redisTemplate.opsForValue().set(prefix + tokenUuid.toString()
                    , gson.toJson(loginObj), timeout);
            return loginResponse;
        } else {
            throw new LoginFailException("密码错误");
        }
    }
}
