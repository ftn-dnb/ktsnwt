package rs.ac.uns.ftn.ktsnwt.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import rs.ac.uns.ftn.ktsnwt.constants.EventDayConstants;
import rs.ac.uns.ftn.ktsnwt.constants.UserConstants;
import rs.ac.uns.ftn.ktsnwt.dto.UserDTO;
import rs.ac.uns.ftn.ktsnwt.exception.ErrorMessage;
import rs.ac.uns.ftn.ktsnwt.security.auth.JwtAuthenticationRequest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EventDayControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    private String accessToken;

    @Before
    public void login() {
        JwtAuthenticationRequest loginDto = new JwtAuthenticationRequest(
                UserConstants.DB_USERNAME, UserConstants.DB_PASSWORD
        );

        ResponseEntity<UserDTO> response = restTemplate.postForEntity("/auth/login", loginDto, UserDTO.class);
        UserDTO user = response.getBody();
        accessToken = user.getToken().getAccessToken();
    }

    private HttpEntity<Object> createHttpEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + this.accessToken);
        return new HttpEntity<>(headers);
    }

    @Test
    public void whenDisableEventDay() {
        ResponseEntity<String> response = restTemplate.exchange(
                "/api/eventDay/disable/" + EventDayConstants.DB_ID, HttpMethod.POST, createHttpEntity(), String.class);

        String responseMessage = response.getBody();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(responseMessage);
        assertEquals("Event day canceled", responseMessage);
    }

    @Test
    public void whenDisableEventDayReturnNotFound() {
        ResponseEntity<ErrorMessage> response = restTemplate.exchange(
                "/api/eventDay/disable/" + EventDayConstants.NON_EXISTING_DB_ID, HttpMethod.POST, createHttpEntity(), ErrorMessage.class);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
