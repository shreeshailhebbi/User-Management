package com.flowable.usermanagement.model;

import lombok.Data;

@Data
public class LoginForm {
    private String email;
    private String password;
}
