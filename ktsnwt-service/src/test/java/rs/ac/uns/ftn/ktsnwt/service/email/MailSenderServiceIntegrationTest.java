package rs.ac.uns.ftn.ktsnwt.service.email;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import rs.ac.uns.ftn.ktsnwt.constants.EmailConstants;
import rs.ac.uns.ftn.ktsnwt.model.ConfirmationToken;
import rs.ac.uns.ftn.ktsnwt.model.User;

import javax.transaction.Transactional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MailSenderServiceIntegrationTest {

    @Autowired
    private MailSenderService mailSenderService;

    @Test
    public void whenSendValidMessage() throws ExecutionException, InterruptedException {
        User u = new User();
        ConfirmationToken ct = new ConfirmationToken();

        u.setEmail(EmailConstants.EXISTING_EMAIL);

        ct.setToken("118b0557-becf-4e5f-83e1-4496d7065256");
        ct.setUser(u);
        Future<SimpleMailMessage> messageFuture = mailSenderService.sendRegistrationMail(ct);

        String text = messageFuture.get().getText();
        String[] emails = messageFuture.get().getTo();

        //there is at least one receiver
        assertNotNull(emails);
        assertNotNull(emails[0]);
        String email = emails[0];

        //text contains token
        assertNotNull(text);
        assertTrue(email.contains(EmailConstants.EXISTING_EMAIL));
        assertTrue(text.contains("118b0557-becf-4e5f-83e1-4496d7065256"));
    }


    @Test
    public void whenSendToNullMail() throws ExecutionException, InterruptedException {
        User u = new User();
        ConfirmationToken ct = new ConfirmationToken();

        u.setEmail(null);
        ct.setToken("118b0557-becf-4e5f-83e1-4496d7065256");
        ct.setUser(u);

        Future<SimpleMailMessage> messageFuture = mailSenderService.sendRegistrationMail(ct);
        assertEquals("Error. Email cannot be null.", messageFuture.get().getText());
    }

    @Test
    public void whenSendNullToken() throws ExecutionException, InterruptedException {
        User u = new User();
        ConfirmationToken ct = new ConfirmationToken();

        u.setEmail(EmailConstants.EXISTING_EMAIL);
        ct.setToken(null);
        ct.setUser(u);

        Future<SimpleMailMessage> messageFuture = mailSenderService.sendRegistrationMail(ct);
        assertEquals("Error. Token cannot be null.", messageFuture.get().getText());
    }


}
