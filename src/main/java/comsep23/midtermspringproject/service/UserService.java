package comsep23.midtermspringproject.service;

import comsep23.midtermspringproject.DTO.UserDTO;
import comsep23.midtermspringproject.entity.User;
import comsep23.midtermspringproject.mappers.UserMapper;
import comsep23.midtermspringproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Autowired
    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public List<UserDTO> toUserDTOsByUsername(String username) {
        List<User> users = userRepository.findByUsername(username);
        return userMapper.toUserDTOList(users);
    }
    public List<UserDTO> toUserDTOList(List<User> users) {
        return userMapper.toUserDTOList(users);
    }
}