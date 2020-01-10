package rs.ac.uns.ftn.ktsnwt.service.userDetails;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import rs.ac.uns.ftn.ktsnwt.constants.SecurityContextConstants;
import rs.ac.uns.ftn.ktsnwt.constants.TestHttpServletRequest;
import rs.ac.uns.ftn.ktsnwt.constants.UserConstants;
import rs.ac.uns.ftn.ktsnwt.dto.UserDTO;
import rs.ac.uns.ftn.ktsnwt.exception.ApiRequestException;
import rs.ac.uns.ftn.ktsnwt.model.User;
import rs.ac.uns.ftn.ktsnwt.model.UserTokenState;
import rs.ac.uns.ftn.ktsnwt.repository.UserRepository;
import rs.ac.uns.ftn.ktsnwt.security.TokenUtils;
import rs.ac.uns.ftn.ktsnwt.security.auth.JwtAuthenticationRequest;
import rs.ac.uns.ftn.ktsnwt.service.user.CustomUserDetailsService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.xml.bind.DatatypeConverter;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CustomUserDetailsServiceUnitTest {

    @Autowired
    CustomUserDetailsService customUserDetailsService;

    @MockBean
    AuthenticationManager authenticationManager;

    @MockBean
    UserRepository userRepositoryMocked;

    @MockBean
    TokenUtils tokenUtilsMocked;

    @Before
    public void setUp() {
        SecurityContext securityContext = SecurityContextConstants.returnMockedSecurityContext();
        SecurityContextHolder.setContext(securityContext);
        //User loggedUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @Test
    public void whenValidUsername_thenUserLoaded() {
        User user = new User();
        user.setUsername(UserConstants.MOCK_USERNAME);
        Mockito.when(userRepositoryMocked.findByUsername(user.getUsername())).thenReturn(user);

        UserDetails foundUser = customUserDetailsService.loadUserByUsername(UserConstants.MOCK_USERNAME);
        assertEquals(user.getUsername(), foundUser.getUsername());
    }

    @Test(expected = UsernameNotFoundException.class)
    public void whenInvalidUsername_thenThrowException() {
        String invalidUsername = UserConstants.MOCK_USERNAME;
        Mockito.when(userRepositoryMocked.findByUsername(invalidUsername)).thenReturn(null);

        customUserDetailsService.loadUserByUsername(invalidUsername);
    }

    @Test
    public void whenUserLoggedIn_changePassword() {
        User loggedUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Mockito.when(userRepositoryMocked.findByUsername(loggedUser.getUsername())).thenReturn(loggedUser);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Mockito.when(authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loggedUser.getUsername(), loggedUser.getPassword())))
                .thenReturn(authentication);

        customUserDetailsService.changePassword(loggedUser.getPassword(), UserConstants.MOCK_NEW_PASSWORD);
    }

    @Test(expected = ApiRequestException.class)
    public void whenCredentialsInvalid_throwException() {
        User loggedUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Mockito.when(authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loggedUser.getUsername(), loggedUser.getPassword())))
                .thenThrow(BadCredentialsException.class);
        customUserDetailsService.changePassword(loggedUser.getPassword(), UserConstants.MOCK_NEW_PASSWORD);
    }

    @Test
    public void whenAuthReqValid_loginUser() {
        JwtAuthenticationRequest jwt = new JwtAuthenticationRequest();
        jwt.setUsername(UserConstants.MOCK_USERNAME);
        jwt.setPassword(UserConstants.MOCK_PASSWORD);

        User loggedUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Mockito.when(userRepositoryMocked.findByUsername(loggedUser.getUsername())).thenReturn(loggedUser);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Mockito.when(authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loggedUser.getUsername(), loggedUser.getPassword())))
                .thenReturn(authentication);

        UserDTO dto = customUserDetailsService.login(jwt);

        assertEquals(jwt.getUsername(), dto.getUsername());

    }

    @Test(expected = ApiRequestException.class)
    public void whenAuthReqInalid_throwException() {
        JwtAuthenticationRequest jwt = new JwtAuthenticationRequest();
        jwt.setUsername(UserConstants.MOCK_USERNAME);
        jwt.setPassword(UserConstants.MOCK_PASSWORD);

        User loggedUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Mockito.when(userRepositoryMocked.findByUsername(loggedUser.getUsername())).thenReturn(loggedUser);
        Mockito.when(authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loggedUser.getUsername(), loggedUser.getPassword())))
                .thenThrow(BadCredentialsException.class);

        UserDTO dto = customUserDetailsService.login(jwt);

    }

    @Test
    public void whenTokenValid_refreshToken() {
        HttpServletRequest request = new TestHttpServletRequest();
        Mockito.when(tokenUtilsMocked.getToken(request)).thenReturn("token");
        Mockito.when(tokenUtilsMocked.getUsernameFromToken("token")).thenReturn(UserConstants.MOCK_USERNAME);
        User loggedUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Mockito.when(userRepositoryMocked.findByUsername(loggedUser.getUsername())).thenReturn(loggedUser);
        Mockito.when(tokenUtilsMocked.canTokenBeRefreshed("token", loggedUser.getLastPasswordResetDate())).thenReturn(true);

        int expiresIn = 4000;

        Mockito.when(tokenUtilsMocked.getExpiredIn()).thenReturn(expiresIn);
        Mockito.when(tokenUtilsMocked.refreshToken("token")).thenReturn("tokenRefreshed");

        UserTokenState state = customUserDetailsService.refreshAuthenticationToken(request);

        assertEquals("tokenRefreshed", state.getAccessToken());

    }

    @Test(expected = ApiRequestException.class)
    public void whenTokenInvalid_throwException() {
        HttpServletRequest request = new TestHttpServletRequest();
        Mockito.when(tokenUtilsMocked.getToken(request)).thenReturn("token");
        Mockito.when(tokenUtilsMocked.getUsernameFromToken("token")).thenReturn(UserConstants.MOCK_USERNAME);
        User loggedUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Mockito.when(userRepositoryMocked.findByUsername(loggedUser.getUsername())).thenReturn(loggedUser);
        Mockito.when(tokenUtilsMocked.canTokenBeRefreshed("token", loggedUser.getLastPasswordResetDate())).thenReturn(false);

        customUserDetailsService.refreshAuthenticationToken(request);
    }



}
