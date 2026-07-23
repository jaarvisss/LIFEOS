package com.lifeos.infrastructure.email;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Enterprise Implementation of EmailService.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    @Override
    public void sendEmail(String toAddress, String subject, String bodyContent) {
        log.info("Sending email to: {} | Subject: {}", toAddress, subject);
    }

    @Override
    public void sendVerificationEmail(String recipientEmail, String token) {
        String body = "Welcome to LifeOS! Verify your account with code: " + token;
        sendEmail(recipientEmail, "Verify Your LifeOS Account", body);
    }

    @Override
    public void sendPasswordResetEmail(String recipientEmail, String token) {
        String body = "Reset your LifeOS password using code: " + token;
        sendEmail(recipientEmail, "LifeOS Password Reset Request", body);
    }
}
