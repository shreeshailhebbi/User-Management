package com.flowable.usermanagement.service;

import com.flowable.usermanagement.entity.User;
import com.flowable.usermanagement.model.LoginForm;
import com.flowable.usermanagement.model.UnlockAccForm;

import java.util.Map;

public interface UserService {
    public String checkEmail(String email);

    public Map<Integer, String> getCountries();

    public Map<Integer, String> getStates(Integer countryId);

    public Map<Integer, String> getCities(Integer stateId);

    public String registerUser(User user);

    public String unlockAccount(UnlockAccForm accForm);

    public String login(LoginForm loginForm);

    public String forgotPwd(String email);
}
