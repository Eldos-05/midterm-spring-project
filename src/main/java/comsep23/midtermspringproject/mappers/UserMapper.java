package comsep23.midtermspringproject.mappers;

import comsep23.midtermspringproject.DTO.UserDTO;
import comsep23.midtermspringproject.entity.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDTO toUserDTO(User user);
    User toUserEntity(UserDTO userDTO);
    List<UserDTO> toUserDTOList(List<User> users);


}
