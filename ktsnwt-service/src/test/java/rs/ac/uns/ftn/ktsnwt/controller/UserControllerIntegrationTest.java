package rs.ac.uns.ftn.ktsnwt.controller;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import rs.ac.uns.ftn.ktsnwt.constants.UserConstants;
import rs.ac.uns.ftn.ktsnwt.dto.UserDTO;
import rs.ac.uns.ftn.ktsnwt.dto.UserEditDTO;
import rs.ac.uns.ftn.ktsnwt.dto.UserRegistrationDTO;
import rs.ac.uns.ftn.ktsnwt.exception.ErrorMessage;
import rs.ac.uns.ftn.ktsnwt.model.ConfirmationToken;
import rs.ac.uns.ftn.ktsnwt.model.User;
import rs.ac.uns.ftn.ktsnwt.repository.AuthorityRepository;
import rs.ac.uns.ftn.ktsnwt.repository.ConfirmationTokenRepository;
import rs.ac.uns.ftn.ktsnwt.repository.UserRepository;
import rs.ac.uns.ftn.ktsnwt.security.auth.JwtAuthenticationRequest;

import java.util.Date;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ConfirmationTokenRepository tokenRepository;

    @Autowired
    private AuthorityRepository authorityRepository;

    private String accessToken;

    private void login() {
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

    private HttpEntity<Object> createHttpEntityEditUser(UserEditDTO dto) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + this.accessToken);
        return new HttpEntity<>(dto,headers);
    }

    private HttpEntity<Object> createUserDtoRequest(UserRegistrationDTO dto){
        HttpHeaders headers = new HttpHeaders();
        return new HttpEntity<>(dto, headers);
    }

    // addUser
    //username exists
    @Test
    public void addNewUserUsernameTaken(){
        UserRegistrationDTO dto = UserConstants.returnUserRegistrationDto();
        dto.setUsername(UserConstants.DB_USERNAME);

        ResponseEntity<ErrorMessage> response = restTemplate.exchange(
                "/api/users/public/add-user",HttpMethod.POST,createUserDtoRequest(dto),ErrorMessage.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Username '" + dto.getUsername() + "' already exists.", response.getBody().getMessage() );
    }
    //passwords not same
    @Test
    public void addNewUserPasswordNotSame(){
        UserRegistrationDTO dto = UserConstants.returnUserRegistrationDto();
        dto.setRepeatPassword("repPassword2");

        ResponseEntity<ErrorMessage> response = restTemplate.exchange(
                "/api/users/public/add-user",HttpMethod.POST,createUserDtoRequest(dto),ErrorMessage.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Provided passwords must be the same.", response.getBody().getMessage() );
    }

    //email taken
    @Test
    public void addNewUserEmailTaken(){
            UserRegistrationDTO dto = UserConstants.returnUserRegistrationDto();
        dto.setEmail(UserConstants.DB_EMAIL);

        ResponseEntity<ErrorMessage> response = restTemplate.exchange(
                "/api/users/public/add-user",HttpMethod.POST,createUserDtoRequest(dto),ErrorMessage.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Email '" + dto.getEmail() + "' is taken.", response.getBody().getMessage() );
    }

    //success
    @Test
    public void addNewUserSuccess(){
        UserRegistrationDTO dto = UserConstants.returnUserRegistrationDto();

        ResponseEntity<UserDTO> response = restTemplate.exchange(
                "/api/users/public/add-user",HttpMethod.POST,createUserDtoRequest(dto),UserDTO.class);

        UserDTO result = response.getBody();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(result);
        assertEquals(dto.getUsername(), result.getUsername());
        assertEquals(dto.getFirstName(), result.getFirstName());
        assertEquals(dto.getLastName(), result.getLastName());
        assertEquals(dto.getEmail(), result.getEmail());

        tokenRepository.deleteById(3L);
        userRepository.deleteById(result.getId());


    }


    //tokenVerify
    //token not in db
    @Test
    public void activateAccountNoTokenInDB(){
        String token = "noToken";

        ResponseEntity<ErrorMessage> response = restTemplate.exchange(
                "/api/users/public/verify-account/" + token, HttpMethod.GET, new HttpEntity<>(new HttpHeaders()), ErrorMessage.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Confirmation token doesn't exist.", response.getBody().getMessage());
    }

    //token already used
    @Test
    public void activateAccountTokenAlreadyUsed(){
        String token = "tokenTest";
        ConfirmationToken confToken = tokenRepository.findByToken(token);
        Date dbTime = confToken.getDatetimeCreated();
        confToken.setDatetimeCreated(new Date());
        tokenRepository.save(confToken);
        tokenRepository.flush();


        ResponseEntity<ErrorMessage> response = restTemplate.exchange(
                "/api/users/public/verify-account/" + token, HttpMethod.GET, new HttpEntity<>(new HttpHeaders()), ErrorMessage.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("This token has been already used.", response.getBody().getMessage());


        confToken.setDatetimeCreated(dbTime);
        tokenRepository.save(confToken);
        tokenRepository.flush();
    }
    //timeout
    @Test
    public void activateAccountTokenTimeout(){
        String token = "tokenTest2";

        ResponseEntity<ErrorMessage> response = restTemplate.exchange(
                "/api/users/public/verify-account/" + token, HttpMethod.GET, new HttpEntity<>(new HttpHeaders()), ErrorMessage.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Confirmation token timed out.", response.getBody().getMessage());
    }

    //success
    public void activateAccountSuccess(){
        String token = "tokenTest2";

        ConfirmationToken confToken = tokenRepository.findByToken(token);
        Date dbTime = confToken.getDatetimeCreated();
        confToken.setDatetimeCreated(new Date());
        tokenRepository.save(confToken);
        tokenRepository.flush();

        ResponseEntity<Boolean> response = restTemplate.exchange(
                "/api/users/public/verify-account/" + token, HttpMethod.GET, new HttpEntity<>(new HttpHeaders()), Boolean.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody());

        User u = userRepository.findByUsername("paul.doe");
        assertTrue(u.isActivatedAccount());

        confToken.setDatetimeCreated(dbTime);
        tokenRepository.save(confToken);
        tokenRepository.flush();

        u = userRepository.findByUsername("paul.doe");
        assertFalse(u.isActivatedAccount());

    }

    //getProfileData
    @Test
    public void getProfileData(){
        login();
        ResponseEntity<UserDTO> response = restTemplate.exchange(
                "/api/users/my-profile", HttpMethod.GET, createHttpEntity(), UserDTO.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        UserDTO dto = response.getBody();
        assertEquals(UserConstants.DB_USERNAME, dto.getUsername());
        assertEquals(UserConstants.DB_FIRST_NAME, dto.getFirstName());
        assertEquals(UserConstants.DB_LAST_NAME, dto.getLastName());
        assertEquals(UserConstants.DB_EMAIL, dto.getEmail());
        assertEquals(UserConstants.DB_IMAGE_PATH, dto.getImagePath());
        assertEquals(UserConstants.DB_ACTIVATED_ACCOUNT, dto.isActivated());

    }


    //edit profile
    //mail in use
    @Test
    public void editUserEmailAlreadyTaken(){
        UserEditDTO dto = new UserEditDTO();
        dto.setFirstName("NewName");
        dto.setLastName("NewSurname");
        dto.setEmail("john@doe.com");

        login();
        ResponseEntity<ErrorMessage> response = restTemplate.exchange(
                "/api/users/my-profile", HttpMethod.PUT, createHttpEntityEditUser(dto), ErrorMessage.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Email '" + dto.getEmail() + "' is taken.", response.getBody().getMessage());

    }

    //success
    @Test
    public void editUserSuccess(){
        UserEditDTO dto = new UserEditDTO();
        dto.setFirstName("NewName");
        dto.setLastName("NewSurname");
        dto.setEmail("newEmail@doe.com");

        login();
        ResponseEntity<UserEditDTO> response = restTemplate.exchange(
                "/api/users/my-profile", HttpMethod.PUT, createHttpEntityEditUser(dto), UserEditDTO.class);


        assertEquals(HttpStatus.OK, response.getStatusCode());
        UserEditDTO result = response.getBody();

        User u = userRepository.findByUsername(UserConstants.DB_USERNAME);
        assertEquals(u.getFirstName(), result.getFirstName());
        assertEquals(u.getLastName(), result.getLastName());
        assertEquals(u.getEmail(), result.getEmail());

        dto.setFirstName(UserConstants.DB_FIRST_NAME);
        dto.setLastName(UserConstants.DB_LAST_NAME);
        dto.setEmail(UserConstants.DB_EMAIL);
        ResponseEntity<UserEditDTO> response2 = restTemplate.exchange(
                "/api/users/my-profile", HttpMethod.PUT, createHttpEntityEditUser(dto), UserEditDTO.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
