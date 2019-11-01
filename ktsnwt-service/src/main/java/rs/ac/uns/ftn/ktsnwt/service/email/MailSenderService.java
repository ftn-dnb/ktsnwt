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

        // @TODO: Kada se implementira stranica za verifikaciju na frontendu dodati putanju do nje (ne zaboravi token)
        message.setText("This will be changed later. Here is your token: " + token.getToken());

        mailSender.send(message);
    }
}
