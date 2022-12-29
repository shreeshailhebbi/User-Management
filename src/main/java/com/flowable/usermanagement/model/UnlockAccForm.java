package com.flowable.usermanagement.model;

import lombok.Data;

@Data
public class UnlockAccForm {
    private String email;
    private String tempPassword;
    private String password;
}
