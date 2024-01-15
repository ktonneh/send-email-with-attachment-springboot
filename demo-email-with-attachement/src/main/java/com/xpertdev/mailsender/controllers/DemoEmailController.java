package com.xpertdev.mailsender.controllers;


import com.xpertdev.mailsender.data.EmailRequest;
import com.xpertdev.mailsender.mailer.EmailService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class DemoEmailController {

    private EmailService emailService;

    public DemoEmailController(final EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/sendmail")
    public ResponseEntity<?> create(@RequestBody EmailRequest emailRequest){
        this.emailService.sendEmail(emailRequest);
        return new ResponseEntity<>("Email sent", HttpStatus.OK);
    }
}
