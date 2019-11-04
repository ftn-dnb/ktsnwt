package rs.ac.uns.ftn.ktsnwt.service.user;

import rs.ac.uns.ftn.ktsnwt.dto.UserEditDTO;
import rs.ac.uns.ftn.ktsnwt.dto.UserRegistrationDTO;
import rs.ac.uns.ftn.ktsnwt.model.User;

import java.util.List;

public interface UserService {

    User findById(Long id);
    User findByUsername(String username);
    List<User> findAll();
    User addUser(UserRegistrationDTO userInfo);
    void activateAccount(String token);
    User getMyProfileData();
    User editUser(UserEditDTO userInfo);
    void changeProfileImage(String imagePath);
}
