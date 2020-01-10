package rs.ac.uns.ftn.ktsnwt.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.client.TestRestTemplateExtensionsKt;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import rs.ac.uns.ftn.ktsnwt.constants.TestHttpServletRequest;
import rs.ac.uns.ftn.ktsnwt.constants.UserConstants;
import rs.ac.uns.ftn.ktsnwt.dto.PasswordChangerDTO;
import rs.ac.uns.ftn.ktsnwt.dto.UserDTO;
import rs.ac.uns.ftn.ktsnwt.model.UserTokenState;
import rs.ac.uns.ftn.ktsnwt.security.auth.JwtAuthenticationRequest;
import rs.ac.uns.ftn.ktsnwt.service.user.CustomUserDetailsService;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthencticationControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    private String accessToken;

    @Before
    public void login() {
        JwtAuthenticationRequest loginDto = new JwtAuthenticationRequest(
                UserConstants.DB_USER_USERNAME, UserConstants.DB_USER_PASSWORD
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
    public void whenValidLoginParameters_loginUser() {

        JwtAuthenticationRequest loginDto = new JwtAuthenticationRequest(
                UserConstants.DB_USERNAME, UserConstants.DB_PASSWORD
        );

        ResponseEntity<UserDTO> response = restTemplate.postForEntity(
                "/auth/login", loginDto, UserDTO.class
        );

        UserDTO data = response.getBody();
        assertEquals(UserConstants.DB_USERNAME, data.getUsername());
        assertNotNull(data.getToken().getAccessToken());
    }

    @Test
    public void whenPasswordChangerValid_changePassword() {
        PasswordChangerDTO dto = new PasswordChangerDTO();
        dto.setNewPassword("noviPass");
        dto.setOldPassword(UserConstants.DB_PASSWORD);

        ResponseEntity<Void> response = restTemplate.postForEntity(
                "/auth/change-password", dto, Void.class
        );
    }
}
