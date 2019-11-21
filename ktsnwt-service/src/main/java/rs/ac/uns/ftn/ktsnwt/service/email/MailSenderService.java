package rs.ac.uns.ftn.ktsnwt.service.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.ktsnwt.model.ConfirmationToken;

@Service
public class MailSenderService {

    @Autowired
    private JavaMailSender mailSender;


    @Async
    public void sendRegistrationMail(ConfirmationToken token) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setSubject("Account verification - KTSNWT");
        message.setFrom("KTSNWT-Tim05");
        message.setTo(token.getUser().getEmail());

        message.setText("Go to this page to activate your account http://localhost:4200/verify?token=" + token.getToken());

        mailSender.send(message);
    }
}
