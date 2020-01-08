package rs.ac.uns.ftn.ktsnwt.service.user;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import rs.ac.uns.ftn.ktsnwt.constants.UserConstants;
import rs.ac.uns.ftn.ktsnwt.dto.UserRegistrationDTO;
import rs.ac.uns.ftn.ktsnwt.exception.ApiRequestException;
import rs.ac.uns.ftn.ktsnwt.exception.ResourceNotFoundException;
import rs.ac.uns.ftn.ktsnwt.model.ConfirmationToken;
import rs.ac.uns.ftn.ktsnwt.model.User;
import rs.ac.uns.ftn.ktsnwt.repository.ConfirmationTokenRepository;
import rs.ac.uns.ftn.ktsnwt.repository.UserRepository;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserServiceImplIntegrationTest {

    @Autowired UserServiceImpl userService;

    @Autowired UserRepository userRepository;

    @Autowired PasswordEncoder passwordEncoder;

    @Autowired AuthenticationManager authManager;

    @Autowired ConfirmationTokenRepository tokenRepo;

    @Value("${user.default-profile-image}")
    private String defaultProfileImage;

    //find by id
    //id not in db
    @Test(expected = ResourceNotFoundException.class)
    public void userIdNotExists(){
        userService.findById(50L);
    }

    //id in db
    @Test
    public void userIdExists(){
        User user = userService.findById(UserConstants.DB_ID);
        assertEquals(UserConstants.DB_USERNAME,user.getUsername());
        assertEquals(UserConstants.DB_FIRST_NAME,user.getFirstName());
        assertEquals(UserConstants.DB_LAST_NAME, user.getLastName());
        assertEquals(UserConstants.DB_ID, user.getId());
        assertEquals(UserConstants.DB_EMAIL,user.getEmail());
        assertEquals(UserConstants.DB_ACTIVATED_ACCOUNT, user.isActivatedAccount());
        assertEquals(UserConstants.DB_IMAGE_PATH, user.getImagePath());


    }

    //getUserByUsername
    //usernameNotInDB
    @Test(expected = ResourceNotFoundException.class)
    public void UserUsernameNotExists(){
        User user = userService.findByUsername("fakeUsername");
    }

    @Test
    public void UserUsernameExists(){
        User user = userService.findByUsername(UserConstants.DB_USERNAME);
        assertEquals(UserConstants.DB_USERNAME,user.getUsername());
        assertEquals(UserConstants.DB_FIRST_NAME,user.getFirstName());
        assertEquals(UserConstants.DB_LAST_NAME, user.getLastName());
        assertEquals(UserConstants.DB_ID, user.getId());
    }

    //findAll
    @Test
    public void getAllUsers(){
        List<User> users = userService.findAll();
        assertEquals(3, users.size());
        User user1 = users.get(0);
        User user2 = users.get(1);

        assertEquals(UserConstants.DB_USER_USERNAME, user1.getUsername());
        assertEquals(UserConstants.DB_USERNAME, user2.getUsername());

    }

    // add new user
    //username exception
    @Test(expected = ApiRequestException.class)
    public void addUserUsernameAlreadyExists(){
        UserRegistrationDTO dto = UserConstants.returnUserRegistrationDto();
        dto.setUsername(UserConstants.DB_USERNAME);
        User user = userService.addUser(dto);
    }

    //password exception
    @Test(expected = ApiRequestException.class)
    public void addUserPasswordNotSame(){
        UserRegistrationDTO dto = UserConstants.returnUserRegistrationDto();
        dto.setRepeatPassword("notSame");
        User user = userService.addUser(dto);
    }

    //email exception
    @Test(expected = ApiRequestException.class)
    public void addUserEmailAlreadyTaken(){
        UserRegistrationDTO dto = UserConstants.returnUserRegistrationDto();
        dto.setEmail(UserConstants.DB_EMAIL);
        User user = userService.addUser(dto);
    }

    //success
    @Test
    @Transactional @Rollback(true)
    public void addUserSuccess(){
        UserRegistrationDTO dto = UserConstants.returnUserRegistrationDto();
        User user = userService.addUser(dto);
        List<User> users = userService.findAll();

        assertEquals(4, users.size());
        assertEquals(defaultProfileImage, user.getImagePath());
        assertFalse(user.isActivatedAccount());
        assertEquals(dto.getUsername(), user.getUsername());
        assertEquals(dto.getFirstName(), user.getFirstName());
        assertEquals(dto.getLastName(), user.getLastName());
        assertEquals(dto.getEmail(), user.getEmail());

    }

    @Test
    public void getMyProfileData(){
        UsernamePasswordAuthenticationToken authReq
                = new UsernamePasswordAuthenticationToken(UserConstants.DB_USERNAME, UserConstants.DB_PASSWORD);
        Authentication auth = authManager.authenticate(authReq);

        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(auth);


        User user = userService.getMyProfileData();

        assertEquals(UserConstants.DB_USERNAME,user.getUsername());
        assertEquals(UserConstants.DB_FIRST_NAME,user.getFirstName());
        assertEquals(UserConstants.DB_LAST_NAME, user.getLastName());
        assertEquals(UserConstants.DB_ID, user.getId());
        assertEquals(UserConstants.DB_EMAIL,user.getEmail());
        assertEquals(UserConstants.DB_ACTIVATED_ACCOUNT, user.isActivatedAccount());
        assertEquals(UserConstants.DB_IMAGE_PATH, user.getImagePath());
    }
    //ToDo activateUser
    @Test(expected = ResourceNotFoundException.class)
    public void activateAccountNoToken(){
        boolean result = userService.activateAccount("noToken");
    }

    @Test(expected = ApiRequestException.class)
    @Transactional @Rollback(true)
    public void activateAccountTokenUsed(){
        ConfirmationToken token = tokenRepo.findByToken("tokenTest");
        token.setDatetimeCreated(new Timestamp(new Date().getTime()));// only used case
        tokenRepo.save(token);
        boolean result = userService.activateAccount("tokenTest");
    }

    @Test(expected = ApiRequestException.class)
    @Transactional @Rollback(true)
    public void activateAccountTokenTimeout(){
        boolean result = userService.activateAccount("tokenTest2");
    }


    @Test
    @Transactional @Rollback(true)
    public void activateAccountSuccess(){
        ConfirmationToken token = tokenRepo.findByToken("tokenTest2");
        token.setDatetimeCreated(new Timestamp(new Date().getTime()));
        tokenRepo.save(token);
        boolean result = userService.activateAccount("tokenTest2");

        List<User> users = userService.findAll();

        assertEquals(3, users.size());
        assertTrue(result);
    }
    //ToDo edit
    //ToDo changeProfileImage

}
