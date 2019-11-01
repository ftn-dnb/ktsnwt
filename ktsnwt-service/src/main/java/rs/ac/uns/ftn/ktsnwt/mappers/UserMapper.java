package rs.ac.uns.ftn.ktsnwt.mappers;

import rs.ac.uns.ftn.ktsnwt.dto.UserDTO;
import rs.ac.uns.ftn.ktsnwt.model.User;

import java.util.List;
import java.util.stream.Collectors;

public class UserMapper {

    private UserMapper() {
    }

    public static UserDTO toDto(User user) {
        return new UserDTO(user);
    }

    public static List<UserDTO> toDtoList(List<User> users) {
        return users.stream().map(user -> new UserDTO(user)).collect(Collectors.toList());
    }
}
