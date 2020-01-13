package rs.ac.uns.ftn.ktsnwt.service.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.ktsnwt.model.ConfirmationToken;

import java.util.concurrent.Future;

@Service
public class MailSenderService {

    @Autowired
    private JavaMailSender mailSender;


    @Async
    public Future<SimpleMailMessage> sendRegistrationMail(ConfirmationToken token) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setSubject("Account verification - KTSNWT");
        message.setFrom("KTSNWT-Tim05");
        message.setTo(token.getUser().getEmail());

        message.setText("Go to this page to activate your account http://localhost:4200/verify?token=" + token.getToken());

        // verifications
        if(token.getUser().getEmail() == null){
            message.setText("Error. Email cannot be null.");
            return new AsyncResult<>(message);
        }

        if(token.getToken() == null){
            message.setText("Error. Token cannot be null.");
            return new AsyncResult<>(message);
        }

        mailSender.send(message);
        return new AsyncResult<>(message);
    }
}
