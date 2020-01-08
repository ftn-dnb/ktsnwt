package rs.ac.uns.ftn.ktsnwt.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import rs.ac.uns.ftn.ktsnwt.model.ConfirmationToken;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ConfirmationTokenRepositoryIntegrationTest {

    @Autowired private ConfirmationTokenRepository tokenRepository;

    @Test
    public void whenTokenNameExists(){
        Long id = 2L;
        String token_name = "tokenTest2";
        boolean activated  = false;

        ConfirmationToken cToken = tokenRepository.findByToken(token_name);
        assertEquals(id, cToken.getId());
        assertEquals(token_name,cToken.getToken());
        assertFalse(cToken.isUsed());
    }

    @Test
    public void whenTokenNameNotExists(){
        String token_name = "fakeTokenName";
        ConfirmationToken cToken = tokenRepository.findByToken(token_name);
        assertEquals(null, cToken);
    }

}
