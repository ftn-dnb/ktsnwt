package rs.ac.uns.ftn.ktsnwt.service.userDetails;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit4.SpringRunner;
import rs.ac.uns.ftn.ktsnwt.constants.TestHttpServletRequest;
import rs.ac.uns.ftn.ktsnwt.constants.UserConstants;
import rs.ac.uns.ftn.ktsnwt.dto.UserDTO;
import rs.ac.uns.ftn.ktsnwt.exception.ApiRequestException;
import rs.ac.uns.ftn.ktsnwt.model.User;
import rs.ac.uns.ftn.ktsnwt.model.UserTokenState;
import rs.ac.uns.ftn.ktsnwt.security.auth.JwtAuthenticationRequest;
import rs.ac.uns.ftn.ktsnwt.service.user.CustomUserDetailsService;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CustomUserDetailsServiceIntegrationTest {

    @Autowired
    CustomUserDetailsService customUserDetailsService;

    @Autowired
    private AuthenticationManager authManager;

    @Before
    public void setUp() {
        UsernamePasswordAuthenticationToken authReq
                = new UsernamePasswordAuthenticationToken(UserConstants.DB_USERNAME, UserConstants.DB_PASSWORD);
        Authentication auth = authManager.authenticate(authReq);
        Authentication auth1 = authManager.authenticate(authReq);

        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(auth);
    }

    @Test
    public void givenValidUsername_loadUser() {
        UserDetails details = customUserDetailsService.loadUserByUsername(UserConstants.DB_USERNAME);
        assertEquals(UserConstants.DB_USERNAME, details.getUsername());
    }

    @Test(expected = UsernameNotFoundException.class)
    public void givenInvalidUsername_throwException() {
        UserDetails details = customUserDetailsService.loadUserByUsername(UserConstants.DB_USERNAME_NON_EXIST);
    }

    @Test
    public void givenLoggedUser_changePassword() {
        String newPassword = "newPass";
        User loggedUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = loggedUser.getUsername();

        UserDetails loggedUserRepo = customUserDetailsService.loadUserByUsername(username);
        System.out.println(loggedUserRepo.getPassword());

        customUserDetailsService.changePassword(UserConstants.DB_PASSWORD, newPassword);

        UserDetails loggedUserRepoChanged = customUserDetailsService.loadUserByUsername(username);
        System.out.println(loggedUserRepoChanged.getPassword());

        assertNotEquals(loggedUserRepo.getPassword(), loggedUserRepoChanged.getPassword());

        customUserDetailsService.changePassword(newPassword, UserConstants.DB_PASSWORD);
    }

    @Test
    public void validLoginData_loginUser() {
        UserDTO user = customUserDetailsService.login(new JwtAuthenticationRequest(UserConstants.DB_USERNAME, UserConstants.DB_PASSWORD));
        assertEquals(UserConstants.DB_USERNAME, user.getUsername());
    }

    @Test(expected = ApiRequestException.class)
    public void invalidLoginData_throwException() {
        UserDTO user = customUserDetailsService.login(new JwtAuthenticationRequest(UserConstants.DB_USERNAME_NON_EXIST, UserConstants.DB_PASSWORD));
    }

    @Test(expected = ApiRequestException.class)
    public void validHttpRequest_refreshAuthToken() {
        UserDTO user = customUserDetailsService.login(new JwtAuthenticationRequest(UserConstants.DB_USERNAME, UserConstants.DB_PASSWORD));
        TestHttpServletRequest request = new TestHttpServletRequest();
        request.setToken(user.getToken().getAccessToken());
        UserTokenState state = customUserDetailsService.refreshAuthenticationToken(request);
    }

}
