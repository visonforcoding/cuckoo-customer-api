package com.vison.cuckoocustomerapi.controller.login;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum LoginType {
    @JsonProperty("user")
    USER,
    @JsonProperty("operator")
    OPERATOR
}
