package comsep23.midtermspringproject.controller;


import comsep23.midtermspringproject.DTO.UserDTO;
import comsep23.midtermspringproject.repository.UserRepository;
import comsep23.midtermspringproject.service.SneakerService;
import comsep23.midtermspringproject.service.UserService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/users/")
public class UserController {
    private final UserService userService;
    private final SneakerService sneakerService;






}
