package com.xpertdev.mailsender.mailer;

import com.xpertdev.mailsender.data.EmailRequest;

public interface EmailService {
    void sendEmail(EmailRequest emailRequest);
}
