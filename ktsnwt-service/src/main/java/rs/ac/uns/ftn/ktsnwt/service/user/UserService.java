package rs.ac.uns.ftn.ktsnwt.service.user;

import rs.ac.uns.ftn.ktsnwt.model.User;

import java.util.List;

public interface UserService {

    User findById(Long id);
    User findByUsername(String username);
    List<User> findAll();
}
