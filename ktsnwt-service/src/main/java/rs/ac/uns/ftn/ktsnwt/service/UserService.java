package rs.ac.uns.ftn.ktsnwt.service;

import rs.ac.uns.ftn.ktsnwt.dto.UserDTO;

import java.util.List;

public interface UserService {

    UserDTO findById(Long id);
    UserDTO findByUsername(String username);
    List<UserDTO> findAll();
}
