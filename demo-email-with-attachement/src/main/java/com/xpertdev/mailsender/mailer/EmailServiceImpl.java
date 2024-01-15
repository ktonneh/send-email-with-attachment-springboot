package com.xpertdev.mailsender.mailer;

import com.xpertdev.mailsender.data.EmailRequest;
import jakarta.mail.Message;
import jakarta.mail.internet.InternetAddress;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import java.io.File;
import java.util.Properties;
import java.util.logging.Logger;

@Service
public class EmailServiceImpl implements EmailService{

    Logger logger = Logger.getLogger(EmailServiceImpl.class.getName());

    @Override
    public void sendEmail(EmailRequest emailRequest) {
        try {
            //create an instance of smtp sender
            JavaMailSender mailSender = getJavaMailSender();
            logger.info("{{Instance of Message Sender Created}}");
            MimeMessagePreparator preparator = mimeMessage -> {
                mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(emailRequest.getRecipient()));
                mimeMessage.setFrom(new InternetAddress("demo@thexpertdev.com"));//have it in properties file
                mimeMessage.setSubject("Demo Email attachment");
                mimeMessage.setText(emailRequest.getMessage());
                logger.info("Files to attach");
                String attachment="uploads/names.csv";//path to the file
                try{
                    FileSystemResource file = new FileSystemResource(new File(attachment));
                    if (file.exists()){
                        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
                        helper.addAttachment(file.getFilename(), file);
                        helper.setText("", true);
                    } else {
                        logger.info("File Not Found");
                    }
                } catch (Exception ex){
                    ex.printStackTrace();
                }
            };

            logger.info("Ready to now send email with attachment");
            mailSender.send(preparator);

        } catch (Exception ex){
            ex.printStackTrace();
        }
    }
    private JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("yourdomain.com");
        mailSender.setPort(Integer.parseInt("465"));//confirm with hosting provider
        mailSender.setUsername("demo@yourdomain.com");
        mailSender.setPassword("demopass");
        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.ssl.enable", "true");//depends on mail server
        props.put("mail.debug", "true");
        return mailSender;
    }
}
