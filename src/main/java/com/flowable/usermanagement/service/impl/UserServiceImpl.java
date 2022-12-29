package com.flowable.usermanagement.service.impl;

import com.flowable.usermanagement.entity.City;
import com.flowable.usermanagement.entity.Country;
import com.flowable.usermanagement.entity.State;
import com.flowable.usermanagement.entity.User;
import com.flowable.usermanagement.model.LoginForm;
import com.flowable.usermanagement.model.UnlockAccForm;
import com.flowable.usermanagement.repository.CityRepository;
import com.flowable.usermanagement.repository.CountryRepository;
import com.flowable.usermanagement.repository.StateRepository;
import com.flowable.usermanagement.repository.UserRepository;
import com.flowable.usermanagement.service.UserService;
import com.flowable.usermanagement.util.UserUtil;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private StateRepository stateRepository;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String sender;

    private static final String LOCKED = "Locked";
    private static final String UNLOCKED = "Unlocked";

    @Override
    public String checkEmail(String email) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isPresent()) {
            return "Email validated successfully";
        }
        return "given email not present";
    }

    @Override
    public Map<Integer, String> getCountries() {
        List<Country> countryList = countryRepository.findAll();
        return countryList.stream().collect(Collectors.toMap(Country::getId, Country::getName));
    }

    @Override
    public Map<Integer, String> getStates(Integer countryId) {
        List<State> stateList = stateRepository.findAllByCountry(countryId);
        return stateList.stream().collect(Collectors.toMap(State::getId, State::getName));
    }

    @Override
    public Map<Integer, String> getCities(Integer stateId) {
        List<City> cityList = cityRepository.findAllByState(stateId);
        return cityList.stream().collect(Collectors.toMap(City::getId, City::getName));
    }

    @Override
    @Transactional
    public String registerUser(User user) {
        String password = UserUtil.generateSecureRandomPassword();
        user.setPassword(password);
        user.setStatus(LOCKED);
        User response = userRepository.save(user);
        if (response != null) {
            sendEmail(response.getEmail(), "Unlock IES Account", UserUtil.getUnlockEmailBody(response));
            return "Please check your email to unlock account!";
        }
        return "Registration Failed!";
    }

    private void sendEmail(String mailId, String subject, String body) {
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();

            mailMessage.setFrom(sender);
            mailMessage.setTo(mailId);
            mailMessage.setText(body);
            mailMessage.setSubject(subject);

            javaMailSender.send(mailMessage);
        } catch (MailException e) {
            e.printStackTrace();
        }
    }

    @Override
    @SneakyThrows
    public String unlockAccount(UnlockAccForm accForm) {
        Optional<User> optionalUser = userRepository.findByEmailAndPassword(accForm.getEmail(), accForm.getTempPassword());
        if (optionalUser.isPresent()) {
            User response = optionalUser.get();
            response.setPassword(accForm.getPassword());
            response.setStatus(UNLOCKED);
            userRepository.save(response);
            return "Account unlocked ,please proceed with login";
        }
        return "Account unlock failed, Given Password is wrong!";
    }

    @Override
    public String login(LoginForm loginForm) {
        Optional<User> optionalUser = userRepository.findByEmailAndPassword(loginForm.getEmail(), loginForm.getPassword());
        if (optionalUser.isPresent()) {
            User response = optionalUser.get();
            if (response.getStatus().equalsIgnoreCase(UNLOCKED)) {
                return "Login Successful!";
            }
            return "Your Account is locked ,please proceed with unlocking";
        }
        return "Invalid credentials";
    }

    @Override
    @SneakyThrows
    public String forgotPwd(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new Exception("Given email is not registered"));
        sendEmail(email, "Password restored Successfully!", UserUtil.getForgoPasswordEmailBody(user));
        return "Password has been sent to given mail";
    }
}
