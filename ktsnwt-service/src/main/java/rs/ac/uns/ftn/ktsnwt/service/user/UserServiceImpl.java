package rs.ac.uns.ftn.ktsnwt.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.ktsnwt.exception.ApiRequestException;
import rs.ac.uns.ftn.ktsnwt.model.User;
import rs.ac.uns.ftn.ktsnwt.repository.UserRepository;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User findById(Long id) throws ApiRequestException {
        try {
            return userRepository.findById(id).get();
        } catch (NoSuchElementException e) {
            throw new ApiRequestException("User with id '" + id + "' doesn't exist.");
        }
    }

    @Override
    public User findByUsername(String username) throws ApiRequestException {
        try {
            return userRepository.findByUsername(username);
        } catch (UsernameNotFoundException e) {
            throw new ApiRequestException("User with username '" + username + "' doesn't exist.");
        }
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }
}
