package org.example.service.impl;

import org.example.constants.ErrorMessage;
import org.example.constants.SuccessMessage;
import org.example.domain.Role;
import org.example.domain.User;
import org.example.dto.response.CaptchaResponse;
import org.example.dto.response.MessageResponse;
import org.example.dto.request.UserRequest;
import org.example.repository.UserRepository;
import org.example.service.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RegistrationServiceImpl implements RegistrationService {

    private final UserRepository userRepository;
    private final MailService mailService;
    private final PasswordEncoder passwordEncoder;
    private final RestTemplate restTemplate;
    private final ModelMapper modelMapper;

    @Value("${recaptcha.url}")
    private String captchaUrl;

    @Value("${recaptcha.secret}")
    private String secret;

    @Override
    @Transactional
    public MessageResponse registration(String captchaResponse, UserRequest userRequest) {
        if (userRequest.getPassword() != null && !userRequest.getPassword().equals(userRequest.getPassword2())) {
            return new MessageResponse("passwordError", ErrorMessage.PASSWORDS_DO_NOT_MATCH);
        }
        if (userRepository.findByEmail(userRequest.getEmail()) != null) {
            return new MessageResponse("emailError", ErrorMessage.EMAIL_IN_USE);
        }

        String url = String.format(captchaUrl, secret, captchaResponse);
        CaptchaResponse response = restTemplate.postForObject(url, Collections.emptyList(), CaptchaResponse.class);
        if (!response.isSuccess()) {
            return new MessageResponse("captchaError", ErrorMessage.CAPTCHA_ERROR);
        }
        User user = modelMapper.map(userRequest, User.class);
        user.setActive(false);
        user.setRoles(Collections.singleton(Role.USER));
        user.setActivationCode(UUID.randomUUID().toString());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("firstName", user.getFirstName());
        attributes.put("activationCode", "/registration/activate/" + user.getActivationCode());
        mailService.sendMessageHtml(user.getEmail(), "Activation code", "registration-template", attributes);
        return new MessageResponse("alert-success", SuccessMessage.ACTIVATION_CODE_SEND);
    }

    @Override
    @Transactional
    public MessageResponse activateEmailCode(String code) {
        User user = userRepository.findByActivationCode(code);

        if (user == null) {
            return new MessageResponse("alert-danger", ErrorMessage.ACTIVATION_CODE_NOT_FOUND);
        }
        user.setActivationCode(null);
        user.setActive(true);
        userRepository.save(user);
        return new MessageResponse("alert-success", SuccessMessage.USER_ACTIVATED);
    }
}