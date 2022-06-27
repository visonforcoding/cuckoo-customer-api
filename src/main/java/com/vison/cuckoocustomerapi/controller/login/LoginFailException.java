package com.vison.cuckoocustomerapi.controller.login;

import com.vison.wonfu.BusinessException;

public class LoginFailException extends BusinessException {
    public LoginFailException(String message) {
        super(message);
    }
}
