package com.flowable.usermanagement.resource;

import com.flowable.usermanagement.entity.User;
import com.flowable.usermanagement.model.LoginForm;
import com.flowable.usermanagement.model.UnlockAccForm;
import com.flowable.usermanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserResource {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public String registerUser(@RequestBody User user) {
        return userService.registerUser(user);
    }

    @PostMapping("/unlock-account")
    public String unlockAccount(@RequestBody UnlockAccForm unlockAccForm) {
        return userService.unlockAccount(unlockAccForm);
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginForm loginForm) {
        return userService.login(loginForm);
    }

    @GetMapping("/forgot-password")
    public String forgotPassword(@RequestParam String email) {
        return userService.forgotPwd(email);
    }
}
