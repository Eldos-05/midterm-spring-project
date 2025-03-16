package comsep23.midtermspringproject.service;

import comsep23.midtermspringproject.DTO.UserDTO;
import comsep23.midtermspringproject.entity.User;
import comsep23.midtermspringproject.mappers.UserMapper;
import comsep23.midtermspringproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    public List<User> getAllUsers;

    @Autowired
    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public List<UserDTO> toUserDTOsByUsername(Long userId) {
        String username
                = userRepository.findById(userId).get().getUsername();
        List<User> users = userRepository.findByUsername(username);
        return userMapper.toUserDTOList(users);
    }
    public List<UserDTO> toUserDTOList(List<User> users) {
        return userMapper.toUserDTOList(users);
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }
    public User updateUser(User user) {
        return userRepository.save(user);
    }
    public void deleteUser(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
        } else {
            throw new RuntimeException("User with id " + id + " not found.");
        }
    }
}