package rs.ac.uns.ftn.ktsnwt.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.ktsnwt.common.TimeProvider;
import rs.ac.uns.ftn.ktsnwt.common.consts.UserRoles;
import rs.ac.uns.ftn.ktsnwt.dto.UserEditDTO;
import rs.ac.uns.ftn.ktsnwt.dto.UserRegistrationDTO;
import rs.ac.uns.ftn.ktsnwt.exception.ApiRequestException;
import rs.ac.uns.ftn.ktsnwt.exception.ResourceNotFoundException;
import rs.ac.uns.ftn.ktsnwt.mappers.UserMapper;
import rs.ac.uns.ftn.ktsnwt.model.Authority;
import rs.ac.uns.ftn.ktsnwt.model.ConfirmationToken;
import rs.ac.uns.ftn.ktsnwt.model.User;
import rs.ac.uns.ftn.ktsnwt.repository.AuthorityRepository;
import rs.ac.uns.ftn.ktsnwt.repository.ConfirmationTokenRepository;
import rs.ac.uns.ftn.ktsnwt.repository.UserRepository;
import rs.ac.uns.ftn.ktsnwt.service.email.MailSenderService;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthorityRepository authorityRepository;

    @Autowired
    private ConfirmationTokenRepository tokenRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TimeProvider timeProvider;

    @Autowired
    private MailSenderService mailSenderService;

    @Value("${user.default-profile-image}")
    private String defaultProfileImage;


    @Override
    public User findById(Long id) {
        try {
            return userRepository.findById(id).get();
        } catch (NoSuchElementException e) {
            throw new ResourceNotFoundException("User with id '" + id + "' doesn't exist.");
        }
    }

    @Override
    public User findByUsername(String username) {
        try {
            return userRepository.findByUsername(username);
        } catch (UsernameNotFoundException e) {
            throw new ResourceNotFoundException("User with username '" + username + "' doesn't exist.");
        }
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User addUser(UserRegistrationDTO userInfo) {
        if (userRepository.findByUsername(userInfo.getUsername()) != null) {
            throw new ApiRequestException("Username '" + userInfo.getUsername() + "' already exists.");
        }

        if (!userInfo.getPassword().equals(userInfo.getRepeatPassword())) {
            throw new ApiRequestException("Provided passwords must be the same.");
        }

        if (userRepository.findByEmail(userInfo.getEmail()) != null) {
            throw new ApiRequestException("Email '" + userInfo.getEmail() + "' is taken.");
        }

        User user = createNewUserObject(userInfo);
        userRepository.save(user);

        ConfirmationToken token = new ConfirmationToken(user);
        tokenRepository.save(token);

        mailSenderService.sendRegistrationMail(token);

        return user;
    }

    private User createNewUserObject(UserRegistrationDTO userInfo) {
        User user = UserMapper.toEntity(userInfo);
        user.setPassword(passwordEncoder.encode(userInfo.getPassword()));
        user.setImagePath(defaultProfileImage);
        user.setLastPasswordResetDate(timeProvider.nowTimestamp());
        user.getUserAuthorities().add(authorityRepository.findByName(UserRoles.ROLE_USER));

        return user;
    }

    @Override
    public void activateAccount(String token) {
        ConfirmationToken confirmationToken = tokenRepository.findByToken(token);

        if (confirmationToken == null) {
            throw new ResourceNotFoundException("Confirmation token doesn't exist.");
        }

        if (confirmationToken.isUsed()) {
            throw new ApiRequestException("This token has been already used.");
        }

        User user = confirmationToken.getUser();
        long timeDifference = timeProvider.timeDifferenceInMinutes(timeProvider.now(), confirmationToken.getDatetimeCreated());

        if (timeDifference < 30) {
            user.setActivatedAccount(true);
            userRepository.save(user);
            confirmationToken.setUsed(true);
            tokenRepository.save(confirmationToken);
        } else {
            tokenRepository.delete(confirmationToken);
            userRepository.delete(user);
            throw new ApiRequestException("Confirmation token timed out.");
        }
    }

    @Override
    public User getMyProfileData() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @Override
    public User editUser(UserEditDTO userInfo) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        user.setFirstName(userInfo.getFirstName());
        user.setLastName(userInfo.getLastName());

        if (userRepository.findByEmail(userInfo.getEmail()) != null) {
            throw new ApiRequestException("Email '" + userInfo.getEmail() + "' is taken.");
        }

        user.setEmail(userInfo.getEmail());

        userRepository.save(user);

        return user;
    }

    @Override
    public void changeProfileImage(String imagePath) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        user.setImagePath(imagePath);
        userRepository.save(user);
    }
}
