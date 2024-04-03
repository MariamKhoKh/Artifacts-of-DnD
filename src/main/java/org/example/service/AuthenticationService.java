package org.example.service;

import org.example.dto.request.PasswordResetRequest;
import org.example.dto.response.MessageResponse;

public interface AuthenticationService {

    MessageResponse sendPasswordResetCode(String email);

    String getEmailByPasswordResetCode(String code);

    MessageResponse resetPassword(PasswordResetRequest request);
}
