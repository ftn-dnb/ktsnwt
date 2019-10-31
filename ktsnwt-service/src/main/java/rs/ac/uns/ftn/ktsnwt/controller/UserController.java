package rs.ac.uns.ftn.ktsnwt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.ktsnwt.dto.UserDTO;
import rs.ac.uns.ftn.ktsnwt.dto.UserRegistrationDTO;
import rs.ac.uns.ftn.ktsnwt.mappers.UserMapper;
import rs.ac.uns.ftn.ktsnwt.model.User;
import rs.ac.uns.ftn.ktsnwt.service.user.UserService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;


    @PostMapping("/public/add-user")
    public ResponseEntity<UserDTO> addUser(@Valid @RequestBody UserRegistrationDTO userInfo) {
        User user = userService.addUser(userInfo);
        return new ResponseEntity<>(UserMapper.toDto(user), HttpStatus.OK);
    }

    @GetMapping("/public/verify-account/{token}")
    public ResponseEntity verifyUserAccount(@PathVariable String token) {
        userService.activateAccount(token);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/my-profile")
    public ResponseEntity<UserDTO> getMyProfileData() {
        User user = userService.getMyProfileData();
        return new ResponseEntity<>(UserMapper.toDto(user), HttpStatus.OK);
    }
}
