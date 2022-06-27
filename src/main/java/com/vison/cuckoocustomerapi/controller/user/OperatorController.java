package com.vison.cuckoocustomerapi.controller.user;

import com.vison.cuckoocustomerapi.entity.Operator;
import com.vison.cuckoocustomerapi.entity.User;
import com.vison.cuckoocustomerapi.repository.OperatorRepository;
import com.vison.cuckoocustomerapi.repository.UserRepository;
import com.vison.cuckoocustomerapi.utils.Security;
import com.vison.wonfu.Response;
import com.vison.wonfu.ResponseCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/operator")
public class OperatorController {
    @Value("${app.security.salt}")
    private String salt;

    @Autowired
    private OperatorRepository operatorRepository;

    @PostMapping(path = "/add")
    public Response create(@Valid @RequestBody Operator operator) {
        operator.setPassword(Security.encrypt(operator.getPassword(), salt));
        UUID tokenUuid = UUID.randomUUID();
        operator.setChatId(tokenUuid.toString());
        operatorRepository.save(operator);
        return new Response(ResponseCode.success, "添加成功");
    }

}
