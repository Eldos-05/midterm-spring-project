package comsep23.midtermspringproject.unit_tests;



import comsep23.midtermspringproject.DTO.UserDTO;
import comsep23.midtermspringproject.controller.UserController;
import comsep23.midtermspringproject.entity.User;
import comsep23.midtermspringproject.mappers.UserMapper;
import comsep23.midtermspringproject.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import com.fasterxml.jackson.databind.ObjectMapper;

public class UserControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserController userController;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    public void testFindById_existingUser() throws Exception {
        User user = new User();
        UserDTO userDTO = new UserDTO();
        when(userService.getUserById(1L)).thenReturn(Optional.of(user));
        when(userMapper.toUserDTO(user)).thenReturn(userDTO);
        mockMvc.perform(get("/api/users/1")).andExpect(status().isOk());
    }

    @Test
    public void testFindById_nonExistingUser() throws Exception {
        when(userService.getUserById(1L)).thenReturn(Optional.empty());
        mockMvc.perform(get("/api/users/1")).andExpect(status().isNotFound());
    }

    @Test
    public void testCreateUser() throws Exception {
        UserDTO userDTO = new UserDTO();
        User user = new User();
        when(userMapper.toUser(userDTO)).thenReturn(user);
        when(userService.createUser(user)).thenReturn(user);
        mockMvc.perform(post("/api/users").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(userDTO))).andExpect(status().isCreated());
    }

    @Test
    public void testUpdateUser_existingUser() throws Exception {
        UserDTO userDTO = new UserDTO();
        User user = new User();
        when(userMapper.toUser(userDTO)).thenReturn(user);
        when(userService.updateUser(user)).thenReturn(user);
        mockMvc.perform(put("/api/users/1").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(userDTO))).andExpect(status().isOk());
    }

    @Test
    public void testUpdateUser_nonExistingUser() throws Exception {
        UserDTO userDTO = new UserDTO();
        User user = new User();
        when(userMapper.toUser(userDTO)).thenReturn(user);
        when(userService.updateUser(user)).thenReturn(null);
        mockMvc.perform(put("/api/users/1").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(userDTO))).andExpect(status().isNotFound());
    }

    @Test
    public void testDeleteUser() throws Exception {
        mockMvc.perform(delete("/api/users/1")).andExpect(status().isNoContent());
        verify(userService, times(1)).deleteUser(1L);
    }
}