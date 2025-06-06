package comsep23.midtermspringproject.controller;

import comsep23.midtermspringproject.DTO.UserDTO;
import comsep23.midtermspringproject.entity.User;
import comsep23.midtermspringproject.mappers.UserMapper;
import comsep23.midtermspringproject.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    @Autowired
    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @GetMapping("/welcome")
    public String welcome() {
        return "Welcome to Online-Store";
    }
    @GetMapping("/unsecured")
    public String unsecuredData() {
        return "Unsecured data";
    }

    @GetMapping("/secured")
    public String securedData() {
        return "Secured data";
    }

    @GetMapping("/admin")
    public String adminData() {
        return "Admin data";
    }

    @GetMapping("/info")
    public String userData(Principal principal) {
        return principal.getName();
    }
    @GetMapping("/listOf-users")
    public ResponseEntity<List<UserDTO>> findAll() {
        List<User> users = userService.getAllUsers();
        List<UserDTO> userDTOList = userMapper.toUserDTOList(users);
        return ResponseEntity.ok(userDTOList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> findById(@PathVariable Long id) {
        Optional<User> user = userService.getUserById(id);
        return user.isPresent()
                ? ResponseEntity.ok(userMapper.toUserDTO(user.get()))
                : ResponseEntity.notFound().build();
    }


    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable long id, @Valid @RequestBody UserDTO userDTO) {
        User user = userMapper.toUser(userDTO);
        user.setId(id);
        User updatedUser = userService.updateUser(user);
        if (updatedUser != null) {
            UserDTO updatedUserDTO = userMapper.toUserDTO(updatedUser);
            return ResponseEntity.ok(updatedUserDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UserDTO> deleteUser(@PathVariable long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}