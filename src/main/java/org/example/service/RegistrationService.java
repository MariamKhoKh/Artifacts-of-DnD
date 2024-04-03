package org.example.service;

import org.example.dto.response.MessageResponse;
import org.example.dto.request.UserRequest;

public interface RegistrationService {

    MessageResponse registration(String captchaResponse, UserRequest user);

    MessageResponse activateEmailCode(String code);
}
