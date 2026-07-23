package com.lifeos.infrastructure.email;

/**
 * Interface for SMTP Email Services.
 */
public interface EmailService {

    void sendEmail(String toAddress, String subject, String bodyContent);

    void sendVerificationEmail(String recipientEmail, String token);

    void sendPasswordResetEmail(String recipientEmail, String token);
}
